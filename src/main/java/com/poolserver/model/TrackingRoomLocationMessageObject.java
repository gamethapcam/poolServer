/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.model;

/**
 * @author messenger
 */
public class TrackingRoomLocationMessageObject {
    private LocationMessage location;

    public TrackingRoomLocationMessageObject(LocationMessage location, String room) {
        this.location = location;
        this.room = room;
    }

    public TrackingRoomLocationMessageObject() {
    }

    public LocationMessage getLocation() {
        return location;
    }

    public void setLocation(LocationMessage location) {
        this.location = location;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    private String room;
}
