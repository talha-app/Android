package com.talha.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class Receiver {
    private static final int BUFFER_SIZE = 1024;
    private final int port;

    public Receiver(int port) {
        this.port = port;
    }

    public void run() {
        try (var datagramSocket = new DatagramSocket(port)) {
            var buf = new byte[BUFFER_SIZE];
            while (true) {
                System.out.println("Waiting for a sender");
                var datagramPacket = new DatagramPacket(buf, buf.length);

                datagramSocket.receive(datagramPacket);
                var length = datagramPacket.getLength();
                var text = new String(datagramPacket.getData(), 0, length, StandardCharsets.UTF_8);
                var hostAddress = datagramPacket.getAddress().getHostAddress();
                var port = datagramPacket.getPort();
                System.out.println(text + "-" + hostAddress + "-" + port);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (Throwable ex) {
            System.err.println(ex.getMessage());
        }

    }
}
