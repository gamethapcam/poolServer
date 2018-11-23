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
public class PrivateMessageObject {
    private String userName;
    private LatLng coordinates;
    private long timestamp;
    private String sockId;

    public String getSockId() {
        return sockId;
    }

    public void setSockId(String sockId) {
        this.sockId = sockId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public PrivateMessageObject() {

    }

    public PrivateMessageObject(String userName, String sockId, long timestamp, LatLng coordinates) {
        super();
        this.userName = userName;
        this.timestamp = timestamp;
        this.coordinates = coordinates;
        this.sockId = sockId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
