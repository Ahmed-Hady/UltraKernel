package com.ultrakernel.fragment;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultrakernel.R;
import com.ultrakernel.util.ShellExecuter;

import java.text.DecimalFormat;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.ultrakernel.util.Config.Android_OS_Version;
import static com.ultrakernel.util.Config.Android_Sdk_Version;
import static com.ultrakernel.util.Config.Android_d_board;
import static com.ultrakernel.util.Config.Android_d_kernel;
import static com.ultrakernel.util.Config.Android_d_manuf;
import static com.ultrakernel.util.Config.Android_d_name;
import static com.ultrakernel.util.Config.Android_system_patch_Version;

public class SystemInfo_fragement  extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private ShellExecuter Shell;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView OS_Version,OS_sdk,OS_patch,board,manuf,name,kernel,total_ram,free_ram,used_ram,root_s,b_level,b_volt,b_status,b_temp,b_tech,b_health;
    private RingProgressBar ram_perc;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragement_systeminfo, container, false);

        // SYSTEM INFO

        //OS_VERSION
        OS_Version=(TextView) view.findViewById(R.id.os_ver);
        OS_Version.setText("" + Android_OS_Version());

        //OS_sdk
        OS_sdk=(TextView) view.findViewById(R.id.os_sdk);
        OS_sdk.setText("" + Android_Sdk_Version());

        //OS_s
        OS_patch=(TextView) view.findViewById(R.id.os_sec_patch);
        OS_patch.setText("" + Android_system_patch_Version());

        //device_Board
        board=(TextView) view.findViewById(R.id.d_board);
        board.setText("" + Android_d_board());

        //device_Manufacturer
        manuf=(TextView) view.findViewById(R.id.d_manuf);
        manuf.setText("" + Android_d_manuf());

        //device_name
        name=(TextView) view.findViewById(R.id.device);
        name.setText("" + Android_d_name());

        //kernel
        kernel=(TextView) view.findViewById(R.id.kernel);
        kernel.setText("" + Android_d_kernel());

        // Battery
            IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);

            TextView b_status = (TextView) view.findViewById(R.id.b_status);
            TextView b_level = (TextView) view.findViewById(R.id.b_percent);
            TextView b_health = (TextView) view.findViewById(R.id.b_health);
            TextView b_tech = (TextView) view.findViewById(R.id.b_tech);
            TextView b_temp = (TextView) view.findViewById(R.id.b_temp);
            TextView b_volt = (TextView) view.findViewById(R.id.b_volt);

            int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if(status == BatteryManager.BATTERY_STATUS_CHARGING){
                b_status.setText("Charging");
            }
            if(status == BatteryManager.BATTERY_STATUS_FULL){
                b_status.setText("Full");
            }
            if(status == BatteryManager.BATTERY_STATUS_DISCHARGING){
                b_status.setText("Dis-Charging");
            }

            int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
                b_status.setText(b_status.getText() + " & PLUGGED USB");
            }
            if(plugged == BatteryManager.BATTERY_PLUGGED_AC){
                b_status.setText(b_status.getText() + " & PLUGGED AC");
            }

            int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            b_level.setText(level + "%");

            int health = batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            b_health.setText(convHealth(health));

            String technology = batteryIntent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            b_tech.setText(""+technology);

            int temperature = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            b_temp.setText(""+temperature);

            int voltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
            b_volt.setText(""+voltage);


        //root
        root_s=(TextView) view.findViewById(R.id.root);
            if(Shell.hasRoot()) {
                root_s.setText("Rooted");
            }else if(Shell.has_systemless_Root()) {
                root_s.setText("Rooted");
            }else{
                root_s.setText("Not Rooted");
            }

        // RAM Monitor

        total_ram = (TextView) view.findViewById(R.id.t_ram);
        free_ram = (TextView) view.findViewById(R.id.f_ram);
        used_ram = (TextView) view.findViewById(R.id.u_ram);
        ram_perc = (RingProgressBar) view.findViewById(R.id.u_ram_perc);


        final ActivityManager activityManager = (ActivityManager) this.getContext().getSystemService(ACTIVITY_SERVICE);
        final ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        total_ram.setText(" " + size((int) memoryInfo.totalMem));
        used_ram.setText(" " + size((int) (memoryInfo.totalMem-memoryInfo.availMem)));
        free_ram.setText(" " + size((int) memoryInfo.availMem));

        double uRam = (memoryInfo.totalMem-memoryInfo.availMem);
        double rRam = (uRam / memoryInfo.totalMem)*100;
        // Set the progress bar's progress
        ram_perc.setProgress((int) rRam);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                                activityManager.getMemoryInfo(memoryInfo);
                                total_ram.setText(" " + size((int) memoryInfo.totalMem));
                                used_ram.setText(" " + size((int) (memoryInfo.totalMem-memoryInfo.availMem)));
                                free_ram.setText(" " + size((int) memoryInfo.availMem));

                                double uRam = (memoryInfo.totalMem-memoryInfo.availMem);
                                double rRam = (uRam / memoryInfo.totalMem)*100;
                                ram_perc.setProgress((int) rRam);

                                // Battery
                                IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                                Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);

                                TextView b_status = (TextView) view.findViewById(R.id.b_status);
                                TextView b_level = (TextView) view.findViewById(R.id.b_percent);
                                TextView b_health = (TextView) view.findViewById(R.id.b_health);
                                TextView b_tech = (TextView) view.findViewById(R.id.b_tech);
                                TextView b_temp = (TextView) view.findViewById(R.id.b_temp);
                                TextView b_volt = (TextView) view.findViewById(R.id.b_volt);

                                int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                                if(status == BatteryManager.BATTERY_STATUS_CHARGING){
                                    b_status.setText("Charging");
                                }
                                if(status == BatteryManager.BATTERY_STATUS_FULL){
                                    b_status.setText("Full");
                                }

                                if(status == BatteryManager.BATTERY_STATUS_DISCHARGING){
                                    b_status.setText("Dis-Charging");
                                }

                                int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                                if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
                                    b_status.setText(b_status.getText() + " & PLUGGED USB");
                                }
                                if(plugged == BatteryManager.BATTERY_PLUGGED_AC){
                                    b_status.setText(b_status.getText() + " & PLUGGED AC");
                                }

                                int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                                b_level.setText(level + "%");

                                int health = batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
                                b_health.setText(convHealth(health));

                                String technology = batteryIntent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
                                b_tech.setText(""+technology);

                                int temperature = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
                                b_temp.setText(""+temperature);

                                int voltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
                                b_volt.setText(""+voltage);
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
        };

        t.start();

        // Refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    public static String size(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    @Override
    public void onRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        refresh();
    }
    public void refresh(){
        ActivityManager activityManager = (ActivityManager) this.getContext().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        TextView total_ram = (TextView) swipeRefreshLayout.findViewById(R.id.t_ram);
        TextView free_ram = (TextView) swipeRefreshLayout.findViewById(R.id.f_ram);
        TextView used_ram = (TextView) swipeRefreshLayout.findViewById(R.id.u_ram);
        RingProgressBar ram_perc = (RingProgressBar) swipeRefreshLayout.findViewById(R.id.u_ram_perc);

        total_ram.setText(" " + size((int) memoryInfo.totalMem));
        used_ram.setText(" " + size((int) (memoryInfo.totalMem-memoryInfo.availMem)));
        free_ram.setText(" " + size((int) memoryInfo.availMem));

        double uRam = (memoryInfo.totalMem-memoryInfo.availMem);
        double rRam = (uRam / memoryInfo.totalMem)*100;
        DecimalFormat dec = new DecimalFormat("0");
        ram_perc.setProgress((int) rRam);


        // SYSTEM INFO

        //OS_VERSION
        OS_Version=(TextView) swipeRefreshLayout.findViewById(R.id.os_ver);
        OS_Version.setText("" + Android_OS_Version());

        //OS_sdk
        OS_sdk=(TextView) swipeRefreshLayout.findViewById(R.id.os_sdk);
        OS_sdk.setText("" + Android_Sdk_Version());

        //OS_s
        OS_patch=(TextView) swipeRefreshLayout.findViewById(R.id.os_sec_patch);
        OS_patch.setText("" + Android_system_patch_Version());

        //device_Board
        board=(TextView) swipeRefreshLayout.findViewById(R.id.d_board);
        board.setText("" + Android_d_board());

        //device_Manufacturer
        manuf=(TextView) swipeRefreshLayout.findViewById(R.id.d_manuf);
        manuf.setText("" + Android_d_manuf());

        //device_name
        name=(TextView) swipeRefreshLayout.findViewById(R.id.device);
        name.setText("" + Android_d_name());

        //kernel
        kernel=(TextView) swipeRefreshLayout.findViewById(R.id.kernel);
        kernel.setText("" + Android_d_kernel());

        // Battery
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);

        TextView b_status = (TextView) swipeRefreshLayout.findViewById(R.id.b_status);
        TextView b_level = (TextView) swipeRefreshLayout.findViewById(R.id.b_percent);
        TextView b_health = (TextView) swipeRefreshLayout.findViewById(R.id.b_health);
        TextView b_tech = (TextView) swipeRefreshLayout.findViewById(R.id.b_tech);
        TextView b_temp = (TextView) swipeRefreshLayout.findViewById(R.id.b_temp);
        TextView b_volt = (TextView) swipeRefreshLayout.findViewById(R.id.b_volt);

        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if(status == BatteryManager.BATTERY_STATUS_CHARGING){
            b_status.setText("Charging");
        }
        if(status == BatteryManager.BATTERY_STATUS_FULL){
            b_status.setText("Full");
        }

        if(status == BatteryManager.BATTERY_STATUS_DISCHARGING){
            b_status.setText("Dis-Charging");
        }

        int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
            b_status.setText(b_status.getText() + " & PLUGGED USB");
        }
        if(plugged == BatteryManager.BATTERY_PLUGGED_AC){
            b_status.setText(b_status.getText() + " & PLUGGED AC");
        }

        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        b_level.setText(level + "%");

        int health = batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
        b_health.setText(convHealth(health));

        String technology = batteryIntent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        b_tech.setText(""+technology);

        int temperature = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
        b_temp.setText(""+temperature);

        int voltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
        b_volt.setText(""+voltage);

        swipeRefreshLayout = (SwipeRefreshLayout) swipeRefreshLayout.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(this);

        //do processing to get new data and set your listview's adapter, maybe  reinitialise the loaders you may be using or so
        //when your data has finished loading, cset the refresh state of the view to false
        swipeRefreshLayout.setRefreshing(false);

    }

    private String convHealth(int health){
        String result;
        switch(health){
            case BatteryManager.BATTERY_HEALTH_COLD:
                result = "COLD";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                result = "DEAD";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                result = "GOOD";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                result = "OVERHEAT";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                result = "VOLTAGE";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                result = "UNKNOWN";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                result = "FAILURE";
                break;
            default:
                result = "unkknown";
        }

        return result;
    }
}
