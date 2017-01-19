package com.ultrakernel.util;

/**
 * Created by ahmedhady on 23/12/16.
 */

public class CPUInfo {
    public static ShellExecuter mShell;

    public static String split(String receiver) {
        String[] splitter = receiver.split(":");
        return splitter[1];
    }
    public static String cur_gov(){
        mShell.command="cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
        return mShell.runAsRoot();
    }

    public String governor = null;
    public long speedCurrent = 0;
    public long speedMin = 0;
    public long speedMax = 0;
    public long speedMinAllowed = 0;
    public long speedMaxAllowed = 0;

    // Wait idle
    public boolean lock = false;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Maximum speed: " + speedMax / 1000 + "MHz");
        sb.append("\n");
        sb.append("Minimum speed: " + speedMin / 1000 + "MHz");
        sb.append("\n");
        sb.append("Current Speed: " + speedCurrent / 1000 + "MHz");
        sb.append("\n");
        sb.append("Active governor: " + governor);

        return sb.toString();
    }

}
