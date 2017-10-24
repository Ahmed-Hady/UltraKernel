package com.ultradevs.ultrakernel.utils.cpu_utils;

import com.ultradevs.ultrakernel.utils.ShellExecuter;

/**
 * Created by ahmedhady on 24/10/17.
 */

public class CPUInfo {

    public static String split(String receiver) {
        String[] splitter = receiver.split(":");
        return splitter[1];
    }

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
        return sb.toString();
    }

}