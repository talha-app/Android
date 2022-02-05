package com.talha.app;

import com.talha.app.config.ClientInfoConfig;
import com.talha.app.util.SchedulerUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReverseServer {
    private final int reverseServerPort;
    private final ExecutorService threadPool;
    private final Map<Socket, ClientInfo> reverseClientsMap = ClientInfoConfig.getReverseClientsMap();
    private final String reverseQuitCommit = "<quit>";

    public ReverseServer(int reverseServerPort) {
        this.reverseServerPort = reverseServerPort;

        this.threadPool = Executors.newCachedThreadPool();
    }

    private synchronized void setLastUpdate(Socket socket, ClientInfo clientInfo) {
        if (!reverseClientsMap.containsKey(socket))
            return;
        clientInfo.setLastUpdate(LocalDateTime.now());
    }


    private void handleReverseThread(Socket socket) {
        try (socket) {
            int port = socket.getPort();
            var clientInfo = new ClientInfo(socket, port);
            reverseClientsMap.put(socket, clientInfo);
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            for (; ; ) {
                var str = bufferedReader.readLine();
                if (str.trim().equals(reverseQuitCommit))
                    break;
                setLastUpdate(socket, clientInfo);
                if (str.charAt(str.length() - 1) == '\r')
                    str = str.substring(0, str.length() - 1);
                str = new StringBuilder(str).reverse().toString();
                bufferedWriter.write(str+"\r\n");
                bufferedWriter.flush();
                setLastUpdate(socket, clientInfo);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            reverseClientsMap.remove(socket);
        }
    }

    private void reverseServerProc() {
        cleanUnusedPortsProc();
        System.out.println("Reverse server started");
        try (var serverSocket = new ServerSocket(reverseServerPort)) {
            while (true) {
                System.out.println("Reverse server is waiting for a client " + reverseServerPort);
                var clientSocket = serverSocket.accept();
                threadPool.submit(() -> {
                    handleReverseThread(clientSocket);
                });
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void cleanUnusedPortsProc() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                reverseSchedulerCallback();
            }
        }, 0, 10000);
    }

    private void reverseSchedulerCallback() {
        System.out.println("Reverse client size: " + reverseClientsMap.size());
        synchronized (this) {
            reverseClientsMap.keySet().removeIf(key -> SchedulerUtil.isRemovable(key, reverseClientsMap));
        }
    }

    public void run() throws InterruptedException, ExecutionException {
        var reverseFuture = threadPool.submit(this::reverseServerProc);
    }

}
