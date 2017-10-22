package com.ultradevs.ultrakernel.utils;

import android.os.Bundle;
import android.util.Log;

import com.stericson.RootTools.BuildConfig;
import com.stericson.RootTools.RootTools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

    public static boolean hasSelinux()
    {
        return new File("/sys/fs/selinux/enforce").isFile();
    }

    //ROOT UTILS

    public static void getRoot(){
        RootTools.isAccessGiven();
    }

        private static Bundle system(String shell, String command) {
            ArrayList<String> res = new ArrayList<String>();
            ArrayList<String> err = new ArrayList<String>();
            boolean success = false;
            try {
                Process process = Runtime.getRuntime().exec(shell);
                DataOutputStream STDIN = new DataOutputStream(process.getOutputStream());
                BufferedReader STDOUT = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader STDERR = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                if (BuildConfig.DEBUG) Log.i(shell, command);
                STDIN.writeBytes(command + "\n");
                STDIN.flush();
                STDIN.writeBytes("exit\n");
                STDIN.flush();
                process.waitFor();
                if (process.exitValue() == 255) {
                    if (BuildConfig.DEBUG) Log.e(shell,"SU was probably denied! Exit value is 255");
                    err.add("SU was probably denied! Exit value is 255");
                }

                while (STDOUT.ready()) {
                    String read = STDOUT.readLine();
                    if (BuildConfig.DEBUG) Log.d(shell, read);
                    res.add(read);
                }
                while (STDERR.ready()) {
                    String read = STDERR.readLine();
                    if (BuildConfig.DEBUG) Log.e(shell, read);
                    err.add(read);
                }

                process.destroy();
                success = true;
                if (err.size() > 0) {
                    success = false;
                }
            } catch (IOException e) {
                if (BuildConfig.DEBUG) Log.e(shell,"IOException: "+e.getMessage());
                err.add("IOException: "+e.getMessage());
            } catch (InterruptedException e) {
                if (BuildConfig.DEBUG) Log.e(shell,"InterruptedException: "+e.getMessage());
                err.add("InterruptedException: "+e.getMessage());
            }
            if (BuildConfig.DEBUG) Log.d(shell,"END");
            Bundle r = new Bundle();
            r.putBoolean("success", success);
            r.putString("cmd", command);
            r.putString("binary", shell);
            r.putStringArrayList("out", res);
            r.putStringArrayList("error", err);
            return r;
    }

    private static String getSuBin() {
        if (new File("/system/xbin","su").exists()) {
            return "/system/xbin/su";
        }
        if (RootTools.isRootAvailable()) {
            return "su";
        }
        return "sh";
    }

    public static String shell(String cmd, boolean root) {
        String out = "";
        ArrayList<String> r = system(root ? getSuBin() : "sh",cmd).getStringArrayList("out");
        for(String l: r) {
            out += l+"\n";
        }
        return out;
    }

    public static String isRoot(){
        boolean checkroot = RootTools.isRootAvailable();
        if(checkroot==true){
            return "Rooted";
        } else {
            return "Not Rooted";
        }
    }
}
