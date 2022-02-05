package com.talha.app;

import com.talha.app.util.StringUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int reverseServerPort;
    private final int palindromeServerPort;

    public Server(int reverseServerPort, int palindromServerPort) {
        this.reverseServerPort = reverseServerPort;
        this.palindromeServerPort = palindromServerPort;
    }

    private void handleReverseThread(Socket socket) {
        try (socket) {
            System.out.println("Reverse client connected " + socket.getInetAddress().getHostAddress() + " " + socket.getPort());
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            var str = bufferedReader.readLine();
            if (str.charAt(str.length()- 1) == '\r')
                str = str.substring(0, str.length() - 1);
            str = new StringBuilder(str).reverse().toString();
            bufferedWriter.write(str);
            bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handlePalindromeThread(Socket socket) {
        try (socket) {
            System.out.println("Palindrome client connected " + socket.getInetAddress().getHostAddress() + " " + socket.getPort());
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var dataOutputStream = new DataOutputStream(socket.getOutputStream());

            var str = bufferedReader.readLine();
            if (str.charAt(str.length()- 1) == '\r')
                str = str.substring(0, str.length() - 1);

            var result = StringUtil.isPalindrome(str);
            dataOutputStream.writeBoolean(result);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void palindromeServerProc() {
        System.out.println("Palindrome server started");
        try (var serverSocket = new ServerSocket(palindromeServerPort)) {
            while (true) {
                System.out.println("Palindrome server is waiting for a client " + palindromeServerPort);
                var clientSocket = serverSocket.accept();
                handlePalindromeThread(clientSocket);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private void reverseServerProc() {
        System.out.println("Reverse server started");
        try (var serverSocket = new ServerSocket(reverseServerPort)) {
            while (true) {
                System.out.println("Reverse server is waiting for a client " + reverseServerPort);
                var clientSocket = serverSocket.accept();
                handleReverseThread(clientSocket);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public void run() throws InterruptedException {
        var palindromeThread = new Thread(this::palindromeServerProc);
        var reverseThread = new Thread(this::reverseServerProc);

        palindromeThread.start();
        reverseThread.start();

        reverseThread.join();
        palindromeThread.join();

    }
}
