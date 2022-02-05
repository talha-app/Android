package com.talha.app;

import java.net.Socket;
import java.time.LocalDateTime;

public class ClientInfo {
    private Socket m_socket;
    private LocalDateTime m_lastUpdate = LocalDateTime.now();
    private int m_port;

    public ClientInfo(Socket socket, int port) {
        m_socket = socket;
        m_port = port;
    }

    public Socket getSocket() {
        return m_socket;
    }

    public void setSocket(Socket socket) {
        m_socket = socket;
    }

    public LocalDateTime getLastUpdate() {
        return m_lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        m_lastUpdate = lastUpdate;
    }

    public int getPort() {
        return m_port;
    }

    public void setPort(int port) {
        m_port = port;
    }
}
