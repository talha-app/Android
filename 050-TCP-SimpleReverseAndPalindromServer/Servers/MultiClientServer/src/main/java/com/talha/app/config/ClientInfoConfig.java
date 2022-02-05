package com.talha.app.config;

import com.talha.app.ClientInfo;

import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClientInfoConfig {
    private static Map<Socket, ClientInfo> reverseClientsMap = Collections.synchronizedMap(new HashMap<>());
    private static Map<Socket, ClientInfo> palindromeClientsMap = Collections.synchronizedMap(new HashMap<>());

    public static Map<Socket, ClientInfo> getReverseClientsMap() {
        return reverseClientsMap;
    }

    public static Map<Socket, ClientInfo> getPalindromeClientsMap() {
        return palindromeClientsMap;
    }

}
