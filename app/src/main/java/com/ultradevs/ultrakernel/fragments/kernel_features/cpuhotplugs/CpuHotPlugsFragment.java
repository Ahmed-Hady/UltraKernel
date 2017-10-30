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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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

import static com.ultradevs.ultrakernel.utils.SocInfoUtils.Ncores;
import static com.ultradevs.ultrakernel.utils.SocInfoUtils.SocName;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuHotPlugsFragment extends Fragment {

    private CheckBox mOnBoot;

    private TextView mSocName;
    private TextView mMPD_min_value;
    private TextView mMPD_max_value;
    private TextView mALU_min_value;
    private TextView mALU_max_value;

    private Switch mMSM;
    private Switch mMPD;
    private Switch mMPDsuspend;
    private Switch mALU;
    private Switch mALUsuspend;
    private Switch mAutoSmp;

    private LinearLayout M_lyMSM;
    private LinearLayout M_lyMPD;
    private LinearLayout M_lyALU;
    private LinearLayout M_lyAuto;

    private SeekBar mMPD_online_min;
    private SeekBar mMPD_online_max;
    private SeekBar mALU_online_min;
    private SeekBar mALU_online_max;

    public CpuHotPlugsFragment() {
        // Required empty public constructor
    }

    public void PutBooleanPreferences(String Name,Boolean Function){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }

    public void PutIntegerPreferences(String Name,int Function){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Name, Function);
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

            mMPD_min_value = v.findViewById(R.id.mpd_min_online_Value);
            mMPD_min_value.setText(Ncores());
            mMPD_online_min = v.findViewById(R.id.mpd_min_online);
            mMPD_online_min.setMax(Integer.valueOf(mMPD_min_value.getText().toString()));
            mMPD_online_min.setProgress(msmMPDutil.getMinOnline());

            mMPD_max_value = v.findViewById(R.id.mpd_max_online_Value);
            mMPD_max_value.setText(Ncores());
            mMPD_online_max = v.findViewById(R.id.mpd_max_online);
            mMPD_online_max.setMax(Integer.valueOf(mMPD_max_value.getText().toString()));
            mMPD_online_max.setProgress(msmMPDutil.getMaxOnline());

            mMPD.setChecked(msmMPDutil.getStatus());
            mMPD.setOnCheckedChangeListener((compoundButton, b) -> {
                msmMPDutil.setEnabled(b);
                PutBooleanPreferences("msm_mpd",b);
            });

            mMPD_online_min.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    msmMPDutil.setMinOnline(seekBar.getProgress());
                    PutIntegerPreferences("msm_mpd_min_online", seekBar.getProgress());
                }
            });

            mMPD_online_max.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    msmMPDutil.setMaxOnline(seekBar.getProgress());
                    PutIntegerPreferences("msm_mpd_max_online", seekBar.getProgress());
                }
            });

            mMPDsuspend = v.findViewById(R.id.mpdSuspend);
            mMPDsuspend.setChecked(msmMPDutil.getSuspend());

            mMPDsuspend.setOnCheckedChangeListener((compoundButton, b) -> {
                msmMPDutil.setSuspend(b);
                PutBooleanPreferences("msm_mpd_suspend", b);
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

            mALU_min_value = v.findViewById(R.id.alu_min_online_Value);
            mALU_min_value.setText(Ncores());
            mALU_online_min = v.findViewById(R.id.alu_min_online);
            mALU_online_min.setMax(Integer.valueOf(mALU_min_value.getText().toString()));
            mALU_online_min.setProgress(AlucardUtils.getMinOnline());

            mALU_max_value = v.findViewById(R.id.alu_max_online_Value);
            mALU_max_value.setText(Ncores());
            mALU_online_max = v.findViewById(R.id.alu_max_online);
            mALU_online_max.setMax(Integer.valueOf(mALU_max_value.getText().toString()));
            mALU_online_max.setProgress(AlucardUtils.getMaxOnline());

            mALU.setChecked(AlucardUtils.getStatus());
            mALU.setOnCheckedChangeListener((compoundButton, b) -> {
                AlucardUtils.setEnabled(b);
                PutBooleanPreferences("alucard",b);
            });

            mALU_online_min.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    AlucardUtils.setMinOnline(seekBar.getProgress());
                    PutIntegerPreferences("alu_min_online", seekBar.getProgress());
                }
            });

            mALU_online_max.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    AlucardUtils.setMaxOnline(seekBar.getProgress());
                    PutIntegerPreferences("alu_max_online", seekBar.getProgress());
                }
            });

            mALUsuspend = v.findViewById(R.id.aluSuspend);
            mALUsuspend.setChecked(AlucardUtils.getSuspend());

            mALUsuspend.setOnCheckedChangeListener((compoundButton, b) -> {
                AlucardUtils.setSuspend(b);
                PutBooleanPreferences("alu_suspend", b);
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
