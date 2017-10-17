package com.ultradevs.ultrakernel.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.BatteryStatusAdapter;
import com.ultradevs.ultrakernel.adapters.bat_status_list;

import java.util.ArrayList;

import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_Name;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_Sdk_Version;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_Version;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_device_board;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_device_kernel;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_device_manuf;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_device_name;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_system_patch_Version;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemInfoFragment extends Fragment {

    ArrayList<bat_status_list> arrayOfSystem = new ArrayList<bat_status_list>();

    public BatteryStatusAdapter adapter;

    ListView sysinfolist;

    public SystemInfoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sysinfo, container, false);

        getActivity().setTitle(R.string.sys_info_title);

        adapter = new BatteryStatusAdapter(getContext(), arrayOfSystem);
        sysinfolist = (ListView) v.findViewById(R.id.sys_info_list);
        sysinfolist.setAdapter(adapter);

        TextView os_name = v.findViewById(R.id.txt_os_name);
        TextView os_version = v.findViewById(R.id.txt_os_version);

        os_name.setText("Android " + Android_Name());
        os_version.setText(Android_Version());

        adapter.add(new bat_status_list("Android Code Name", Android_Name()));
        adapter.add(new bat_status_list("Android Version", Android_Version()));
        adapter.add(new bat_status_list("SDK Version", Android_Sdk_Version()));
        adapter.add(new bat_status_list("Security Patch", Android_system_patch_Version()));
        adapter.add(new bat_status_list("Device Name", Android_device_name()));
        adapter.add(new bat_status_list("Device SOC", Android_device_board()));
        adapter.add(new bat_status_list("Device Manufacturer", Android_device_manuf()));
        adapter.add(new bat_status_list("Kernel Version", Android_device_kernel()));

        return v;
    }

}
