package com.ultrakernel.services;

import android.content.Context;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by ahmedhady on 26/01/17.
 */

public class motoLED {
    public static void SetMOTOLED(Context context) {
        if (context.getSharedPreferences("Moto",0).getBoolean("Moto",Boolean.parseBoolean(null)) == true) {
            Shell.SU.run("echo 255 > /sys/class/leds/charging/max_brightness");
        } else if (context.getSharedPreferences("Moto",0).getBoolean("Moto",Boolean.parseBoolean(null)) == false) {
            Shell.SU.run("echo 0 > /sys/class/leds/charging/max_brightness");
        }
    }
}
