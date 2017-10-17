package com.ultradevs.ultrakernel.utils;

import java.io.IOException;

/**
 * Created by ahmedhady on 17/10/17.
 */

public class SystemInfoUtils {

    public static ShellExecuter mShell;

    public static String Android_Version() {
        mShell.command="getprop ro.build.version.release";
        return mShell.runAsRoot();
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
    public static String Android_Sdk_Version(){
        mShell.command="getprop ro.build.version.sdk";
        return mShell.runAsRoot();
    }
    public static String Android_system_patch_Version(){
        mShell.command="getprop ro.build.version.security_patch";
        return mShell.runAsRoot();
    }
    public static String Android_device_board(){
        mShell.command="getprop ro.product.board";
        return mShell.runAsRoot();
    }
    public static String Android_device_manuf(){
        mShell.command="getprop ro.product.manufacturer";
        return mShell.runAsRoot();
    }

    public static String Android_device_name(){
        mShell.command="getprop ro.product.model";
        return mShell.runAsRoot();
    }

    public static String Android_device_kernel(){
        mShell.command="cat /proc/version";
        return mShell.runAsRoot();
    }
}
