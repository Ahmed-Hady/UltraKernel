package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import java.io.File;

import static com.ultradevs.ultrakernel.utils.ShellExecuter.shell;

/**
 * Created by ahmedhady on 29/10/17.
 */

public class MSM_utils {
    private static final String MSM_PATH = "/sys/kernel/msm_hotplug/conf";
    private static final String MSM_ENABLE = MSM_PATH + "/enabled";
    /*private static final String MSM_HOTPLUG_SUSPEND = MSM_PATH + "/hotplug_suspend";
    private static final String MSM_HOTPLUG_MIN_CPUS_ONLINE = MSM_PATH + "/min_cpus_online";*/

    public static boolean isAvailable() {
        if(new File(MSM_PATH).exists()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean getStatus() {
        if (shell("cat " + MSM_ENABLE, true).toString().contains("1")){
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
        shell("echo " + set.toString() + " > " + MSM_ENABLE, true);
    }
}
