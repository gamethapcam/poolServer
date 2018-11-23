/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.model;

/**
 * @author messenger
 */
public class TrackingMessage {
    public TrackingMessage() {
    }

    public TrackingMessage(String[] sockId, String room) {
        this.sockId = sockId;
        this.room = room;
    }

    public String getSockIdAt(int idx) {
        return idx < this.sockId.length ? this.sockId[idx] : "";
    }

    public String[] getSockId() {
        return sockId;
    }

    public void setSockId(String[] sockId) {
        this.sockId = sockId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    private String[] sockId;
    private String room;
}
