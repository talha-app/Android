package com.talha.app;

import com.talha.app.config.ClientInfoConfig;
import com.talha.app.util.SchedulerUtil;
import com.talha.app.util.StringUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PalindromeServer {
    private final int palindromeServerPort;
    private final ExecutorService threadPool;
    private final Map<Socket, ClientInfo> palindromeClientsMap = ClientInfoConfig.getPalindromeClientsMap();

    public PalindromeServer(int palindromeServerPort) {
        this.palindromeServerPort = palindromeServerPort;
        this.threadPool = Executors.newCachedThreadPool();
    }

    private void handlePalindromeThread(Socket socket) {
        try (socket) {
            var port = socket.getPort();
            var clientInfo = new ClientInfo(socket, port);
            palindromeClientsMap.put(socket, clientInfo);
            System.out.println("Palindrome client connected " + socket.getInetAddress().getHostAddress() + " " + socket.getPort());
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var dataOutputStream = new DataOutputStream(socket.getOutputStream());

            var str = bufferedReader.readLine();
            synchronized (palindromeClientsMap){
                if (!palindromeClientsMap.containsKey(port)){
                    return;
                }
                clientInfo.setLastUpdate(LocalDateTime.now());
            }
            if (str.charAt(str.length() - 1) == '\r')
                str = str.substring(0, str.length() - 1);
            Thread.sleep(1000);
            var result = StringUtil.isPalindrome(str);
            dataOutputStream.writeBoolean(result);

        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private void palindromeServerProc() {
        cleanUnusedPortsProc();
        System.out.println("Palindrome server started");
        try (var serverSocket = new ServerSocket(palindromeServerPort)) {
            while (true) {
                System.out.println("Palindrome server is waiting for a client " + palindromeServerPort);
                var clientSocket = serverSocket.accept();
                threadPool.submit(() -> handlePalindromeThread(clientSocket));

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private void cleanUnusedPortsProc() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                palindromeSchedulerCallback();
            }
        }, 0, 3000);
    }

    private void palindromeSchedulerCallback() {
        System.out.println("Reverse client size: "+palindromeClientsMap.size());
        synchronized (palindromeClientsMap){
            palindromeClientsMap.keySet().removeIf(key-> SchedulerUtil.isRemovable(key,palindromeClientsMap));
        }
    }

    public void run() throws InterruptedException, ExecutionException {
        var palindromeFuture = threadPool.submit(this::palindromeServerProc);
        palindromeFuture.get();
        threadPool.shutdown();
    }

}
