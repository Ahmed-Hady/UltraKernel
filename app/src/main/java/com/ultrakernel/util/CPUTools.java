package com.ultrakernel.util;

import java.io.File;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by: veli
 * Date: 10/21/16 1:04 AM
 */

public class CPUTools {
    public final static String TAG = "CPUTools";

    // use with caution: Due to thread delay, info may not pushed before its read sequence
    public static void getCpuInfo(ShellUtils shell, final CPUInfo info) {

        String corePath = Config.PATH_CPUS + "/cpu0/";

        info.lock = true;

        shell.getSession().addCommand("cat " + corePath + "cpufreq/cpuinfo_cur_freq", 42, new Shell.OnCommandResultListener() {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output) {
                if (output.size() > 0)
                    info.speedCurrent = Long.valueOf(output.get(0));
            }
        });

        shell.getSession().addCommand("cat " + corePath + "cpufreq/cpuinfo_max_freq", 43, new Shell.OnCommandResultListener() {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output) {
                if (output.size() > 0)
                    info.speedMaxAllowed = Long.valueOf(output.get(0));
            }
        });

        shell.getSession().addCommand("cat " + corePath + "cpufreq/cpuinfo_min_freq", 44, new Shell.OnCommandResultListener() {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output) {
                if (output.size() > 0)
                    info.speedMinAllowed = Long.valueOf(output.get(0));
            }
        });

        shell.getSession().addCommand("cat " + corePath + "cpufreq/scaling_min_freq", 44, new Shell.OnCommandResultListener() {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output) {
                if (output.size() > 0)
                    info.speedMin = Long.valueOf(output.get(0));
            }
        });

        shell.getSession().addCommand("cat " + corePath + "cpufreq/scaling_max_freq", 44, new Shell.OnCommandResultListener() {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output) {
                if (output.size() > 0)
                    info.speedMax = Long.valueOf(output.get(0));
            }
        });

        shell.getSession().addCommand("cat " + corePath + "cpufreq/scaling_governor", 45, new Shell.OnCommandResultListener() {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output) {
                if (output.size() > 0)
                    info.governor = output.get(0);

                info.lock = false;
            }
        });
    }


    public static boolean hasMPDecision() {
        return new File("/system/bin/mpdecision").isFile();
    }

    public static boolean hasMsmMPDecisionHotplug() {
        return new File("/sys/kernel/msm_mpdecision/conf/enabled").isFile();
    }

    public static boolean DynamicFsync() {
        return new File("/sys/kernel/dyn_fsync/Dyn_fsync_active").isFile();
    }
}
