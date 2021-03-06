package com.ultradevs.ultrakernel.fragments.kernel_features.cpugov;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.activities.Activity;
import com.ultradevs.ultrakernel.dialogs.GovernorOptionDialogFragment;
import com.ultradevs.ultrakernel.utils.ConcurrentSync;
import com.ultradevs.ultrakernel.utils.cpu_utils.CPUInfo;
import com.ultradevs.ultrakernel.utils.cpu_utils.CpuInfoUtils;
import com.ultradevs.ultrakernel.utils.ShellUtils;
import com.ultradevs.ultrakernel.utils.prefs;
import com.ultradevs.ultrakernel.utils.utils;

import static com.ultradevs.ultrakernel.fragments.deviceInfo.KernelInfoFragment.kernel_Current_Gov;
import static com.ultradevs.ultrakernel.utils.SocInfoUtils.Ncores;
import static com.ultradevs.ultrakernel.utils.cpu_utils.CpuInfoUtils.PATH_CPUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuGovFragment extends Fragment {

    public TextView mCurrent;
    public Button GovernorButton;

    private CPUCoreListFragment mCPUCoreListFragment;
    public TextView mCPUInfoText;
    private TextView mCPUInfoCurrentSpeedText;
    private CpuGovFragment.UpdateHelper mSync;

    // cpu sliders
    private TextView mCPUSliderMinLow;
    private TextView mCPUSliderMinHigh;
    private TextView mCPUSliderMaxLow;
    private TextView mCPUSliderMaxHigh;
    private SeekBar mCPUSliderMinSeekBar;
    private SeekBar mCPUSliderMaxSeekBar;

    private ShellUtils mShell;
    private CPUInfo mCPUInfo = new CPUInfo();

    private CheckBox mOnBoot;

    public CpuGovFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cpu_gov, container, false);

        getActivity().setTitle(getString(R.string.k_cpu_gov));

        //mMaxFreq = (TextView) v.findViewById(R.id.cpufreq);
        mCurrent = (TextView) v.findViewById(R.id.cpugov);

        //mMaxFreq.setText(getMaxFreq());
        mCurrent.setText(kernel_Current_Gov());

        prefs.putString("cur_gov", kernel_Current_Gov(), getContext());

        //Change gov
        GovernorButton=(Button)v.findViewById(R.id.change_gov);
        GovernorButton.setOnClickListener(view -> {
            GovernorOptionDialogFragment fragment = new GovernorOptionDialogFragment();
            fragment.show(getActivity().getSupportFragmentManager(), "governor_dialog");
        });

        // get terminal session
        mShell = ((Activity) getActivity()).getShellSession();
        mCPUInfoText = v.findViewById(R.id.fragment_cputools_cpuinfo_text);
        mCPUInfoCurrentSpeedText= v.findViewById(R.id.fragment_cputools_cpuinfo_currentspeed_text);
        mCPUSliderMaxHigh = v.findViewById(R.id.cpu_sliders_max_high_value);
        mCPUSliderMaxLow = v.findViewById(R.id.cpu_sliders_max_low_value);
        mCPUSliderMinHigh = v.findViewById(R.id.cpu_sliders_min_high_value);
        mCPUSliderMinLow = v.findViewById(R.id.cpu_sliders_min_low_value);
        mCPUSliderMaxSeekBar = v.findViewById(R.id.cpu_sliders_max_seekbar);
        mCPUSliderMinSeekBar = v.findViewById(R.id.cpu_sliders_min_seekbar);

        mCPUSliderMaxSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                mShell.getSession().addCommand("echo " + (seekBar.getProgress() + mCPUInfo.speedMinAllowed) + " > " + PATH_CPUS + "/cpu0/cpufreq/scaling_max_freq");
                prefs.putInt("cpu_max_freq", (int) (seekBar.getProgress() + mCPUInfo.speedMinAllowed), getContext());
            }
        });

        mCPUSliderMinSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                mShell.getSession().addCommand("echo " + (seekBar.getProgress() + mCPUInfo.speedMinAllowed) + " > " + PATH_CPUS + "/cpu0/cpufreq/scaling_min_freq");
                prefs.putInt("cpu_min_freq", (int) (seekBar.getProgress() + mCPUInfo.speedMinAllowed), getContext());
            }
        });



        // Update stats for initializing
        updateOnActivity();

        mOnBoot = v.findViewById(R.id.cpuGov_runOnBoot);
        if (prefs.getBoolean("cpugov_onboot", utils.strToBoolean(null), getContext()))
            mOnBoot.setChecked(prefs.getBoolean("cpugov_onboot", utils.strToBoolean(null), getContext()));

        mOnBoot.setOnCheckedChangeListener((compoundButton, b) -> {
            if(mOnBoot.isChecked()==true){
                prefs.putBoolean("cpugov_onboot",true, getContext());
            } else {
                prefs.putBoolean("cpugov_onboot",false, getContext());
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mCPUCoreListFragment = (CPUCoreListFragment) getChildFragmentManager().findFragmentById(R.id.cputools_cpulist_fragment);

        ViewGroup.LayoutParams params = mCPUCoreListFragment.getView().getLayoutParams();

        params.height = 100*(Integer.valueOf(Ncores()));

        mCPUCoreListFragment.getView().setLayoutParams(params);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        // check if another thread is running. if not start one
        if (mSync == null || mSync.getState() == Thread.State.TERMINATED)
        {
            mSync = new CpuGovFragment.UpdateHelper();
            mSync.start();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();

        mSync.interrupt();
    }

    private boolean updateOnActivity()
    {
        if (getActivity() == null || isDetached())
            return false;

        getActivity().runOnUiThread(() -> {
            // catch the exceptions if response of shell delays
            try
            {
                CpuInfoUtils.getCpuInfo(mShell, mCPUInfo);

                StringBuilder cpuInfo = new StringBuilder();

                mCPUInfoCurrentSpeedText.setText(mCPUInfo.speedCurrent / 1000 + "MHz");

                cpuInfo.append(mCPUInfo.speedMin / 1000 + "MHz / " + mCPUInfo.speedMax / 1000 + "MHz");

                mCPUSliderMaxLow.setText(mCPUInfo.speedMinAllowed / 1000 + "MHz");
                mCPUSliderMaxHigh.setText(mCPUInfo.speedMaxAllowed / 1000 + "MHz");
                mCPUSliderMinLow.setText(mCPUInfo.speedMinAllowed / 1000 + "MHz");
                mCPUSliderMinHigh.setText(mCPUInfo.speedMax / 1000 + "MHz");

                mCPUSliderMaxSeekBar.setMax((int)(mCPUInfo.speedMaxAllowed - mCPUInfo.speedMinAllowed));
                mCPUSliderMinSeekBar.setMax((int)(mCPUInfo.speedMax - mCPUInfo.speedMinAllowed));

                mCPUSliderMaxSeekBar.setProgress((int)(mCPUInfo.speedMax - mCPUInfo.speedMinAllowed));
                mCPUSliderMinSeekBar.setProgress((int)(mCPUInfo.speedMin - mCPUInfo.speedMinAllowed));

                prefs.putInt("cpu_min_freq", (int) mCPUInfo.speedMin, getContext());

                mCPUInfoText.setText(cpuInfo.toString());

                if (mCPUCoreListFragment != null)
                    mCPUCoreListFragment.update();

            }
            catch (RuntimeException e)
            {}
            catch (Exception e)
            {}
        });

        return true;
    }

    protected class UpdateHelper extends ConcurrentSync
    {
        @Override
        protected void onRun()
        {
            try
            {
                Thread.sleep(2000);
                updateOnActivity();
            } catch (InterruptedException e)
            {
                // Known state don't do anything
            }
        }

        @Override
        protected boolean onCondition()
        {
            return !isDetached() && getActivity() != null;
        }
    }

}
