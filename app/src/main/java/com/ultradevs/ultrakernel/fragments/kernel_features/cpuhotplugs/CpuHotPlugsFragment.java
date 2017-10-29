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

    private RelativeLayout M_lyMSM;
    private RelativeLayout M_lyMPD;
    private RelativeLayout M_lyALU;

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

        //MSM MPDecision
        final String mpd_path = "/sys/kernel/msm_mpdecision/conf/enabled";
        if(new File(mpd_path).exists()){
            mMPD = v.findViewById(R.id.mpd_hotplug);
            if(shell("cat " + mpd_path, true).toString().contains("0")){
                mMPD.setChecked(false);
            } else {
                mMPD.setChecked(true);
            }

            mMPD.setOnCheckedChangeListener((compoundButton, b) -> {
                if(mMPD.isChecked()==true){
                    shell("echo 1  > " + mpd_path , true);
                } else {
                    shell("echo 0  > " + mpd_path , true);
                }
                PutBooleanPreferences("msm_mpd",b);
            });
        } else {
            M_lyMPD.setVisibility(View.GONE);
        }


        //MSM Hotplug
        String msm_path = "/sys/kernel/msm_hotplug/conf/enabled";
        if(new File(msm_path).exists()){
            mMSM = v.findViewById(R.id.msm_hotplug);
            if(shell("cat " + msm_path, true).toString().contains("0")){
                mMSM.setChecked(false);
            } else {
                mMSM.setChecked(true);
            }
            mMSM.setOnCheckedChangeListener((compoundButton, b) -> {
                if(mMSM.isChecked()==true){
                    shell("echo 1  > " + msm_path , true);
                } else {
                    shell("echo 0  > " + msm_path , true);
                }
                PutBooleanPreferences("msm_hotplug",b);
            });
        } else {
            M_lyMSM.setVisibility(View.GONE);
        }

        //Alucard Hotplug
        String alucard_path = "/sys/kernel/alucard_hotplug/hotplug_enable";
        if(new File(alucard_path).exists()){
            mALU = v.findViewById(R.id.alucard_hotplug);
            if(shell("cat " + alucard_path, true).toString().contains("0")){
                mALU.setChecked(false);
            } else {
                mALU.setChecked(true);
            }
            mALU.setOnCheckedChangeListener((compoundButton, b) -> {
                if(mALU.isChecked()==true){
                    shell("echo 1  > " + alucard_path , true);
                } else {
                    shell("echo 0  > " + alucard_path , true);
                }
                PutBooleanPreferences("alucard",b);
            });
        } else {
            M_lyALU.setVisibility(View.GONE);
        }
        return v ;
    }
}
