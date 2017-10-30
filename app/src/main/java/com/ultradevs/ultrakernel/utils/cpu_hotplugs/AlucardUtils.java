package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import com.ultradevs.ultrakernel.utils.RootUtils;
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

    public static void setEnabled(boolean enable) {
        final Integer set;
        if(enable == true){
            set = 1;
        } else {
            set = 0;
        }
        RootUtils.runCommand("echo " + set.toString() + " > " + ALUCARD_HOTPLUG_ENABLE);
    }

    public static void setMinOnline(int value){
        RootUtils.runCommand("echo " + value + " > " + ALUCARD_HOTPLUG_MIN_CPUS_ONLINE);
    }

    public static void setMaxOnline(int value){
        RootUtils.runCommand("echo " + value + " > " + ALUCARD_HOTPLUG_MAX_CORES_LIMIT);
    }

    public static void setSuspend(boolean value){
        int set;
        if(value == true){
            set = 1;
        } else {
            set = 0;
        }
        RootUtils.runCommand("echo " + set + " > " + ALUCARD_HOTPLUG_SUSPEND);
    }
}
