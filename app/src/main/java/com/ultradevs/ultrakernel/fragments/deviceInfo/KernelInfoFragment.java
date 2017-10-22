package com.ultradevs.ultrakernel.fragments.deviceInfo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.InfoList;
import com.ultradevs.ultrakernel.adapters.StatusAdapter;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class KernelInfoFragment extends Fragment {

    public static ShellExecuter mShell;
    TextView txtKVersion;
    ArrayList<InfoList> arrayOfKernel = new ArrayList<InfoList>();
    public StatusAdapter adapter;
    ListView Kinfolist;


    public static String readKernel(){
        try {
            Process p = Runtime.getRuntime().exec("cat /proc/version");
            InputStream is = null;
            if (p.waitFor() == 0) {
                is = p.getInputStream();
            } else {
                is = p.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is),
                    4096);
            String line = br.readLine();
            br.close();
            return line;
        } catch (Exception ex) {
            return "ERROR: " + ex.getMessage();
        }
    }

    public KernelInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_kernel_info, container, false);

        getActivity().setTitle(getString(R.string.KernelInfo));

        txtKVersion = v.findViewById(R.id.txt_kernel_version);
        txtKVersion.setText(kernel_version());

        adapter = new StatusAdapter(getContext(), arrayOfKernel);
        Kinfolist = v.findViewById(R.id.kernel_info_list);
        Kinfolist.setAdapter(adapter);

        adapter.clear();

        adapter.add(new InfoList("Kernel Version", readKernel()));
        adapter.add(new InfoList("Kernel GCC", getFormattedKernelVersion(3)));
        adapter.add(new InfoList("Current Governor", kernel_Current_Gov()));
        return v;
    }

    public static String kernel_version() {
        mShell.command="cat /proc/version";
        String CurrentString = mShell.runAsRoot();
        String[] splitter1 = CurrentString.split("-");
        String[] splitter2 = splitter1[0].split(" ");
        return splitter2[2];
    }
    public static String kernel_Current_Gov(){
        mShell.command="cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
        return mShell.runAsRoot() ;
    }

    final static String LOG_TAG = "UltraKernel";

    public static String getFormattedKernelVersion(Integer gNumber) {

        final String PROC_VERSION_REGEX =
                "\\w+\\s+" + /* ignore: Linux */
                        "\\w+\\s+" + /* ignore: version */
                        "([^\\s]+)\\s+" + /* group 1: 2.6.22-omap1 */
                        "\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+" + /* group 2: (xxxxxx@xxxxx.constant) */
                        "\\(([^(]*\\([^)]*\\))?[^)]*\\)\\s+" + /* ignore: (gcc ..) */
                        "([^\\s]+)\\s+" + /* group 3: #26 */
                        "(?:PREEMPT\\s+)?" + /* ignore: PREEMPT (optional) */
                        "(.+)"; /* group 4: date */

        Pattern p = Pattern.compile(PROC_VERSION_REGEX);
        Matcher m = p.matcher(readKernel());

        if (!m.matches()) {
            Log.e(LOG_TAG, "Regex did not match on /proc/version: " + readKernel());
            return "Unavailable";
        } else if (m.groupCount() < 4) {
            Log.e(LOG_TAG, "Regex match on /proc/version only returned " + m.groupCount()
                    + " groups");
            return "Unavailable";
        } else {
            return (new StringBuilder(m.group(gNumber))).toString();
        }
    }
}
