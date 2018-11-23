/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver;

/**
 * Handles a server-side channel.
 *
 * @author messenger
 * @author messenger
 * @author messenger
 */
/**
 *
 * @author messenger
 */

import com.poolserver.server.PoolServer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int port = 8443;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        PoolServer ps = new PoolServer.Builder()
                .addNamespace("/")
                .addNamespace("/fm")
                .setPort(port)
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> ps.stop()));

        ps.start();

        System.out.print("press Enter to shutdown server.");
        new Scanner(System.in).nextLine();
        System.exit(0);
    }
}
