package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import android.content.Context;

import com.ultradevs.ultrakernel.utils.RootUtils;
import com.ultradevs.ultrakernel.utils.prefs;
import com.ultradevs.ultrakernel.utils.utils;

import java.io.File;

/**
 * Created by ahmedhady on 29/10/17.
 */

public class MpdUtils {
    private static final String MPD_HOTPLUG = "/sys/kernel/msm_mpdecision/conf";
    private static final String MPD_HOTPLUG_ENABLE = MPD_HOTPLUG + "/enabled";
    private static final String MPD_HOTPLUG_MIN_CPUS_ONLINE = MPD_HOTPLUG + "/min_cpus_online";
    private static final String MPD_HOTPLUG_MAX_CORES_LIMIT = MPD_HOTPLUG + "/max_cpus_online";
    private static final String MPD_HOTPLUG_SUSPEND = MPD_HOTPLUG + "/hotplug_suspend";

    public static boolean isAvailable() {
        if(new File(MPD_HOTPLUG).exists()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean getStatus() {
        if (utils.readFile(MPD_HOTPLUG_ENABLE, true).toString().contains("1")){
            return true;
        } else {
            return false;
        }
    }

    public static int getMinOnline(){
        return utils.strToInt(utils.readFile(MPD_HOTPLUG_MIN_CPUS_ONLINE));
    }

    public static int getMaxOnline(){
        return utils.strToInt(utils.readFile(MPD_HOTPLUG_MAX_CORES_LIMIT));
    }

    public static boolean getSuspend(){
        if(utils.readFile(MPD_HOTPLUG_SUSPEND).contains("1")){
            return true;
        } else {
            return false;
        }
    }

    public static void setEnabled(boolean enable, Context context) {
        final Integer set;
        if(enable == true){
            set = 1;
        } else {
            set = 0;
        }
        utils.writeFile(MPD_HOTPLUG_ENABLE, set.toString());
        prefs.putBoolean("mpd_onboot", enable, context);
    }

    public static void setMinOnline(int value, Context context){
        utils.writeFile(MPD_HOTPLUG_MIN_CPUS_ONLINE, value);
        prefs.putInt("mpd_min_online", value, context);
    }

    public static void setMaxOnline(int value, Context context){
        utils.writeFile(MPD_HOTPLUG_MAX_CORES_LIMIT, value);
        prefs.putInt("mpd_max_online", value, context);
    }

    public static void setSuspend(boolean value, Context context){
        int set;
        if(value == true){
            set = 1;
        } else {
            set = 0;
        }
        utils.writeFile(MPD_HOTPLUG_SUSPEND, set);
        prefs.putBoolean("mpd_suspend", value, context);
    }
}
