package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import android.content.Context;

import java.io.File;

import static com.ultradevs.ultrakernel.utils.ShellExecuter.shell;

/**
 * Created by ahmedhady on 29/10/17.
 */

public class msmMPDutil {
    private static final String MPD_PATH = "/sys/kernel/msm_mpdecision/conf";
    private static final String MPD_ENABLE = MPD_PATH + "/enabled";
    private static final String MPD_HOTPLUG_SUSPEND = MPD_PATH + "/hotplug_suspend";
    private static final String MPD_HOTPLUG_MIN_CPUS_ONLINE = MPD_PATH + "/min_cpus_online";

    public static boolean isAvailable() {
        if(new File(MPD_PATH).exists()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean getStatus() {
        if (shell("cat " + MPD_ENABLE, true).toString().contains("1")){
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
        shell("echo " + set.toString() + " > " + MPD_ENABLE, true);
    }
}
