package com.talha.sample.application.rawresource;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ResourcesUtil {
    private ResourcesUtil() {

    }

    public static void copyRawResource(Context context, int resId, String path) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        FileOutputStream fis = new FileOutputStream(path);
        copy(is, fis, 1024, true);
    }

    public static void copyRawResourceToFilesDir(Context context, int resId, String fileName) throws IOException {
        copyRawResource(context, resId, new File(context.getFilesDir(), fileName).getAbsolutePath());
    }

    public static void copy(InputStream src, OutputStream dest, int blockSize, boolean flush) throws IOException {
        byte[] buf = new byte[blockSize];

        int read;
        while ((read = src.read(buf)) > 0) {
            dest.write(buf, 0, read);
        }

        if (flush) {
            dest.flush();
        }

    }
}
