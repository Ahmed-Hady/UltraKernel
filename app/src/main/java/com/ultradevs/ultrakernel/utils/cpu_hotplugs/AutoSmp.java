package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import com.ultradevs.ultrakernel.utils.RootUtils;
import com.ultradevs.ultrakernel.utils.utils;

import java.io.File;

/**
 * Created by ahmedhady on 29/10/17.
 */

public class AutoSmp {
    private static final String HOTPLUG_AUTOSMP_PARAMETERS = "/sys/module/autosmp/parameters";
    private static final String HOTPLUG_AUTOSMP_ENABLE = HOTPLUG_AUTOSMP_PARAMETERS + "/enabled";

    public static boolean isAvailable() {
        if(new File(HOTPLUG_AUTOSMP_PARAMETERS).exists()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean getStatus() {
        if (utils.readFile(HOTPLUG_AUTOSMP_ENABLE, true).toString().contains("Y")){
            return true;
        } else {
            return false;
        }
    }
    public static void setEnaled(boolean enable) {
        final String set;
        if(enable == true){
            set = "Y";
        } else {
            set = "N";
        }
        RootUtils.runCommand("echo " + set + " > " + HOTPLUG_AUTOSMP_ENABLE);
    }
}
