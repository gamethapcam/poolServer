package com.poolserver.server;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.poolserver.configs.RedisConfig;
import com.poolserver.enums.PoolRoom;
import com.poolserver.listener.SocketAuthorizationListener;
import com.poolserver.model.*;
import com.poolserver.enums.PoolEvent;
import com.poolserver.enums.PoolClientType;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.util.*;

public class PoolServer {

    private SocketIOServer mServer;
    private Configuration mConfig;
    private RedissonClient mRClient;
    private RSet<String> mRSetRoom;
    private RMap<String, ClientData> mRMapClientStore;

    private ArrayList<String> namespaces;
    private int port;


    private PoolServer() {

    }

    private void setServer(SocketIOServer server) {
        this.mServer = server;
    }

    private void addListenerToNs(SocketIONamespace ns) {
        ns.addEventListener(PoolEvent.CREATE_ROOM.toString(), String.class, (sioc, t, ar) -> {
            String roomName = UUID.randomUUID().toString();
            if (mRSetRoom.add(roomName)) {
                sioc.leaveRoom(PoolRoom.DRIVER_ROOM.toString());
                sioc.joinRoom(roomName);
            }
        });
        ns.addEventListener(PoolEvent.LEAVE_ROOM.toString(), ClientData.class, (sioc, t, ar) -> {
            if (t.getType().equals(PoolClientType.DRIVER.toString()))
                sioc.joinRoom(PoolRoom.DRIVER_ROOM.toString());
            else
                sioc.joinRoom(PoolRoom.DRIVER_ROOM.toString());
        });
        ns.addEventListener(PoolEvent.TRACKING.toString(), TrackingRoomLocationMessageObject.class, (sioc, t, ar) ->
                ns.getRoomOperations(t.getRoom()).sendEvent(PoolEvent.TRACKING.toString(), t));
        ns.addEventListener(PoolEvent.TRACKING_ACCEPT.toString(), TrackingMessage.class, (sioc, t, ar) -> {
            if (t.getRoom().isEmpty() == false) {
                SocketIOClient client = ns.getClient(UUID.fromString(t.getSockIdAt(0)));
                if (client != null) {
                    client.joinRoom(t.getRoom());
                }
                client = ns.getClient(UUID.fromString(t.getSockIdAt(1)));
                if (client != null) {
                    client.joinRoom(t.getRoom());
                }
                ns.getRoomOperations(t.getRoom()).sendEvent(PoolEvent.TRACKING_ESTABLISHED.toString(), t.getRoom());
            }
        });
        ns.addEventListener(PoolEvent.TRACKING_DENIED.toString(), TrackingMessage.class, (sioc, t, ar) -> {
            SocketIOClient client = ns.getClient(UUID.fromString(t.getSockIdAt(0)));
            if (client != null) {
                client.sendEvent(PoolEvent.TRACKING_DENIED.toString(), t);
            }
        });
        ns.addEventListener(PoolEvent.TRACKING_INVITE.toString(), TrackingMessage.class, (sioc, t, ar) -> {
            if (t.getRoom().isEmpty()) {
                t.setRoom(new String(
                                Base64.getEncoder().encode(
                                        (t.getSockIdAt(0) + t.getSockIdAt(1)).getBytes()
                                )
                        )
                );
            }
            SocketIOClient client = ns.getClient(UUID.fromString(t.getSockIdAt(1)));
            if (client != null) {
                client.sendEvent(PoolEvent.TRACKING_INVITE.toString(), t);
                sioc.sendEvent(PoolEvent.TRACKING_INVITE_SENT.toString(), t);
            } else {
                sioc.sendEvent(PoolEvent.TRACKING_INVITE_ERROR.toString(), "Client tracking not found!");
            }
        });
        ns.addEventListener(PoolEvent.PRIVATE.toString(), PrivateMessageObject.class, (client, data, ackRequest) -> {
            SocketIOClient toClient = ns.getClient(UUID.fromString(data.getSockId()));
            if (toClient != null) {
                toClient.sendEvent(PoolEvent.LOCATION.toString(), data);
            }
        });
        ns.addEventListener(PoolEvent.LOCATION.toString(), LocationMessage.class, (client, data, ackRequest) -> {
            UUID sid = client.getSessionId();
            ClientData user = mRMapClientStore.get(sid.toString());
            if (user != null) {
                user.setLastLatLng(data.getClientLatLng());
                mRMapClientStore.replace(sid.toString(), user);
            }
            //
            data.setSessionId(sid.toString());
            //
            if (data.getType().equals(PoolClientType.DRIVER.toString())) {
                ns.getRoomOperations(PoolRoom.RIDER_ROOM.toString()).sendEvent(PoolEvent.LOCATION.toString(), data);
            } else if (data.getType().equals(PoolClientType.RIDER.toString())) {
                ns.getRoomOperations(PoolRoom.DRIVER_ROOM.toString()).sendEvent(PoolEvent.LOCATION.toString(), data);
            } else {
                mRMapClientStore.remove(sid.toString());
                client.disconnect();
            }
        });
        ns.addEventListener(PoolEvent.CONNECTED.toString(), LocationMessage.class, (client, data, ackRequest) -> {
            if (data != null) {
                if (data.getType().equals(PoolClientType.DRIVER.toString())) {
                    client.joinRoom(PoolRoom.DRIVER_ROOM.toString());
                } else if (data.getType().equals(PoolClientType.RIDER.toString())) {
                    client.joinRoom(PoolRoom.RIDER_ROOM.toString());
                } else {
                    client.disconnect();
                    return;
                }
                //
                mRMapClientStore.put(client.getSessionId().toString(),
                        new ClientData(
                                data.getType(),
                                client.getSessionId().toString(),
                                data.getClientMetaData(),
                                data.getClientLatLng()
                        )
                );
                Set<Map.Entry<String, ClientData>> entries = mRMapClientStore.readAllEntrySet();
                ArrayList<ClientData> flash = new ArrayList<>();
                ArrayList<ClientData> revFlash = new ArrayList<>();

                String toRoom = data.getType().equals(PoolClientType.DRIVER.toString()) ? PoolRoom.RIDER_ROOM.toString() : PoolRoom.DRIVER_ROOM.toString();
                String revToRoom = toRoom.equals(PoolRoom.RIDER_ROOM.toString()) ? PoolRoom.DRIVER_ROOM.toString() : PoolRoom.RIDER_ROOM.toString();

                entries.stream().forEach((entry) -> {
                    String sessionId = entry.getValue().getSessionId();
                    if (sessionId != null && this.mServer.getClient(UUID.fromString(sessionId)) != null) {
                        if (entry.getValue().getType().equals(data.getType())) {
                            flash.add(entry.getValue());
                        } else {
                            revFlash.add(entry.getValue());
                        }
                    }
                });
                ns.getRoomOperations(toRoom).sendEvent(PoolEvent.JOIN.toString(), flash);
                ns.getRoomOperations(revToRoom).sendEvent(PoolEvent.JOIN.toString(), revFlash);
            }
        });
        ns.addDisconnectListener(sioc -> {
            ns.getBroadcastOperations().sendEvent(PoolEvent.LEAVE.toString(), sioc.getSessionId().toString());
        });
    }

    private void attempStartServer() {
        if (mServer != null) {
            mServer.start();
            //
            System.out.println("Server started. Listening on: " + this.port);
        } else
            System.out.println("Failed to start server.");
    }

    private Configuration setUp() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);

        mRClient = RedisConfig.getRedisson();

        Configuration config = new Configuration();
        config.setPort(port);
        config.setStoreFactory(new RedissonStoreFactory(mRClient));
        config.setSocketConfig(socketConfig);
        config.setAuthorizationListener(new SocketAuthorizationListener());

        // config.setOrigin("http://localhost:8000");
        // config.setKeyStorePassword("");
        // InputStream stream = PoolServer.class.getResourceAsStream("/keystore.jks");
        // config.setKeyStore(stream);

        this.mRSetRoom = mRClient.getSet("rooms");
        this.mRMapClientStore = mRClient.getMap("clientStore");

        return mConfig = config;
    }

    private void cleanUp() {
        for (SocketIONamespace ns : mServer.getAllNamespaces()) {
            ns.removeAllListeners(PoolEvent.LOCATION.toString());
            ns.getBroadcastOperations().disconnect();
            this.mServer.removeNamespace(ns.getName());
        }
    }

    public void start() {

        System.out.println("Starting...");

        setServer(new SocketIOServer(setUp()));

        for (String ns : namespaces) {
            addListenerToNs(mServer.addNamespace(ns));
        }

        attempStartServer();
    }

    public void stop() {
        if (this.mServer != null) {
            this.cleanUp();
            this.mServer.stop();
            //
            System.out.println("Server stoped.");
        } else
            System.out.println("Failed to stop server.");
    }

    public ArrayList<String> getNamespaceNames() {
        return this.namespaces;
    }

    public static class Builder {
        private ArrayList<String> namespaces;
        private int port;

        public Builder() {

        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder addNamespace(String namespace) {
            if (namespaces == null)
                namespaces = new ArrayList<>();
            if (namespace.isEmpty() == false && namespaces.contains(namespace) == false)
                namespaces.add(namespace);
            return this;
        }

        public PoolServer build() {
            PoolServer poolServer = new PoolServer();
            poolServer.port = this.port;
            poolServer.namespaces = new ArrayList<>(this.namespaces);
            return poolServer;
        }
    }
}
