package com.ultrakernel.services;

import android.content.Context;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by ahmedhady on 26/01/17.
 */

public class d2w {
    public static void SETD2W(Context context){
        if (context.getSharedPreferences("d2w_enable",0).getBoolean("d2w_enable",Boolean.parseBoolean(null)) == true) {
            Shell.SU.run("echo 1 > " + context.getSharedPreferences("d2w",0).getString("d2w",null));
        } else if (context.getSharedPreferences("d2w_enable",0).getBoolean("d2w_enable",Boolean.parseBoolean(null)) == false) {
            Shell.SU.run("echo 0 > " + context.getSharedPreferences("d2w",0).getString("d2w",null));
        }
    }
}
