package com.ultradevs.ultrakernel.fragments.kernel_features.cpuhotplugs;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.PrefAdapter;
import com.ultradevs.ultrakernel.adapters.SwitchPrefList;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import java.io.File;
import java.util.ArrayList;

import static com.ultradevs.ultrakernel.activities.InitActivity.LOG_TAG;
import static com.ultradevs.ultrakernel.utils.ShellExecuter.shell;
import static com.ultradevs.ultrakernel.utils.SocInfoUtils.SocName;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuHotPlugsFragment extends Fragment {

    private CheckBox mOnBoot;
    private TextView mSocName;
    private ArrayList<SwitchPrefList> arrayOfSwitchPref = new ArrayList<SwitchPrefList>();
    private PrefAdapter adapter;
    private ListView HotplugList;

    public CpuHotPlugsFragment() {
        // Required empty public constructor
    }

    public void PutBooleanPreferences(String Name,Boolean Function){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }

    public boolean getPreferences_bool(String Name){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getBoolean(Name, Boolean.parseBoolean(null));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cpu_hotplugs, container, false);

        getActivity().setTitle(getString(R.string.cpu_hotplugs));

        mOnBoot = (CheckBox) v.findViewById(R.id.cpuHP_runOnBoot);
        mSocName = (TextView) v.findViewById(R.id.socversion);

        adapter = new PrefAdapter(getContext(), arrayOfSwitchPref);
        HotplugList = v.findViewById(R.id.hotplugs_list);
        HotplugList.setAdapter(adapter);

        adapter.clear();

        mSocName.setText(SocName());

        if (getPreferences_bool("cpu_hotplug_onboot"))
            mOnBoot.setChecked(getPreferences_bool("cpu_hotplug_onboot"));

        mOnBoot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(mOnBoot.isChecked()==true){
                    PutBooleanPreferences("cpu_hotplug_onboot",true);
                } else {
                    PutBooleanPreferences("cpu_hotplug_onboot",false);
                }
            }
        });


        //MSM MPDecision
        String mpd_path = "/sys/kernel/msm_mpdecision/conf/enabled";
        if(new File(mpd_path).exists())
            adapter.add(new SwitchPrefList(getString(R.string.cpu_hotplug_mpd), "Replacement for Qualcomm MPD hotplug developed by show-p-1984", mpd_path, "msm_mpd"));

        //MSM Hotplug
        String msm_path = "/sys/kernel/msm_hotplug/conf/enabled";
        if(new File(msm_path).exists())
            adapter.add(new SwitchPrefList(getString(R.string.cpu_hotplug_msm), "Replacement for Qualcomm MPD hotplug developed by myfluxi", msm_path, "msm_hp"));

        //Alucard Hotplug
        String alucard_path = "/sys/kernel/alucard_hotplug/hotplug_enable";
        if(new File(alucard_path).exists())
            adapter.add(new SwitchPrefList(getString(R.string.cpu_hotplug_alucard), "Replacement for Qualcomm MPD hotplug developed by Alucard", alucard_path, "msm_alucard"));

        return v ;
    }
}
