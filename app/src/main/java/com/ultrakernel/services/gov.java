package com.ultrakernel.services;

import android.content.Context;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by ahmedhady on 26/01/17.
 */

public class gov {
    public static void SETGOV(Context context){
        Shell.SU.run("echo " + context.getSharedPreferences("governor",0).getString("governor",null) + " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
    }
}
