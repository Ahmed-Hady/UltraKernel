package com.ultradevs.ultrakernel.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.ultradevs.ultrakernel.R;

import static com.ultradevs.ultrakernel.utils.ShellExecuter.shell;
import static com.ultradevs.ultrakernel.utils.cpu_utils.CpuInfoUtils.PATH_CPUS;

public class OnBootApply extends Service {
    public OnBootApply() {
    }

    Integer task = 0;

    public boolean getPreferences_bool(String Name){
        SharedPreferences settings = getBaseContext().getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getBoolean(Name, Boolean.parseBoolean(null));
    }

    public String getPreferences_string(String Name){
        SharedPreferences settings = getBaseContext().getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getString(Name, null);
    }

    public Long getPreferences_long(String Name){
        SharedPreferences settings = getBaseContext().getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getLong(Name, 0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String Scaling_gov_path="/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";

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
                        .setSmallIcon(R.drawable.ic_sys_info)
                        .setContentTitle("UltraKernel")
                        .setContentText("Applying OnBoot Preferences !")
                        .setProgress(100,0,true);;

        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        if(getPreferences_bool("cpugov_onboot")==true)
            task += 1;

        if (task > 0)
            mNotificationManager.notify(1, mBuilder.build());

        new Thread(
                () -> {
                    if(task > 0){

                        if(getPreferences_bool("cpugov_onboot")==true){

                            String gov = getPreferences_string("cur_gov");
                            if(gov != null){
                                shell("chmod 777 " + Scaling_gov_path + " && echo " + gov + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path, true);
                            }

                            Long max_freq = getPreferences_long("cpu_max_freq");
                            if(max_freq != null){
                                shell("echo " + max_freq + " > " + PATH_CPUS + "/cpu0/cpufreq/scaling_max_freq", true);
                            }

                            Long min_freq = getPreferences_long("cpu_min_freq");
                            if(max_freq != null){
                                shell("echo " + min_freq + " > " + PATH_CPUS + "/cpu0/cpufreq/scaling_min_freq", true);
                            }
                        }

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        mBuilder.setContentText("Applying complete")
                                .setProgress(0,0,false);
                        mNotificationManager.notify(1, mBuilder.build());
                    }

                }).start();

        return Service.START_NOT_STICKY;
    }
}
