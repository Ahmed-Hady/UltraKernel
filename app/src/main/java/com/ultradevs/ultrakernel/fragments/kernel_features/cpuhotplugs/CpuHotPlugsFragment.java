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
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.utils.ShellExecuter;
import com.ultradevs.ultrakernel.utils.cpu_hotplugs.AlucardUtils;
import com.ultradevs.ultrakernel.utils.cpu_hotplugs.AutoSmp;
import com.ultradevs.ultrakernel.utils.cpu_hotplugs.MSM_utils;
import com.ultradevs.ultrakernel.utils.cpu_hotplugs.msmMPDutil;

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

    private ShellExecuter mShell;

    private Switch mMSM;
    private Switch mMPD;
    private Switch mALU;
    private Switch mAutoSmp;

    private RelativeLayout M_lyMSM;
    private RelativeLayout M_lyMPD;
    private RelativeLayout M_lyALU;
    private RelativeLayout M_lyAuto;

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

        mSocName.setText(SocName());

        if (getPreferences_bool("cpu_hotplug_onboot"))
            mOnBoot.setChecked(getPreferences_bool("cpu_hotplug_onboot"));

        mOnBoot.setOnCheckedChangeListener((compoundButton, b) -> {
            if(mOnBoot.isChecked()==true){
                PutBooleanPreferences("cpu_hotplug_onboot",true);
            } else {
                PutBooleanPreferences("cpu_hotplug_onboot",false);
            }
        });

        M_lyMPD = v.findViewById(R.id.ly_mpd);
        M_lyMSM = v.findViewById(R.id.ly_msm);
        M_lyALU = v.findViewById(R.id.ly_alu);
        M_lyAuto = v.findViewById(R.id.ly_auto);

        //MSM MPDecision
        if(msmMPDutil.isAvailable()){
            mMPD = v.findViewById(R.id.mpd_hotplug);
            mMPD.setChecked(msmMPDutil.getStatus());
            mMPD.setOnCheckedChangeListener((compoundButton, b) -> {
                msmMPDutil.setEnaled(b);
                PutBooleanPreferences("msm_mpd",b);
            });
        } else {
            M_lyMPD.setVisibility(View.GONE);
        }

        //MSM Hotplug
        if(MSM_utils.isAvailable()){
            mMSM = v.findViewById(R.id.msm_hotplug);
            mMSM.setChecked(MSM_utils.getStatus());
            mMSM.setOnCheckedChangeListener((compoundButton, b) -> {
                MSM_utils.setEnaled(b);
                PutBooleanPreferences("msm_hotplug",b);
            });
        } else {
            M_lyMSM.setVisibility(View.GONE);
        }

        //Alucard Hotplug
        if(AlucardUtils.isAvailable()){
            mALU = v.findViewById(R.id.alucard_hotplug);
            mALU.setChecked(AlucardUtils.getStatus());
            mALU.setOnCheckedChangeListener((compoundButton, b) -> {
                AlucardUtils.setEnaled(b);
                PutBooleanPreferences("alucard",b);
            });
        } else {
            M_lyALU.setVisibility(View.GONE);
        }

        //AutoSMP Hotplug
        if(AutoSmp.isAvailable()){
            mAutoSmp = v.findViewById(R.id.autosmp_hotplug);
            mAutoSmp.setChecked(AutoSmp.getStatus());
            mAutoSmp.setOnCheckedChangeListener((compoundButton, b) -> {
                AutoSmp.setEnaled(b);
                PutBooleanPreferences("autosmp",b);
            });
        } else {
            M_lyAuto.setVisibility(View.GONE);
        }

        return v ;
    }
}
