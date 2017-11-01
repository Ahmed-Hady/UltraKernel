package com.ultradevs.ultrakernel.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.utils.RootUtils;
import com.ultradevs.ultrakernel.utils.cpu_hotplugs.AlucardUtils;
import com.ultradevs.ultrakernel.utils.cpu_hotplugs.MpdUtils;
import com.ultradevs.ultrakernel.utils.prefs;
import com.ultradevs.ultrakernel.utils.utils;

import static com.ultradevs.ultrakernel.activities.InitActivity.LOG_TAG;
import static com.ultradevs.ultrakernel.utils.cpu_utils.CpuInfoUtils.PATH_CPUS;

public class OnBootApply extends Service {
    public OnBootApply() {
    }

    Integer task = 0;

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
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.notif_onboot_title))
                        .setProgress(100,0,true);;

        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        if(prefs.getBoolean("cpugov_onboot", utils.strToBoolean(null), getBaseContext())==true)
            task += 1;

        if(prefs.getBoolean("cpu_hotplug_onboot", utils.strToBoolean(null), getBaseContext())==true)
            task +=1;

        if (task > 0)
            mNotificationManager.notify(1, mBuilder.build());

        new Thread(
                () -> {
                    if(task > 0){

                        if(prefs.getBoolean("cpugov_onboot", utils.strToBoolean(null), getBaseContext())==true){

                            String gov = prefs.getString("cur_gov",null,getBaseContext());
                            if(gov != null){
                                utils.writeFile(Scaling_gov_path,gov);
                            }

                            Long max_freq = prefs.getLong("cpu_max_freq", utils.strToLong(null), getBaseContext());
                            if(max_freq > 0){
                                utils.writeFile(PATH_CPUS + "/cpu0/cpufreq/scaling_max_freq", max_freq.toString());
                                Log.i(LOG_TAG, "Applying Max Freq:" + max_freq.toString());
                            }

                            int min_freq = prefs.getInt("cpu_min_freq",utils.strToInt(null), getBaseContext());
                            if(min_freq > 0){
                                utils.writeFile(PATH_CPUS + "/cpu0/cpufreq/scaling_min_freq", min_freq);
                                Log.i(LOG_TAG, "Applying Min Freq:" + min_freq);
                            }
                        }

                        if(prefs.getBoolean("cpu_hotplug_onboot", utils.strToBoolean(null), getBaseContext())==true){

                            /*
                            * AluCard Hotplug
                            */
                            boolean ALU = prefs.getBoolean("alucard_onboot", utils.strToBoolean(null), getBaseContext());
                            if(ALU){
                                AlucardUtils.setEnabled(true,getBaseContext());
                                if(prefs.getInt("alucard_min_online", utils.strToInt(null), getBaseContext()) > 0){
                                    AlucardUtils.setMinOnline(prefs.getInt("alucard_min_online", utils.strToInt(null), getBaseContext()), getBaseContext());
                                }
                                if(prefs.getInt("alucard_max_online", utils.strToInt(null), getBaseContext()) > 0){
                                    AlucardUtils.setMaxOnline(prefs.getInt("alucard_max_online", utils.strToInt(null), getBaseContext()), getBaseContext());
                                }
                                if(prefs.getBoolean("alucard_suspend", utils.strToBoolean(null),getBaseContext())){
                                    AlucardUtils.setSuspend(prefs.getBoolean("alucard_suspend", utils.strToBoolean(null),getBaseContext()),getBaseContext());
                                }
                            } else {
                                AlucardUtils.setSuspend(false,getBaseContext());
                            }

                            /*
                            * MPD Hotplug
                            */
                            boolean MPD = prefs.getBoolean("mpd_onboot", utils.strToBoolean(null), getBaseContext());
                            if(MPD){
                                MpdUtils.setEnabled(true,getBaseContext());
                                if(prefs.getInt("mpd_min_online", utils.strToInt(null), getBaseContext()) > 0){
                                    MpdUtils.setMinOnline(prefs.getInt("mpd_min_online", utils.strToInt(null), getBaseContext()), getBaseContext());
                                }
                                if(prefs.getInt("mpd_max_online", utils.strToInt(null), getBaseContext()) > 0){
                                    MpdUtils.setMaxOnline(prefs.getInt("mpd_max_online", utils.strToInt(null), getBaseContext()), getBaseContext());
                                }
                                if(prefs.getBoolean("mpd_suspend", utils.strToBoolean(null),getBaseContext())){
                                    MpdUtils.setSuspend(prefs.getBoolean("mpd_suspend", utils.strToBoolean(null),getBaseContext()),getBaseContext());
                                }
                            } else {
                                MpdUtils.setSuspend(false,getBaseContext());
                            }
                        }

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        mBuilder.setContentText(getString(R.string.notif_onboot_complete))
                                .setProgress(0,0,false);
                        mNotificationManager.notify(1, mBuilder.build());
                    }

                }).start();

        return Service.START_NOT_STICKY;
    }
}
