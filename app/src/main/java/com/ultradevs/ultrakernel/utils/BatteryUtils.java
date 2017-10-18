package com.ultradevs.ultrakernel.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.StatusAdapter;

/**
 * Created by ahmedhady on 16/10/17.
 */

public class BatteryUtils {
    public static String current_status(Context mContext) {
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(null, batteryIntentFilter);
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if(status == BatteryManager.BATTERY_STATUS_CHARGING){
            return "Charging";
        }
        if(status == BatteryManager.BATTERY_STATUS_FULL){
            return "Full";
        }
        if(status == BatteryManager.BATTERY_STATUS_DISCHARGING){
            return "Not Charging";
        }
        return null;
    }
    public static String BatteryHealth(Context mContext){
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(null, batteryIntentFilter);

        boolean present = batteryIntent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

        if (present) {
            int health = batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
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

            if (healthLbl != -1)
                return mContext.getString(healthLbl);
        }
        return null;
    }
    public static String Plugged(Context mContext){
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(null, batteryIntentFilter);
        int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
            return " USB Cable";
        } else if(plugged == BatteryManager.BATTERY_PLUGGED_AC) {
            return " AC Charger";
        }if(plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS){
            return " Wireless Charger";
        }
        return "";
    }
    public static String Techonolgy(Context mContext){
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(null, batteryIntentFilter);
        return batteryIntent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
    }
    public static String Temp(Context mContext){
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(null, batteryIntentFilter);

        float  temp   = ((float) batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0)) / 10;

        return temp + Character.toString ((char) 176) + " C";
    }
    public static int Voltage(Context mContext){
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(null, batteryIntentFilter);
        return batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
    }
}

    /*public void updateBatteryData(Intent intent) {
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
    }*/