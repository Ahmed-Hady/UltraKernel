package com.ultrakernel.services;

import android.content.Context;

import eu.chainfire.libsuperuser.Shell;

import static com.ultrakernel.util.Config.ARCH_POWER;

/**
 * Created by ahmedhady on 26/01/17.
 */

public class ArchPower {
    public static void SETARCHPOWER(Context context){
        if (context.getSharedPreferences("archP_enable",0).getBoolean("archP_enable",Boolean.parseBoolean(null)) == true) {
            Shell.SU.run("echo 1 > " + ARCH_POWER);
        } else if (context.getSharedPreferences("archP_enable",0).getBoolean("archP_enable",Boolean.parseBoolean(null)) == false) {
            Shell.SU.run("echo 0 > " + ARCH_POWER);
        }
    }
}
