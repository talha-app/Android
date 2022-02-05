package com.talha.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sender {
    private final int port;
    private final String host;
    private static final Scanner scanner = new Scanner(System.in);

    public Sender(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() {
        try (var datagramSocket = new DatagramSocket()) {
            while (true) {
                System.out.println("Please write a text");
                var text = scanner.nextLine();
                if (text.equals("quit"))
                    break;
                var buf = text.getBytes(StandardCharsets.UTF_8);
                var datagramPacket = new DatagramPacket(buf, 0, buf.length, InetAddress.getByName(host), port);
                datagramSocket.send(datagramPacket);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (Throwable ex) {
            System.err.println(ex.getMessage());
        }
    }
}
