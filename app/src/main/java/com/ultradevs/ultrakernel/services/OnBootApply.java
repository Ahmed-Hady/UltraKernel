package com.ultradevs.ultrakernel.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import static android.R.attr.id;

import com.ultradevs.ultrakernel.R;

public class OnBootApply extends Service {
    public OnBootApply() {
    }

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
                        .setContentTitle("UltraKernel")
                        .setContentText("Hello World !");

        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        mNotificationManager.notify(id, mBuilder.build());

        return Service.START_NOT_STICKY;
    }
}
