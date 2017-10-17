package com.ultradevs.ultrakernel.fragments;


import android.annotation.SuppressLint;
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
import com.ultradevs.ultrakernel.utils.BatteryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryInfoFragment extends Fragment {

    TextView mtxt_perc;
    TextView mtxt_bat_status;
    int level;
    ListView batinfolist;

    ArrayList<bat_status_list> arrayOfBattery = new ArrayList<bat_status_list>();

    public BatteryInfoFragment() {
        // Required empty public constructor
    }

    public BatteryStatusAdapter adapter;

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_battery, container, false);

        getActivity().setTitle(R.string.bat_info);

        // Battery
        bat_status_list battery_status;
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);
        level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        mtxt_perc = v.findViewById(R.id.txt_bat_perce);
        mtxt_bat_status = v.findViewById(R.id.txt_bat_status);
        BatteryMeterView bat = v.findViewById(R.id.battery_header_icon);
        adapter = new BatteryStatusAdapter(getContext(), arrayOfBattery);
        batinfolist = (ListView) v.findViewById(R.id.bat_status_list);
        batinfolist.setAdapter(adapter);


        // Battery: Set Defaults
        bat.setColorFilter(getContext().getColor(R.color.colorAccent_light));
        bat.setImageLevel(level);
        bat.setBatteryLevel(level);
        mtxt_perc.setText(level + "%");

        mtxt_bat_status.setText(BatteryUtils.current_status(getContext()));
        mtxt_bat_status.setText(mtxt_bat_status.getText() + BatteryUtils.Plugged(getContext()));

        String S = BatteryUtils.current_status(getContext());
        String H = BatteryUtils.BatteryHealth(getContext());

        adapter.add(new bat_status_list("Status", S));
        adapter.add(new bat_status_list("Health", H));
        adapter.add(new bat_status_list("Technology", BatteryUtils.Techonolgy(getContext())));
        adapter.add(new bat_status_list("Plugged to", BatteryUtils.Plugged(getContext())));
        adapter.add(new bat_status_list("Temperature", String.valueOf(BatteryUtils.Temp(getContext()))));
        adapter.add(new bat_status_list("Voltage", String.valueOf(BatteryUtils.Voltage(getContext()))));
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
                                level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                                bat.setBatteryLevel(level);
                                mtxt_perc.setText(level + "%");

                                mtxt_bat_status.setText(BatteryUtils.current_status(getContext()));
                                mtxt_bat_status.setText(mtxt_bat_status.getText() + BatteryUtils.Plugged(getContext()));
                                if(BatteryUtils.current_status(getContext())=="Charging"){
                                    bat.setCharging(true);
                                } else if(BatteryUtils.current_status(getContext())=="Not Charging"){
                                    bat.setCharging(false);
                                };
                                adapter.clear();
                                adapter.add(new bat_status_list("Status", S));
                                adapter.add(new bat_status_list("Health", H));
                                adapter.add(new bat_status_list("Technology", BatteryUtils.Techonolgy(getContext())));
                                adapter.add(new bat_status_list("Plugged to", BatteryUtils.Plugged(getContext())));
                                adapter.add(new bat_status_list("Temperature", String.valueOf(BatteryUtils.Temp(getContext()))));
                                adapter.add(new bat_status_list("Voltage", String.valueOf(BatteryUtils.Voltage(getContext()))));

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
