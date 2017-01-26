package com.ultrakernel;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import static android.R.attr.id;
import static com.ultrakernel.services.ArchPower.SETARCHPOWER;
import static com.ultrakernel.services.d2w.SETD2W;
import static com.ultrakernel.services.gov.SETGOV;
import static com.ultrakernel.services.motoLED.SetMOTOLED;
import static com.ultrakernel.services.usbFCH.USBFCH;
import static com.ultrakernel.util.Config.Android_d_manuf;

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
    public int onStartCommand(Intent intent, int flags, int startId) {


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
                            int incr;
                            for (incr = 0; incr <= 100; incr+=20) {

                                mBuilder.setProgress(100, incr, false);
                                mNotificationManager.notify(id, mBuilder.build());

                                try {
                                    Thread.sleep(10);
                                    //MOTO led
                                    String MOTO = "motorola";
                                    if (Android_d_manuf().toLowerCase().indexOf(MOTO.toLowerCase()) != -1) {
                                        SetMOTOLED(getBaseContext());
                                    }

                                    //d2w
                                    if (getPreferences_bool("d2w_exist") == true){
                                        SETD2W(getBaseContext());
                                    }

                                    //Fast Charging
                                    if (getPreferences_bool("usbFCH_exist") == true){
                                        USBFCH(getBaseContext());
                                    }

                                    //ARCH POWER
                                    if (getPreferences_bool("archP_exist") == true){
                                        SETARCHPOWER(getBaseContext());
                                    }

                                    //CPU GOV
                                        SETGOV(getBaseContext());
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
        return Service.START_NOT_STICKY;
    }

}