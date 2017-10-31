package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import android.content.Context;

import com.ultradevs.ultrakernel.utils.RootUtils;
import com.ultradevs.ultrakernel.utils.prefs;
import com.ultradevs.ultrakernel.utils.utils;

import java.io.File;

/**
 * Created by ahmedhady on 29/10/17.
 */

public class AlucardUtils {
    private static final String ALUCARD_HOTPLUG = "/sys/kernel/alucard_hotplug";
    private static final String ALUCARD_HOTPLUG_ENABLE = ALUCARD_HOTPLUG + "/hotplug_enable";
    private static final String ALUCARD_HOTPLUG_MIN_CPUS_ONLINE = ALUCARD_HOTPLUG + "/min_cpus_online";
    private static final String ALUCARD_HOTPLUG_MAX_CORES_LIMIT = ALUCARD_HOTPLUG + "/maxcoreslimit";
    private static final String ALUCARD_HOTPLUG_SUSPEND = ALUCARD_HOTPLUG + "/hotplug_suspend";

    public static boolean isAvailable() {
        if(new File(ALUCARD_HOTPLUG).exists()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean getStatus() {
        if (utils.readFile(ALUCARD_HOTPLUG_ENABLE, true).toString().contains("1")){
            return true;
        } else {
            return false;
        }
    }

    public static int getMinOnline(){
        return utils.strToInt(utils.readFile(ALUCARD_HOTPLUG_MIN_CPUS_ONLINE));
    }

    public static int getMaxOnline(){
        return utils.strToInt(utils.readFile(ALUCARD_HOTPLUG_MAX_CORES_LIMIT));
    }

    public static boolean getSuspend(){
        if(utils.readFile(ALUCARD_HOTPLUG_SUSPEND).contains("1")){
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
        utils.writeFile(ALUCARD_HOTPLUG_ENABLE, set.toString());
        prefs.putBoolean("alucard_onboot", enable, context);
    }

    public static void setMinOnline(int value, Context context){
        utils.writeFile(ALUCARD_HOTPLUG_MIN_CPUS_ONLINE, value);
        prefs.putInt("alucard_min_online", value, context);
    }

    public static void setMaxOnline(int value, Context context){
        utils.writeFile(ALUCARD_HOTPLUG_MAX_CORES_LIMIT, value);
        prefs.putInt("alucard_max_online", value, context);
    }

    public static void setSuspend(boolean value, Context context){
        int set;
        if(value == true){
            set = 1;
        } else {
            set = 0;
        }
        utils.writeFile(ALUCARD_HOTPLUG_SUSPEND, set);
        prefs.putBoolean("alucard_suspend", value, context);
    }
}
