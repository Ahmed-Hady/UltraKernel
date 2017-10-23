package com.ultradevs.ultrakernel.fragments.kernel_features.cpugov;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import java.text.DecimalFormat;

import static com.ultradevs.ultrakernel.utils.cpu_utils.CpuInfoUtils.getMaxFreq;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuGovFragment extends Fragment {

    public  static ShellExecuter mShell;

    public TextView mCurrent;

    public CpuGovFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cpu_gov, container, false);

        getActivity().setTitle(getString(R.string.k_cpu_gov));

        mCurrent = (TextView) v.findViewById(R.id.cpufreq);
        mCurrent.setText(getMaxFreq());
        return v;
    }

}
