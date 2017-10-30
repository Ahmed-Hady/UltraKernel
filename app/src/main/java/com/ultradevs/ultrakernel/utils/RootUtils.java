package com.ultradevs.ultrakernel.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Based On RootUtils.java of Kernel Auditor
 */
public class RootUtils {

    private static SU su;

    public static boolean rootAccess() {
        SU su = getSU();
        su.runCommand("echo /testRoot/");
        return !su.denied;
    }

    public static boolean busyboxInstalled() {
        return existBinary("busybox") || existBinary("toybox");
    }

    private static boolean existBinary(String binary) {
        String paths;
        if (System.getenv("PATH") != null) {
            paths = System.getenv("PATH");
        } else {
            paths = "/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin";
        }
        for (String path : paths.split(":")) {
            if (!path.endsWith("/")) path += "/";
            if (utils.existFile(path + binary, false) || utils.existFile(path + binary)) {
                return true;
            }
        }
        return false;
    }


    public static void closeSU() {
        if (su != null) su.close();
        su = null;
    }

    public static String runCommand(String command) {
        return getSU().runCommand(command);
    }

    public static SU getSU() {
        if (su == null || su.closed || su.denied) {
            if (su != null && !su.closed) {
                su.close();
            }
            su = new SU();
        }
        return su;
    }

    /*
     * Based on AndreiLux's SU code in Synapse
     * https://github.com/AndreiLux/Synapse/blob/master/src/main/java/com/af/synapse/utils/Utils.java#L238
     */
    public static class SU {

        private Process mProcess;
        private BufferedWriter mWriter;
        private BufferedReader mReader;
        private final boolean mRoot;
        private final String mTag;
        private boolean closed;
        private boolean denied;
        private boolean firstTry;

        public SU() {
            this(true, null);
        }

        public SU(boolean root, String tag) {
            mRoot = root;
            mTag = tag;
            try {
                if (mTag != null) {
                    Log.i(mTag, String.format("%s initialized", root ? "SU" : "SH"));
                }
                firstTry = true;
                mProcess = Runtime.getRuntime().exec(root ? "su" : "sh");
                mWriter = new BufferedWriter(new OutputStreamWriter(mProcess.getOutputStream()));
                mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            } catch (IOException e) {
                if (mTag != null) {
                    Log.e(mTag, root ? "Failed to run shell as su" : "Failed to run shell as sh");
                }
                denied = true;
                closed = true;
            }
        }

        public synchronized String runCommand(final String command) {
            synchronized (this) {
                try {
                    StringBuilder sb = new StringBuilder();
                    String callback = "/shellCallback/";
                    mWriter.write(command + "\necho " + callback + "\n");
                    mWriter.flush();

                    int i;
                    char[] buffer = new char[256];
                    while (true) {
                        sb.append(buffer, 0, mReader.read(buffer));
                        if ((i = sb.indexOf(callback)) > -1) {
                            sb.delete(i, i + callback.length());
                            break;
                        }
                    }
                    firstTry = false;
                    if (mTag != null) {
                        Log.i(mTag, "run: " + command + " output: " + sb.toString().trim());
                    }

                    return sb.toString().trim();
                } catch (IOException e) {
                    closed = true;
                    e.printStackTrace();
                    if (firstTry) denied = true;
                } catch (ArrayIndexOutOfBoundsException e) {
                    denied = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    denied = true;
                }
                return null;
            }
        }

        public void close() {
            try {
                if (mWriter != null) {
                    mWriter.write("exit\n");
                    mWriter.flush();

                    mWriter.close();
                }
                if (mReader != null) {
                    mReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mProcess != null) {
                try {
                    mProcess.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mProcess.destroy();
                if (mTag != null) {
                    Log.i(mTag, String.format("%s closed: %d", mRoot ? "SU" : "SH", mProcess.exitValue()));
                }
            }
            closed = true;
        }

    }

}