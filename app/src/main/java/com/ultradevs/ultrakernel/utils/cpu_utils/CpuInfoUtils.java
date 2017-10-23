package com.ultradevs.ultrakernel.utils.cpu_utils;

/**
 * Created by ahmedhady on 23/10/17.
 */

import com.ultradevs.ultrakernel.utils.ShellExecuter;

import static com.ultradevs.ultrakernel.utils.utils.roundOneDecimals;

public class CpuInfoUtils {

    public static ShellExecuter mShell;

    public static String getMaxFreq() {
        mShell.command = "cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
        return roundOneDecimals(Double.valueOf(mShell.runAsRoot())/1000000) + " GHz";
    }
}
