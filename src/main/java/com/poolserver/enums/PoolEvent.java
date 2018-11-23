/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.enums;

/**
 * @author messenger
 */
public enum PoolEvent {
    LOCATION("location"),

    CONNECTED("connected"),

    JOIN("join"),
    LEAVE("leave"),

    CREATE_ROOM("create_room"),
    JOIN_ROOM("join_room"),
    LEAVE_ROOM("leave_room"),

    PRIVATE("private"),

    TRACKING("tracking"),
    TRACKING_INVITE("tracking_invite"),
    TRACKING_INVITE_SENT("tracking_invite_sent"),
    TRACKING_INVITE_ERROR("tracking_invite_error"),
    TRACKING_ACCEPT("tracking_accept"),
    TRACKING_DENIED("tracking_denied"),
    TRACKING_ESTABLISHED("tracking_established");

    private final String value;

    private PoolEvent(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
