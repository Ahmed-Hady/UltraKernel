package com.ultradevs.ultrakernel.fragments.kernel_features.cpugov;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.dialogs.GovernorOptionDialogFragment;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import static com.ultradevs.ultrakernel.fragments.deviceInfo.KernelInfoFragment.kernel_Current_Gov;
import static com.ultradevs.ultrakernel.utils.cpu_utils.CpuInfoUtils.getMaxFreq;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuGovFragment extends Fragment {

    public  static ShellExecuter mShell;

    public TextView mMaxFreq;
    public TextView mCurrent;
    public Button GovernorButton;

    public CpuGovFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cpu_gov, container, false);

        getActivity().setTitle(getString(R.string.k_cpu_gov));

        mMaxFreq = (TextView) v.findViewById(R.id.cpufreq);
        mCurrent = (TextView) v.findViewById(R.id.cpugov);

        mMaxFreq.setText(getMaxFreq());
        mCurrent.setText(kernel_Current_Gov());

        //Change gov
        GovernorButton=(Button)v.findViewById(R.id.change_gov);
        GovernorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GovernorOptionDialogFragment fragment = new GovernorOptionDialogFragment();
                fragment.show(getActivity().getSupportFragmentManager(), "governor_dialog");
            }
        });


        return v;
    }

}
