package com.ultradevs.ultrakernel.fragments;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.utils.BatteryMeterView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryInfoFragment extends Fragment {

    TextView mtxt_perc;
    TextView mtxt_bat_status;
    int level;

    public BatteryInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_battery, container, false);


        // Battery
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);
        level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        mtxt_perc = v.findViewById(R.id.txt_bat_perce);
        mtxt_bat_status = v.findViewById(R.id.txt_bat_status);
        BatteryMeterView bat = v.findViewById(R.id.battery_header_icon);

        // Battery: Set Defaults
        bat.setBatteryLevel(level);
        mtxt_perc.setText(level + "%");

        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if(status == BatteryManager.BATTERY_STATUS_CHARGING){
            mtxt_bat_status.setText("Charging");
        }
        if(status == BatteryManager.BATTERY_STATUS_FULL){
            mtxt_bat_status.setText("Full");
        }
        if(status == BatteryManager.BATTERY_STATUS_DISCHARGING){
            mtxt_bat_status.setText("Not Charging");
        }

        int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
            mtxt_bat_status.setText(mtxt_bat_status.getText() + " & PLUGGED USB");
        }
        if(plugged == BatteryManager.BATTERY_PLUGGED_AC){
            mtxt_bat_status.setText(mtxt_bat_status.getText() + " & PLUGGED AC");
        }

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Battery
                                IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                                Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);

                                bat.setBatteryLevel(level);
                                mtxt_perc.setText(level + "%");
                                int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                                if(status == BatteryManager.BATTERY_STATUS_CHARGING){
                                    bat.setCharging(true);
                                    mtxt_bat_status.setText("Charging");
                                } else {
                                    bat.setCharging(false);
                                    mtxt_bat_status.setText("Not Charging");
                                }

                                if(status == BatteryManager.BATTERY_STATUS_FULL){
                                    mtxt_bat_status.setText("Full");
                                }

                                int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                                if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
                                    mtxt_bat_status.setText(mtxt_bat_status.getText() + " & PLUGGED USB");
                                }
                                if(plugged == BatteryManager.BATTERY_PLUGGED_AC){
                                    mtxt_bat_status.setText(mtxt_bat_status.getText() + " & PLUGGED AC");
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
        };

        t.start();



        return v;
    }

}
