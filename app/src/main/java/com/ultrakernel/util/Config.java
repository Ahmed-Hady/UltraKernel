package com.ultrakernel.util;

/**
 * Created by sumit on 20/12/16.
 */

public class Config {
    public static ShellExecuter mShell;
    public static String Android_OS_Version(){
        mShell.command="getprop ro.build.version.release";
        return mShell.runAsRoot();
    }
    public static String Android_Sdk_Version(){
        mShell.command="getprop ro.build.version.sdk";
        return mShell.runAsRoot();
    }
    public static String Android_system_patch_Version(){
        mShell.command="getprop ro.build.version.security_patch";
        return mShell.runAsRoot();
    }
    public static String Android_d_board(){
        mShell.command="getprop ro.product.board";
        return mShell.runAsRoot();
    }
    public static String Android_d_manuf(){
        mShell.command="getprop ro.product.manufacturer";
        return mShell.runAsRoot();
    }

    public static String Android_d_name(){
        mShell.command="getprop ro.product.model";
        return mShell.runAsRoot();
    }

    public static String Android_d_kernel(){
        mShell.command="cat /proc/version";
        return mShell.runAsRoot();
    }

}
