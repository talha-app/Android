package com.talha.app.util;

import com.talha.app.ClientInfo;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public final class SchedulerUtil {
    public static boolean isRemovable(Socket key, Map<Socket, ClientInfo> clients)
    {
        var now = LocalDateTime.now();
        var ci = clients.get(key);

        var  status = ChronoUnit.SECONDS.between(ci.getLastUpdate(), now) > 3;

        try {
            if (status)
                ci.getSocket().close();
        }
        catch (IOException ignore) {

        }

        return status;
    }
}
