package com.talha.app;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final int reverseServerPort;
    private final int palindromeServerPort;
    private final String serverIP;
    Scanner scanner = new Scanner(System.in);

    public Client(int reverseServerPort, int palindromeServerPort, String serverIP) {
        this.reverseServerPort = reverseServerPort;
        this.palindromeServerPort = palindromeServerPort;
        this.serverIP = serverIP;
    }

    private void displayMenu() {
        for (; ; ) {
            System.out.println("1. Reverse");
            System.out.println("2. Palindrome");
            System.out.println("3. ReverseBlock");
            System.out.println("4. PalindromeBlock");
            System.out.println("5. Exit");
            System.out.println("Select option");
            int option = scanner.nextInt();
            try {
                doWorkForOption(option);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    private void doWorkForOption(int option) {
        switch (option) {
            case 1:
                reverseServerProc();
                break;
            case 2:
                palindromeServerProc();
                break;
            case 3:
                reverseServerBlockProc();
                break;
            case 4:
                palindromeServerBlockProc();
                break;
            case 5:
                System.exit(1);
                break;
            default:
                System.err.println("Invalid option");
                break;
        }
    }

    private void reverseServerProc() {
        System.out.println("bir yazı giriniz");
        var str = scanner.next();

        try (var socket = new Socket(serverIP, reverseServerPort)) {
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write(str + "\r\n");
            bufferedWriter.flush();
            str = bufferedReader.readLine();
            System.out.println("recieved data: " + str);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void reverseServerBlockProc() {
        try (var socket = new Socket(serverIP, reverseServerPort)) {
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("bir yazı giriniz");
            var str = scanner.next();
            bufferedWriter.write(str + "\r\n");
            bufferedWriter.flush();
            str = bufferedReader.readLine();
            System.out.println("recieved data: " + str);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void palindromeServerProc() {
        System.out.println("bir yazı giriniz");
        var str = scanner.next();
        try (var socket = new Socket(serverIP, palindromeServerPort)) {
            var dataInputStream = new DataInputStream(socket.getInputStream());
            var bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write(str + "\r\n");
            bufferedWriter.flush();
            boolean isPalindrome = dataInputStream.readBoolean();
            System.out.println("recieved data: " + isPalindrome);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void palindromeServerBlockProc() {
        System.out.println("bir yazı giriniz");
        var str = scanner.next();
        try (var socket = new Socket(serverIP, palindromeServerPort)) {
            var dataInputStream = new DataInputStream(socket.getInputStream());
            var bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            scanner.next();
            bufferedWriter.write(str + "\r\n");
            bufferedWriter.flush();
            boolean isPalindrome = dataInputStream.readBoolean();
            System.out.println("recieved data: " + isPalindrome);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void run() throws InterruptedException {
        displayMenu();

    }
}
