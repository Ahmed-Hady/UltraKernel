package com.ultradevs.ultrakernel.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by ahmedhady on 17/10/17.
 */

public class ShellExecuter {
    public static String command;

    public static final  String runAsRoot() {

        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean hasSelinux()
    {
        return new File("/sys/fs/selinux/enforce").isFile();
    }
}
