package com.talha.app;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("wrong number of arguments");
            System.exit(-1);
        }

        try {
            var sender = new Sender(Integer.parseInt(args[0]), args[1]);
            sender.run();
        } catch (NumberFormatException ex) {
            System.err.println(ex.getMessage() + "NumberFormatException");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
