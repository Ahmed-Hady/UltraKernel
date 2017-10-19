package com.ultradevs.ultrakernel.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.InfoList;
import com.ultradevs.ultrakernel.adapters.StatusAdapter;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import java.util.ArrayList;

import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_Name;

/**
 * A simple {@link Fragment} subclass.
 */
public class KernelInfoFragment extends Fragment {

    public static ShellExecuter mShell;
    TextView txtKVersion;
    ArrayList<InfoList> arrayOfKernel = new ArrayList<InfoList>();
    public StatusAdapter adapter;
    ListView Kinfolist;

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

        adapter.add(new InfoList("Kernel Version", Kernel_Full_Version()));
        adapter.add(new InfoList("Kernel GCC", "Gcc " + Kernel_GCC()));
        return v;
    }

    public static String kernel_version() {
        mShell.command="cat /proc/version";
        String CurrentString = mShell.runAsRoot();
        String[] splitter1 = CurrentString.split("-");
        String[] splitter2 = splitter1[0].split(" ");
        return splitter2[2];
    }
    public static String Kernel_Full_Version() {
        mShell.command="cat /proc/version";
        return mShell.runAsRoot();
    }
    public static String Kernel_GCC() {
        mShell.command="cat /proc/version";
        String CurrentString = mShell.runAsRoot();
        String[] splitter1 = CurrentString.split("-");
        String[] splitter2 = splitter1[7].split(" ");
        return splitter2[3] + " " + splitter2[4];
    }
}
