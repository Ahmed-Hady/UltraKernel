package com.ultradevs.ultrakernel.fragments.deviceInfo;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.CircularPropagation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eralp.circleprogressview.CircleProgressView;
import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.InfoList;
import com.ultradevs.ultrakernel.adapters.StatusAdapter;
import com.ultradevs.ultrakernel.utils.BatteryMeterView;
import com.ultradevs.ultrakernel.utils.BatteryUtils;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_Sdk_Version;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryInfoFragment extends Fragment {

    TextView mtxt_perc;
    TextView mtxt_bat_status;
    int level;
    ListView batinfolist;
    BatteryMeterView bat;
    CircleProgressView bat2;

    ArrayList<InfoList> arrayOfBattery = new ArrayList<InfoList>();

    public BatteryInfoFragment() {
        // Required empty public constructor
    }

    public StatusAdapter adapter;

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_battery, container, false);

        getActivity().setTitle(R.string.bat_info);

        // Battery
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = getActivity().registerReceiver(null, batteryIntentFilter);
        level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        mtxt_perc = v.findViewById(R.id.txt_bat_perce);
        mtxt_bat_status = v.findViewById(R.id.txt_bat_status);

        bat = v.findViewById(R.id.battery_header_icon);
        bat2 = v.findViewById(R.id.battery_header_icon2);

        adapter = new StatusAdapter(getContext(), arrayOfBattery);
        batinfolist = v.findViewById(R.id.bat_status_list);
        batinfolist.setAdapter(adapter);

        // Battery: Set Defaults
        if(Android_Sdk_Version() > 23) {
            bat.setColorFilter(getContext().getColor(R.color.colorAccent_light));
            bat.setImageLevel(level);
            bat.setBatteryLevel(level);
        } else {
            bat2.setProgress(level);
        }
        mtxt_perc.setText(level + "%");

        mtxt_bat_status.setText(BatteryUtils.current_status(getContext()));
        mtxt_bat_status.setText(mtxt_bat_status.getText() + BatteryUtils.Plugged(getContext()));

        loadBatterySection();

        return v;
    }

    private void loadBatterySection() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        getActivity().registerReceiver(batteryInfoReceiver, intentFilter);
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateBatteryData(intent);
        }
    };

    private void updateBatteryData(Intent intent) {
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
        adapter.clear();
        if (present) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int healthLbl = -1;

            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    healthLbl = R.string.battery_health_cold;
                    break;

                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthLbl = R.string.battery_health_dead;
                    break;

                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthLbl = R.string.battery_health_good;
                    break;

                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthLbl = R.string.battery_health_over_voltage;
                    break;

                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthLbl = R.string.battery_health_overheat;
                    break;

                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthLbl = R.string.battery_health_unspecified_failure;
                    break;

                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                default:
                    break;
            }

            if (healthLbl != -1) {
                // display battery health ...
                adapter.add(new InfoList("Health", getString(healthLbl)));
            }

            // Calculate Battery Percentage ...
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if (level != -1 && scale != -1) {
                int batteryPct = (int) ((level / (float) scale) * 100f);
                mtxt_perc.setText(batteryPct + "%");
                if(Android_Sdk_Version() > 23) {
                    bat.setBatteryLevel(batteryPct);
                } else {
                    bat2.setProgress(batteryPct);
                }
            }

            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            int pluggedLbl = R.string.battery_plugged_none;

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    pluggedLbl = R.string.battery_plugged_wireless;
                    break;

                case BatteryManager.BATTERY_PLUGGED_USB:
                    pluggedLbl = R.string.battery_plugged_usb;
                    break;

                case BatteryManager.BATTERY_PLUGGED_AC:
                    pluggedLbl = R.string.battery_plugged_ac;
                    break;

                default:
                    pluggedLbl = R.string.battery_plugged_none;
                    break;
            }

            // display plugged status ...
            if(pluggedLbl != R.string.battery_plugged_none) {
                adapter.add(new InfoList("Plugged", getString(pluggedLbl)));
            }
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int statusLbl = R.string.battery_status_discharging;

            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusLbl = R.string.battery_status_charging;
                    if(Android_Sdk_Version() > 23) {
                        bat.setCharging(true);
                    }
                    break;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusLbl = R.string.battery_status_discharging;
                    if(Android_Sdk_Version() > 23) {
                        bat.setCharging(false);
                    }
                    break;

                case BatteryManager.BATTERY_STATUS_FULL:
                    statusLbl = R.string.battery_status_full;
                    break;

                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusLbl = -1;
                    break;

                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                default:
                    statusLbl = R.string.battery_status_discharging;
                    break;
            }

            if (statusLbl != -1) {
                mtxt_bat_status.setText(getString(statusLbl) + " " + getString(pluggedLbl));
                adapter.add(new InfoList("Status", getString(statusLbl)));
            }

            if (intent.getExtras() != null) {
                String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

                if (!"".equals(technology)) {
                    adapter.add(new InfoList("Technology", technology));
                }
            }

            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

            if (temperature > 0) {
                float temp = ((float) temperature) / 10f;
                adapter.add(new InfoList("Temperature", temp + "°C"));
            }

            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            if (voltage > 0) {
                adapter.add(new InfoList("Voltage", voltage + " mV"));
            }

            double capacity = BatteryUtils.getBatteryCapacity(getContext());

            if (capacity > 0) {
                adapter.add(new InfoList("Capacity", capacity + " mAh"));
            }

        }

    }
}
