package com.ultradevs.ultrakernel.utils.cpu_hotplugs;

import android.content.Context;

import com.ultradevs.ultrakernel.utils.prefs;
import com.ultradevs.ultrakernel.utils.utils;

import java.io.File;

/**
 * Created by ahmedhady on 29/10/17.
 */

public class MsmUtils {
    private static final String MSM_HOTPLUG = "/sys/module/msm_hotplug";
    private static final String MSM_HOTPLUG_ENABLE = MSM_HOTPLUG + "/msm_enabled";
    private static final String MSM_HOTPLUG_MIN_CPUS_ONLINE = MSM_HOTPLUG + "/min_cpus_online";
    private static final String MSM_HOTPLUG_MAX_CORES_LIMIT = MSM_HOTPLUG + "/max_cpus_online";
    //private static final String MSM_HOTPLUG_SUSPEND = MSM_HOTPLUG + "/hotplug_suspend";

    public static boolean isAvailable() {
        if(new File(MSM_HOTPLUG).exists()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean getStatus() {
        if (utils.readFile(MSM_HOTPLUG_ENABLE, true).toString().contains("1")){
            return true;
        } else {
            return false;
        }
    }

    public static int getMinOnline(){
        return utils.strToInt(utils.readFile(MSM_HOTPLUG_MIN_CPUS_ONLINE));
    }

    public static int getMaxOnline(){
        return utils.strToInt(utils.readFile(MSM_HOTPLUG_MAX_CORES_LIMIT));
    }

    public static void setEnabled(boolean enable, Context context) {
        final Integer set;
        if(enable == true){
            set = 1;
        } else {
            set = 0;
        }
        utils.writeFile(MSM_HOTPLUG_ENABLE, set.toString());
        prefs.putBoolean("msm_onboot", enable, context);
    }

    public static void setMinOnline(int value, Context context){
        utils.writeFile(MSM_HOTPLUG_MIN_CPUS_ONLINE, value);
        prefs.putInt("msm_min_online", value, context);
    }

    public static void setMaxOnline(int value, Context context){
        utils.writeFile(MSM_HOTPLUG_MAX_CORES_LIMIT, value);
        prefs.putInt("msm_max_online", value, context);
    }

}
