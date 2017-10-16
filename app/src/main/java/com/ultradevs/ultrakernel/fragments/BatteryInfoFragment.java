package com.ultradevs.ultrakernel.fragments;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.BatteryStatusAdapter;
import com.ultradevs.ultrakernel.adapters.bat_status_list;
import com.ultradevs.ultrakernel.utils.BatteryMeterView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryInfoFragment extends Fragment {

    TextView mtxt_perc;
    TextView mtxt_bat_status;
    int level;

    ArrayList<bat_status_list> arrayOfBattery = new ArrayList<bat_status_list>();

    public BatteryInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_battery, container, false);


        // Battery
        bat_status_list battery_status;
        BatteryStatusAdapter adapter = new BatteryStatusAdapter(getContext(), arrayOfBattery);
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);
        level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        mtxt_perc = v.findViewById(R.id.txt_bat_perce);
        mtxt_bat_status = v.findViewById(R.id.txt_bat_status);
        BatteryMeterView bat = v.findViewById(R.id.battery_header_icon);
        ListView listView = (ListView) v.findViewById(R.id.bat_status_list);
        listView.setAdapter(adapter);


        // Battery: Set Defaults
        bat.setColorFilter(getContext().getColor(R.color.colorAccent_light));
        bat.setImageLevel(level);
        bat.setBatteryLevel(level);
        mtxt_perc.setText(level + "%");

        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if(status == BatteryManager.BATTERY_STATUS_CHARGING){
            mtxt_bat_status.setText("Charging");
            battery_status = new bat_status_list("Status", "Charging");
            adapter.add(battery_status);
        }
        if(status == BatteryManager.BATTERY_STATUS_FULL){
            mtxt_bat_status.setText("Full");
            battery_status = new bat_status_list("Status", "Full");
            adapter.add(battery_status);
        }
        if(status == BatteryManager.BATTERY_STATUS_DISCHARGING){
            mtxt_bat_status.setText("Not Charging");
            battery_status = new bat_status_list("Status", "Not Charging");
            adapter.add(battery_status);
        }

        if(status == BatteryManager.BATTERY_HEALTH_COLD){
            battery_status = new bat_status_list("Health", "Cold");
            adapter.add(battery_status);
        } else if(status == BatteryManager.BATTERY_HEALTH_DEAD){
            battery_status = new bat_status_list("Health", "Dead");
            adapter.add(battery_status);
        } else if(status == BatteryManager.BATTERY_HEALTH_GOOD){
            battery_status = new bat_status_list("Health", "Good");
            adapter.add(battery_status);
        } else if(status == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
            battery_status = new bat_status_list("Health", "Over Voltage");
            adapter.add(battery_status);
        } else if(status == BatteryManager.BATTERY_HEALTH_OVERHEAT){
            battery_status = new bat_status_list("Health", "Over Heat");
            adapter.add(battery_status);
        } else if(status == BatteryManager.BATTERY_HEALTH_UNKNOWN){
            battery_status = new bat_status_list("Health", "Unknown");
            adapter.add(battery_status);
        }

        int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
            mtxt_bat_status.setText(mtxt_bat_status.getText() + " & PLUGGED USB");
            battery_status = new bat_status_list("Plugged to", "USB");
            adapter.add(battery_status);
        }
        if(plugged == BatteryManager.BATTERY_PLUGGED_AC){
            mtxt_bat_status.setText(mtxt_bat_status.getText() + " & PLUGGED AC");
            battery_status = new bat_status_list("Plugged to", "AC");
            adapter.add(battery_status);
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
