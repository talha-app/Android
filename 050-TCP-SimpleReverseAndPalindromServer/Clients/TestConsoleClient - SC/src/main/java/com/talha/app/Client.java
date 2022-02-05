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
            System.out.println("2. Exit");
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
                System.exit(1);
                break;
            default:
                System.err.println("Invalid option");
                break;
        }
    }

    private void reverseServerProc() {

        try (var socket = new Socket(serverIP, reverseServerPort)) {
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            for (; ; ) {
                System.out.println("bir yazı giriniz");
                var str = scanner.next();
                bufferedWriter.write(str + "\r\n");
                bufferedWriter.flush();
                if ("<quit>".equals(str.trim()))
                    break;
                str = bufferedReader.readLine();
                System.out.println("recieved data: " + str);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void run() throws InterruptedException {
        displayMenu();
    }
}
