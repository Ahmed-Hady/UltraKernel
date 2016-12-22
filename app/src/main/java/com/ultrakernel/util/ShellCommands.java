package com.ultrakernel.util;

/**
 * Created by ahmedhady on 22/12/16.
 */

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

import eu.chainfire.libsuperuser.Shell;

public class ShellCommands {
    public static ShellExecuter mShell;

    public static void boost_system(final Context mMain){
        TastyToast.makeText(mMain.getApplicationContext(), "Boosting System!", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);

        if(Shell.SU.available()) {
                Shell.SU.run("sync");
                Shell.SU.run("busybox sysctl -w vm.drop_caches=3");
                TastyToast.makeText(mMain.getApplicationContext(), "Boosted!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        }else{
            TastyToast.makeText(mMain.getApplicationContext(), "Failed: you have no Root", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }

    }

    public static void RAM_IMP(final Context mMain){
        TastyToast.makeText(mMain.getApplicationContext(), "Killing ram draining apps...", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);

        if(Shell.SU.available()) {
            Shell.SU.run("killall -9 android.process.media");
            Shell.SU.run("killall -9 mediaserver");
            Shell.SU.run("killall -9 com.facebook.katana"); //facebook
            Shell.SU.run("killall -9 com.facebook.orca");  //Messenger
            Shell.SU.run("killall -9 com.whatsapp");  //WhatsApp

            TastyToast.makeText(mMain.getApplicationContext(), "Ram Improved!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        }else{
            TastyToast.makeText(mMain.getApplicationContext(), "Failed: you have no Root", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }

    }
}
