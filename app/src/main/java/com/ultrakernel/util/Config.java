package com.ultrakernel.util;

import android.content.Context;
import android.content.SharedPreferences;

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

    public static String get_l(){
        mShell.command = "cat /sys/class/leds/charging/max_brightness";
        return mShell.runAsRoot();
    }

    private static Context mContext;

    public static String getStringPreferences(String Name){
        String o;
        SharedPreferences settings = mContext.getSharedPreferences(Name, 0); // 0 - for private mode
        o=settings.getString(Name,null);
        return o;
    }

    public static String get_d(){
        mShell.command = "su -c cat " + getStringPreferences("d2w");
        return mShell.runAsRoot();
    }

    public static final String LGE_TOUCH_DT2W = "/sys/devices/virtual/input/lge_touch/dt_wake_enabled";
    public static final String LGE_TOUCH_CORE_DT2W = "/sys/module/lge_touch_core/parameters/doubletap_to_wake";
    public static final String LGE_TOUCH_GESTURE = "/sys/devices/virtual/input/lge_touch/touch_gesture";
    public static final String ANDROID_TOUCH_DT2W = "/sys/android_touch/doubletap2wake";
    public static final String ANDROID_TOUCH2_DT2W = "/sys/android_touch2/doubletap2wake";
    public static final String TOUCH_PANEL_DT2W = "/proc/touchpanel/double_tap_enable";
    public static final String DT2W_WAKEUP_GESTURE = "/sys/devices/virtual/input/input1/wakeup_gesture";
    public static final String DT2W_ENABLE = "/sys/devices/platform/s3c2440-i2c.3/i2c-3/3-004a/dt2w_enable";
    public static final String DT2W_WAKE_GESTURE = "/sys/devices/platform/spi-tegra114.2/spi_master/spi2/spi2.0/input/input0/wake_gesture";
    public static final String DT2W_WAKE_GESTURE_2 = "/sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture";
    public static final String DT2W_FT5X06 = "/sys/bus/i2c/drivers/ft5x06_i2c/5-0038/d2w_switch";
    public static final String UpdaterUrl = "https://raw.githubusercontent.com/Ahmed-Hady/UltraKernel/master/Updater.xml";
    public static final String FORCE_FAST_CHARGE = "/sys/kernel/fast_charge/force_fast_charge";
    public static final String ARCH_POWER = "/sys/kernel/sched/arch_power";
    public final static String PATH_CPUS = "/sys/devices/system/cpu";
    public static final String ALUCARD_HOTPLUG = "/sys/kernel/alucard_hotplug";
    public static final String HOTPLUG_MSM = "/sys/module/msm_hotplug";

}
