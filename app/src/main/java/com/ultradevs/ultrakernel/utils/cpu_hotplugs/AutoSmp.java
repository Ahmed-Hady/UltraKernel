package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import java.io.File;

import static com.ultradevs.ultrakernel.utils.ShellExecuter.shell;

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
        if (shell("cat " + HOTPLUG_AUTOSMP_ENABLE, true).toString().contains("Y")){
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
        shell("echo " + set + " > " + HOTPLUG_AUTOSMP_ENABLE, true);
    }
}
