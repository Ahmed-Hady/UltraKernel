package com.ultrakernel.services;

import android.content.Context;

import eu.chainfire.libsuperuser.Shell;

import static com.ultrakernel.util.Config.FORCE_FAST_CHARGE;

/**
 * Created by ahmedhady on 26/01/17.
 */

public class usbFCH {
    public static void USBFCH(Context context){
        if (context.getSharedPreferences("usbFCH_enable",0).getBoolean("usbFCH_enable",Boolean.parseBoolean(null)) == true) {
            Shell.SU.run("echo 1 > " + FORCE_FAST_CHARGE);
        } else if (context.getSharedPreferences("usbFCH_enable",0).getBoolean("usbFCH_enable",Boolean.parseBoolean(null)) == false) {
            Shell.SU.run("echo 0 > " + FORCE_FAST_CHARGE);
        }
    }
}
