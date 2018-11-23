/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.enums;

/**
 * @author messenger
 */
public enum PoolClientType {
    DRIVER("driver"),
    RIDER("rider");

    private final String value;

    private PoolClientType(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
