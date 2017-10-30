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
    public static void setEnaled(boolean enable) {
        final Integer set;
        if(enable == true){
            set = 1;
        } else {
            set = 0;
        }
        RootUtils.runCommand("echo " + set.toString() + " > " + ALUCARD_HOTPLUG_ENABLE);
    }
}
