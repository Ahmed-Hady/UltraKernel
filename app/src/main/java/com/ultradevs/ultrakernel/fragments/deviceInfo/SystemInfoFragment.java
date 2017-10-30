package com.ultradevs.ultrakernel.fragments.deviceInfo;


import android.annotation.SuppressLint;
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

import java.util.ArrayList;

import static com.ultradevs.ultrakernel.utils.ShellExecuter.isRoot;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_ABI;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_Bootloader;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_Name;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_RadioVersion;
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

    ArrayList<InfoList> arrayOfSystem = new ArrayList<InfoList>();
    public StatusAdapter adapter;
    ListView sysinfolist;
    TextView os_name;
    TextView os_version;

    public SystemInfoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sysinfo, container, false);

        getActivity().setTitle(R.string.sys_info_title);

        adapter = new StatusAdapter(getContext(), arrayOfSystem);
        sysinfolist = v.findViewById(R.id.sys_info_list);
        sysinfolist.setAdapter(adapter);

        os_name = v.findViewById(R.id.txt_os_name);
        os_version = v.findViewById(R.id.txt_os_version);

        os_name.setText("Android " + Android_Name());
        os_version.setText(Android_Version());

        adapter.clear();
        adapter.add(new InfoList(getString(R.string.sys_code_name), Android_Name()));
        adapter.add(new InfoList(getString(R.string.sys_version), Android_Version()));
        adapter.add(new InfoList(getString(R.string.sys_sdk), String.valueOf(Android_Sdk_Version())));
        adapter.add(new InfoList(getString(R.string.sys_sec_patch), Android_system_patch_Version()));
        adapter.add(new InfoList(getString(R.string.sys_device_name), Android_device_name()));
        adapter.add(new InfoList(getString(R.string.sys_device_soc), Android_device_board()));
        adapter.add(new InfoList(getString(R.string.sys_device_manuf), Android_device_manuf()));
        adapter.add(new InfoList(getString(R.string.sys_bootloader), Android_Bootloader()));
        adapter.add(new InfoList(getString(R.string.sys_cpu_abi), Android_ABI()));
        adapter.add(new InfoList(getString(R.string.sys_baseband), Android_RadioVersion()));
        adapter.add(new InfoList(getString(R.string.sys_k_version), Android_device_kernel()));
        /*if(hasSelinux()==true) {
            adapter.add(new InfoList(getString(R.string.sys_selinux), getString(R.string.sys_selinux_enforcing)));
        } else if(hasSelinux()==false) {
            adapter.add(new InfoList(getString(R.string.sys_selinux), getString(R.string.sys_selinux_permissive)));
        }else {
            adapter.add(new InfoList(getString(R.string.sys_selinux), getString(R.string.sys_selinux_off)));
        }*/
        adapter.add(new InfoList(getString(R.string.sys_root), isRoot()));

        return v;
    }

}
