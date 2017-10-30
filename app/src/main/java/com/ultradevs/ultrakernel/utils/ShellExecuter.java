package com.ultradevs.ultrakernel.utils;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by ahmedhady on 17/10/17.
 */

public class ShellExecuter {
    public static String command;

    public static final  String runAsRoot() {
        try {
            Process p = Runtime.getRuntime().exec(command);
            InputStream is = null;
            if (p.waitFor() == 0) {
                is = p.getInputStream();
            } else {
                is = p.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is),
                    4096);
            String line = br.readLine();
            br.close();
            return line;
        } catch (Exception ex) {
            return "ERROR: " + ex.getMessage();
        }
    }

    public static String isRoot(){
        boolean checkroot = RootUtils.rootAccess();
        if(checkroot==true){
            return "Rooted";
        } else {
            return "Not Rooted";
        }
    }
}
