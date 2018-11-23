/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.enums;

/**
 * @author messenger
 */
public enum PoolRoom {
    DRIVER_ROOM("driver_room"),
    RIDER_ROOM("rider_room");

    private final String value;

    private PoolRoom(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
