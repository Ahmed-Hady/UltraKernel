package com.ultradevs.ultrakernel.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

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
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        if(status == BatteryManager.BATTERY_HEALTH_COLD){
            return "Cold";
        } else if(status == BatteryManager.BATTERY_HEALTH_DEAD){
            return "Dead";
        } else if(status == BatteryManager.BATTERY_HEALTH_GOOD){
            return "Good";
        } else if(status == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
            return "OverVoltage";
        } else if(status == BatteryManager.BATTERY_HEALTH_UNKNOWN){
            return "Unknown";
        } else if(status == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
            return "OverHeat";
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
    public static int Temp(Context mContext){
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(null, batteryIntentFilter);
        return batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
    }
    public static int Voltage(Context mContext){
        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(null, batteryIntentFilter);
        return batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
    }
}
