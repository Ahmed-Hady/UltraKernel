package com.ultrakernel;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.io.File;

import eu.chainfire.libsuperuser.Shell;

import static android.R.attr.id;

/**
 * Created by ahmedhady on 04/01/17.
 */

public class ApplyScripts extends Service {

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
                        .setSmallIcon(R.drawable.ic_android)
                        .setContentTitle("Applying Mods")
                        .setContentText("Please Wait ...");

        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        final String get_l1 = (eu.chainfire.libsuperuser.Shell.SU.run("cat /sys/class/leds/charging/max_brightness")).toString();
                        final String get_l2 = (eu.chainfire.libsuperuser.Shell.SU.run("cat /data/data/com.ultrakernel/moto_led")).toString();
                        if(!get_l2.toString().contains(get_l1.toString())){
                            int incr;
                            mBuilder.setProgress(0, 0, true);
                            mNotificationManager.notify(id, mBuilder.build());
                            for (incr = 0; incr <= 100; incr+=10) {
                                try {
                                    Thread.sleep(600);

                                    //MOTO led
                                    if(new File("/data/data/com.ultrakernel/moto_led").exists()) {
                                        final String R_ml = (Shell.SU.run("cat /data/data/com.ultrakernel/moto_led")).toString();
                                        if (R_ml.equals("[1]")) {
                                            Shell.SU.run("echo 255 > /sys/class/leds/charging/max_brightness");
                                        } else if (R_ml.equals("[0]")) {
                                            Shell.SU.run("echo 0 > /sys/class/leds/charging/max_brightness");
                                        }
                                    }

                                } catch (InterruptedException e) {

                                }
                            }
                            mBuilder.setContentText("Applying complete")
                                    .setProgress(0,0,false);
                            mNotificationManager.notify(id, mBuilder.build());
                        }
                    }
                }
        ).start();
    }

}