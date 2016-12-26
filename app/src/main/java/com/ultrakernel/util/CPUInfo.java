package com.ultrakernel.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ahmedhady on 23/12/16.
 */

public class CPUInfo {
    public static ShellExecuter mShell;

    public static String split(String receiver) {
        String[] splitter = receiver.split(":");
        return splitter[1];
    }

    public static String HW() {
        try{
            int counter = 0;
            BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String line;
                while ((line = br.readLine()) != null) {
                    counter++;
                    switch(counter){
                        case 41:
                            return split(line.toString());
                    }
                }
             br.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
        return HW();
    }
    public static String PROCESSOR() {
        try{
            int counter = 0;
            BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String line;
            while ((line = br.readLine()) != null) {
                counter++;
                switch(counter){
                    case 44 :
                        return split(line.toString());
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PROCESSOR();
    }
    public static String cur_gov(){
        mShell.command="cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
        return mShell.runAsRoot();
    }
}
