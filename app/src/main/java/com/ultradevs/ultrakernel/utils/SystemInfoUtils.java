package com.ultradevs.ultrakernel.utils;

import android.os.Build;

import com.stericson.RootTools.RootTools;

import java.io.IOException;

/**
 * Created by ahmedhady on 17/10/17.
 */

public class SystemInfoUtils {

    private static ShellExecuter mShell;

    public static String Android_Version() {
        return Build.VERSION.RELEASE;
    }
    public static String Android_Name() {
        if (Android_Version().startsWith("4.4")){
            return "KitKat";
        }
        if (Android_Version().startsWith("5")){
            return "Lollipop";
        }
        if (Android_Version().startsWith("6")){
            return "Marshmallow";
        }
        if (Android_Version().startsWith("7")){
            return "Nougat";
        }
        if (Android_Version().startsWith("8")){
            return "Oreo";
        }
        return null;
    }
    public static int Android_Sdk_Version(){
        if (Android_Name() == "Lollipop"){
            mShell.command="getprop ro.build.version.sdk";
            return Integer.parseInt(mShell.runAsRoot());
        } else {
            return Integer.parseInt(Build.VERSION.SDK);
        }
    }
    public static String Android_system_patch_Version(){
        return Build.VERSION.SECURITY_PATCH;
    }
    public static String Android_device_board(){
        return Build.BOARD;
    }
    public static String Android_device_manuf(){
        return Build.MANUFACTURER;
    }

    public static String Android_device_name(){
        return Build.MODEL + " (" + Build.DEVICE + ")";
    }

    public static String Android_device_kernel(){
        return System.getProperty("os.version");
    }
    public static String Android_Bootloader(){
        return Build.BOOTLOADER;
    }
    public static String Android_ABI(){
        return Build.CPU_ABI;
    }
    public static String Android_RadioVersion(){
        return Build.getRadioVersion();
    }
}
