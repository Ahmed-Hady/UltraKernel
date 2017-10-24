package com.ultradevs.ultrakernel.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
        return Service.START_NOT_STICKY;
    }
}
