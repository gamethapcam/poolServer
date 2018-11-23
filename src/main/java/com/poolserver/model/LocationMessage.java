/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.model;

import com.poolserver.gis.LatLng;
import reactor.util.annotation.NonNull;

/**
 * @author messenger
 */
public class LocationMessage {
    public LocationMessage() {
    }

    public LocationMessage(
            @NonNull String sessionId,
            @NonNull String type,
            @NonNull ClientMetaData clientMetaData,
            @NonNull LatLng clientLatLng,
            @NonNull String locationName,
            @NonNull LatLng destinationLatLng,
            @NonNull String destination, long timestamp) {
        super();
        //
        this.sessionId = sessionId;
        this.type = type;
        this.clientMetaData = clientMetaData;
        this.clientLatLng = clientLatLng;
        this.locationName = locationName;
        this.destinationLatLng = destinationLatLng;
        this.destination = destination;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ClientMetaData getClientMetaData() {
        return clientMetaData;
    }

    public void setClientMetaData(ClientMetaData clientMetaData) {
        this.clientMetaData = clientMetaData;
    }

    public LatLng getClientLatLng() {
        return clientLatLng;
    }

    public void setClientLatLng(LatLng clientLatLng) {
        this.clientLatLng = clientLatLng;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LatLng getDestinationLatLng() {
        return destinationLatLng;
    }

    public void setDestinationLatLng(LatLng destinationLatLng) {
        this.destinationLatLng = destinationLatLng;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private String sessionId;
    private String type;
    private ClientMetaData clientMetaData;
    private LatLng clientLatLng;
    private String locationName;
    private LatLng destinationLatLng;
    private String destination;
    private long timestamp;
}
