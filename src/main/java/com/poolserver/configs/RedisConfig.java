/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.configs;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author messenger
 */
public class RedisConfig {
    private static RedissonClient redisson;

    public static RedissonClient getRedisson() {
        return redisson;
    }

    static {
        // Instantiate Redisson configuration
        Config redissonConfig = new Config();
        redissonConfig
                .useSingleServer()
                .setAddress("redis://42.112.17.40:6379") // hardcode for testing
//                .setConnectionPoolSize(5)
        ;

        // Instantiate Redisson connection
        redisson = Redisson.create(redissonConfig);
    }
}
