package com.ultradevs.ultrakernel.utils.cpu_utils;

/**
 * Created by ahmedhady on 23/10/17.
 */

import com.ultradevs.ultrakernel.utils.ShellExecuter;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

import static com.ultradevs.ultrakernel.utils.utils.roundOneDecimals;

public class CpuInfoUtils {

    public final static String PATH_CPUS = "/sys/devices/system/cpu";

    // use with caution: Due to thread delay, info may not pushed before its read sequence
    public static void getCpuInfo(CpuShellUtils shell, final CPUInfo info) {

        String corePath = PATH_CPUS + "/cpu0/";

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
    }
}
