package com.ultradevs.ultrakernel.fragments.kernel_features.cpuhotplugs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.utils.cpu_hotplugs.AlucardUtils;
import com.ultradevs.ultrakernel.utils.cpu_hotplugs.MpdUtils;
import com.ultradevs.ultrakernel.utils.prefs;
import com.ultradevs.ultrakernel.utils.sliderUtils;
import com.ultradevs.ultrakernel.utils.utils;

import static com.ultradevs.ultrakernel.utils.SocInfoUtils.Ncores;
import static com.ultradevs.ultrakernel.utils.SocInfoUtils.SocName;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuHotPlugsFragment extends Fragment {

    private CheckBox mOnBoot;

    private TextView mSocName;
    private TextView mALU_min_value;
    private TextView mALU_max_value;
    private TextView mMPD_min_value;
    private TextView mMPD_max_value;

    private Switch mALU;
    private Switch mALUsuspend;
    private Switch mMPD;
    private Switch mMPDsuspend;

    private LinearLayout M_lyALU;
    private LinearLayout M_lyMPD;
    private LinearLayout mALU_opt;
    private LinearLayout mMPD_opt;

    private SeekBar mALU_online_min;
    private SeekBar mALU_online_max;
    private SeekBar mMPD_online_min;
    private SeekBar mMPD_online_max;

    public CpuHotPlugsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cpu_hotplugs, container, false);
        getActivity().setTitle(getString(R.string.cpu_hotplugs));

        mALU_opt = v.findViewById(R.id.alu_opt);
        mMPD_opt = v.findViewById(R.id.mpd_opt);

        mOnBoot = v.findViewById(R.id.cpuHP_runOnBoot);
        mSocName = v.findViewById(R.id.socversion);
        mSocName.setText(SocName());
        mALU = v.findViewById(R.id.alucard_hotplug);
        mALU_online_min = v.findViewById(R.id.alu_min_online);
        mALU_online_max = v.findViewById(R.id.alu_max_online);
        mALU_max_value = v.findViewById(R.id.alu_max_online_Value);
        mALU_min_value = v.findViewById(R.id.alu_min_online_Value);
        mALUsuspend = v.findViewById(R.id.aluSuspend);
        M_lyALU = v.findViewById(R.id.ly_alu);

        mMPD = v.findViewById(R.id.mpd_hotplug);
        mMPD_online_min = v.findViewById(R.id.mpd_min_online);
        mMPD_online_max = v.findViewById(R.id.mpd_max_online);
        mMPD_max_value = v.findViewById(R.id.mpd_max_online_Value);
        mMPD_min_value = v.findViewById(R.id.mpd_min_online_Value);
        mMPDsuspend = v.findViewById(R.id.mpdSuspend);
        M_lyMPD = v.findViewById(R.id.ly_mpd);

        /*
        * ==========
        * On Boot
        * ==========
        */
        if(prefs.getBoolean("cpu_hotplug_onboot", utils.strToBoolean(null), getContext()))
            mOnBoot.setChecked(prefs.getBoolean("cpu_hotplug_onboot", utils.strToBoolean(null), getContext()));

        mOnBoot.setOnCheckedChangeListener((compoundButton, b) -> prefs.putBoolean("cpu_hotplug_onboot", b, getContext()));

        /*
        * ================
        * AluCard HotPlug
        * ================
        */
        if(AlucardUtils.isAvailable()){
            mALU.setChecked(AlucardUtils.getStatus());
            mALU.setOnCheckedChangeListener((compoundButton, b) -> {
                AlucardUtils.setEnabled(b,getContext());
                OnlyOne_hotplug("alu", b);
            });
            mALU_online_min.setMax(Integer.valueOf(Ncores()));
            mALU_online_min.setProgress(AlucardUtils.getMinOnline());
            mALU_online_min.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    AlucardUtils.setMinOnline(seekBar.getProgress(),getContext());
                }
            });
            mALU_online_max.setMax(Integer.valueOf(Ncores()));
            mALU_online_max.setProgress(AlucardUtils.getMaxOnline());
            mALU_online_max.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    AlucardUtils.setMaxOnline(seekBar.getProgress(),getContext());
                }
            });
            mALU_max_value.setText(Ncores());
            mALU_min_value.setText(Ncores());
            mALUsuspend.setChecked(AlucardUtils.getSuspend());
            mALUsuspend.setOnCheckedChangeListener((compoundButton, b) -> AlucardUtils.setSuspend(b, getContext()));
        } else {
            M_lyALU.setVisibility(View.GONE);
        }

        /*
        * ================
        * MSM MPD HotPlug
        * ================
        */
        if(MpdUtils.isAvailable()){
            mMPD.setChecked(MpdUtils.getStatus());
            mMPD.setOnCheckedChangeListener((compoundButton, b) -> {
                MpdUtils.setEnabled(b,getContext());
                OnlyOne_hotplug("mpd", b);
            });
            mMPD_online_min.setMax(Integer.valueOf(Ncores()));
            mMPD_online_min.setProgress(MpdUtils.getMinOnline());
            mMPD_online_min.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    MpdUtils.setMinOnline(seekBar.getProgress(),getContext());
                }
            });
            mMPD_online_max.setMax(Integer.valueOf(Ncores()));
            mMPD_online_max.setProgress(MpdUtils.getMaxOnline());
            mMPD_online_max.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    MpdUtils.setMaxOnline(seekBar.getProgress(),getContext());
                }
            });
            mMPD_max_value.setText(Ncores());
            mMPD_min_value.setText(Ncores());
            mMPDsuspend.setChecked(MpdUtils.getSuspend());
        } else {
            M_lyMPD.setVisibility(View.GONE);
        }

        return v ;
    }

    private int n = 0;

    private void OnlyOne_hotplug(String curr_hp, boolean b){
        if(AlucardUtils.getStatus())
            n += 1;
        if(MpdUtils.getStatus())
            n += 1;

        if (n > 1){
            //Disabling All
            AlucardUtils.setEnabled(false, getContext());
            MpdUtils.setEnabled(false, getContext());

            Toast.makeText(getContext(),getString(R.string.onlyone_hotplug), Toast.LENGTH_SHORT).show();

            mALU.setChecked(false);
            mMPD.setChecked(false);

            //Enable only checked hotplug
            if(curr_hp == "alu"){
                mALU.setChecked(b);
                AlucardUtils.setEnabled(b, getContext());
            } else if(curr_hp == "mpd"){
                mMPD.setChecked(b);
                MpdUtils.setEnabled(b, getContext());
            }
            HotPlugFeatures(curr_hp);
        }
    }

    private void HotPlugFeatures(String curr_hp){
        if(curr_hp == "alu"){
            mMPDsuspend.setChecked(false);
            MpdUtils.setSuspend(false,getContext());
            sliderUtils.slideUp(mMPD_opt);
            sliderUtils.slideDown(mALU_opt);

        } else if(curr_hp == "mpd"){
            mALUsuspend.setChecked(false);
            AlucardUtils.setSuspend(false,getContext());
            sliderUtils.slideUp(mALU_opt);
            sliderUtils.slideDown(mMPD_opt);
        }
    }
}
