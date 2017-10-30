package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import android.util.Log;

import com.ultradevs.ultrakernel.utils.RootUtils;
import com.ultradevs.ultrakernel.utils.utils;

import java.io.File;

import static com.ultradevs.ultrakernel.activities.InitActivity.LOG_TAG;

/**
 * Created by ahmedhady on 29/10/17.
 */

public class msmMPDutil {
    private static final String MPD_PATH = "/sys/kernel/msm_mpdecision/conf";
    private static final String MPD_ENABLE = MPD_PATH + "/enabled";
    private static final String MPD_HOTPLUG_SUSPEND = MPD_PATH + "/hotplug_suspend";
    private static final String MPD_HOTPLUG_MIN_CPUS_ONLINE = MPD_PATH + "/min_cpus_online";
    private static final String MPD_HOTPLUG_MAX_CPUS_ONLINE = MPD_PATH + "/max_cpus_online";

    public static boolean isAvailable() {
        if(new File(MPD_PATH).exists()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean getStatus() {
        if (utils.readFile(MPD_ENABLE, true).toString().contains("1")){
            return true;
        } else {
            return false;
        }
    }
    public static void setEnabled(boolean enable) {
        final Integer set;
        if(enable == true){
            set = 1;
        } else {
            set = 0;
        }
        RootUtils.runCommand("echo " + set.toString() + " > " + MPD_ENABLE);
    }
    public static int getMinOnline(){
        return utils.strToInt(utils.readFile(MPD_HOTPLUG_MIN_CPUS_ONLINE,true));
    }
    public static int getMaxOnline(){
        return utils.strToInt(utils.readFile(MPD_HOTPLUG_MAX_CPUS_ONLINE, true));
    }
}
