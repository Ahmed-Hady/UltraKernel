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

import java.text.DecimalFormat;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

import static android.content.Context.ACTIVITY_SERVICE;

public class SystemInfo_fragement  extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragement_systeminfo, container, false);

        // RAM Monitor

        ActivityManager activityManager = (ActivityManager) this.getContext().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        TextView total_ram = (TextView) view.findViewById(R.id.t_ram);
        TextView free_ram = (TextView) view.findViewById(R.id.f_ram);
        TextView used_ram = (TextView) view.findViewById(R.id.u_ram);
        TextView ram_perc = (TextView) view.findViewById(R.id.u_ram_perc);

        total_ram.setText(" " + size((int) memoryInfo.totalMem));
        used_ram.setText(" " + size((int) (memoryInfo.totalMem-memoryInfo.availMem)));
        free_ram.setText(" " + size((int) memoryInfo.availMem));

        double uRam = (memoryInfo.totalMem-memoryInfo.availMem);
        double rRam = (uRam / memoryInfo.totalMem)*100;
        DecimalFormat dec = new DecimalFormat("0");
        ram_perc.setText("" + dec.format(rRam).concat("%"));

        // SYSTEM INFO

            //OS_VERSION
            TextView OS_Version=(TextView) view.findViewById(R.id.os_ver);
            List<String> system_version= Shell.SH.run("getprop ro.build.version.release");
            OS_Version.setText("" + system_version);

            //OS_sdk
            TextView OS_sdk=(TextView) view.findViewById(R.id.os_sdk);
            List<String> system_sdk= Shell.SH.run("getprop ro.build.version.sdk");
            OS_sdk.setText("" + system_sdk);

            //OS_s
            TextView OS_patch=(TextView) view.findViewById(R.id.os_sec_patch);
            List<String> system_patch= Shell.SH.run("getprop ro.build.version.security_patch");
            OS_patch.setText("" + system_patch);

            //device_Board
            TextView board=(TextView) view.findViewById(R.id.d_board);
            List<String> d_board= Shell.SH.run("getprop ro.product.board");
            board.setText("" + d_board);

            //device_Manufacturer
            TextView manuf=(TextView) view.findViewById(R.id.d_manuf);
            List<String> d_manuf= Shell.SH.run("getprop ro.product.manufacturer");
            manuf.setText("" + d_manuf);

            //device_name
            TextView name=(TextView) view.findViewById(R.id.device);
            List<String> d_name= Shell.SH.run("getprop ro.product.model");
            name.setText("" + d_name);

            //kernel
            TextView kernel=(TextView) view.findViewById(R.id.kernel);
            List<String> d_kernel= Shell.SH.run("cat /proc/version");
            kernel.setText("" + d_kernel);

        // Battery

        TextView B=(TextView) view.findViewById(R.id.battery);
        B.setText(readBattery());


        // Refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    public String size(int size){
        String hrSize = "";
        int k = size;
        double m = size/1024;
        double g = size/1048576;

        DecimalFormat dec = new DecimalFormat("0.0");

        if (k>0)
        {
            hrSize = dec.format(k).concat(" KB");
        }
        if (m>0)
        {
            hrSize = dec.format(m).concat(" MB");
        }
        if (g>0)
        {
            hrSize = dec.format(g/1000).concat(" GB");
        }

        return hrSize;
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
        TextView ram_perc = (TextView) swipeRefreshLayout.findViewById(R.id.u_ram_perc);

        total_ram.setText(" " + size((int) memoryInfo.totalMem));
        used_ram.setText(" " + size((int) (memoryInfo.totalMem-memoryInfo.availMem)));
        free_ram.setText(" " + size((int) memoryInfo.availMem));

        double uRam = (memoryInfo.totalMem-memoryInfo.availMem);
        double rRam = (uRam / memoryInfo.totalMem)*100;
        DecimalFormat dec = new DecimalFormat("0");
        ram_perc.setText("" + dec.format(rRam).concat("%"));
        swipeRefreshLayout = (SwipeRefreshLayout) swipeRefreshLayout.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(this);


        //do processing to get new data and set your listview's adapter, maybe  reinitialise the loaders you may be using or so
        //when your data has finished loading, cset the refresh state of the view to false
        swipeRefreshLayout.setRefreshing(false);

    }



    private String readBattery(){
        StringBuilder sb = new StringBuilder();
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);

        boolean  present= batteryIntent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
        sb.append("PRESENT: " + present + "\n");

        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if(status == BatteryManager.BATTERY_STATUS_CHARGING){
            sb.append("BATTERY_STATUS_CHARGING\n");
        }
        if(status == BatteryManager.BATTERY_STATUS_FULL){
            sb.append("BATTERY_STATUS_FULL\n");
        }

        int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
            sb.append("BATTERY_PLUGGED_USB\n");
        }
        if(plugged == BatteryManager.BATTERY_PLUGGED_AC){
            sb.append("BATTERY_PLUGGED_AC\n");
        }

        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        sb.append("LEVEL: " + level + "\n");

        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        sb.append("SCALE: " + scale + "\n");

        int  health = batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
        sb.append("health: " + convHealth(health) + "\n");

        String  technology = batteryIntent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        sb.append("TECHNOLOGY: " + technology + "\n");

        int  temperature = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
        sb.append("TEMPERATURE: " + temperature + "\n");

        int  voltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
        sb.append("VOLTAGE: " + voltage + "\n");

        //I have no idea how to load the small icon from system resources!
        int  icon_small_resourceId = batteryIntent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
        sb.append("ICON_SMALL: " + icon_small_resourceId + "\n");

        return sb.toString();
    }

    private String convHealth(int health){
        String result;
        switch(health){
            case BatteryManager.BATTERY_HEALTH_COLD:
                result = "BATTERY_HEALTH_COLD";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                result = "BATTERY_HEALTH_DEAD";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                result = "BATTERY_HEALTH_GOOD";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                result = "BATTERY_HEALTH_OVERHEAT";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                result = "BATTERY_HEALTH_OVER_VOLTAGE";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                result = "BATTERY_HEALTH_UNKNOWN";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                result = "BATTERY_HEALTH_UNSPECIFIED_FAILURE";
                break;
            default:
                result = "unkknown";
        }

        return result;
    }
}
