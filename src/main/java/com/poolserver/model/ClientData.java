/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.model;

import com.poolserver.gis.LatLng;

/**
 * @author messenger
 */
public class ClientData {

    private String type;
    private String sessionId;
    private ClientMetaData clientMetaData;
    private LatLng lastLatLng;

    public ClientMetaData getClientMetaData() {
        return clientMetaData;
    }

    public void setClientMetaData(ClientMetaData clientMetaData) {
        this.clientMetaData = clientMetaData;
    }

    public LatLng getLastLatLng() {
        return lastLatLng;
    }

    public void setLastLatLng(LatLng lastLatLng) {
        this.lastLatLng = lastLatLng;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ClientData() {
    }

    public ClientData(String type, String sessionId, ClientMetaData clientMetaData, LatLng lastLatLng) {
        this.type = type;
        this.sessionId = sessionId;
        this.clientMetaData = clientMetaData;
        this.lastLatLng = lastLatLng;
    }
}
