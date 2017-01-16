package com.ultrakernel;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import eu.chainfire.libsuperuser.Shell;

import static android.R.attr.id;
import static com.ultrakernel.util.Config.Android_d_manuf;
import static com.ultrakernel.util.Config.FORCE_FAST_CHARGE;

/**
 * Created by ahmedhady on 04/01/17.
 */

public class ApplyScripts extends Service {

    //*********************************** Getting & Setting Info **************************************
    public void PutStringPreferences(String Name,String Function){
        SharedPreferences settings = getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, Function);
        editor.commit();
    }

    public String getStringPreferences(String Name){
        String o;
        SharedPreferences settings = getSharedPreferences(Name, 0); // 0 - for private mode
        o=settings.getString(Name,null);
        return o;
    }

    public void PutBooleanPreferences(String Name,Boolean Function){
        SharedPreferences settings = getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }

    public boolean getPreferences_bool(String Name){
        SharedPreferences settings = getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getBoolean(Name, Boolean.parseBoolean(null));
    }

//*************************************************************************************************

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);

        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_build)
                        .setContentTitle("Applying Mods")
                        .setContentText("Please Wait ...");

        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //final String get_l1 = (eu.chainfire.libsuperuser.Shell.SU.run("cat /sys/class/leds/charging/max_brightness")).toString();
                        //final String get_l2 = (eu.chainfire.libsuperuser.Shell.SU.run("cat /data/data/com.ultrakernel/moto_led")).toString();
                        //if(!get_l2.toString().contains(get_l1.toString())){
                            int incr;
                            mBuilder.setProgress(0, 0, true);
                            mNotificationManager.notify(id, mBuilder.build());
                            for (incr = 0; incr <= 100; incr+=10) {
                                try {
                                    Thread.sleep(600);
                                    String MOTO = "motorola";
                                    //MOTO led
                                    if (Android_d_manuf().toLowerCase().indexOf(MOTO.toLowerCase()) != -1) {

                                        if (getPreferences_bool("Moto") == true) {
                                            Shell.SU.run("echo 255 > /sys/class/leds/charging/max_brightness");
                                        } else if (getPreferences_bool("Moto") == false) {
                                            Shell.SU.run("echo 0 > /sys/class/leds/charging/max_brightness");
                                        }
                                    }

                                    //d2w
                                    if (getPreferences_bool("d2w_exist") == true){
                                        if (getPreferences_bool("d2w_enable") == true) {
                                            Shell.SU.run("echo 1 > " + getStringPreferences("d2w"));
                                        } else if (getPreferences_bool("d2w_enable") == false) {
                                            Shell.SU.run("echo 0 > " + getStringPreferences("d2w"));
                                        }
                                    }

                                    //Fast Charging
                                    if (getPreferences_bool("usbFCH_exist") == true){
                                        if (getPreferences_bool("usbFCH_enable") == true) {
                                            Shell.SU.run("echo 1 > " + FORCE_FAST_CHARGE);
                                        } else if (getPreferences_bool("usbFCH_enable") == false) {
                                            Shell.SU.run("echo 0 > " + FORCE_FAST_CHARGE);
                                        }
                                    }
                                } catch (InterruptedException e) {

                                }
                            }
                            mBuilder.setContentText("Applying complete")
                                    .setProgress(0,0,false);
                            mNotificationManager.notify(id, mBuilder.build());
                        }
                    //}
                }
        ).start();
    }

}