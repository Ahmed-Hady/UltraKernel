package com.ultradevs.ultrakernel.fragments.kernel_features.cpugov;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ultradevs.ultrakernel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuGovFragment extends Fragment {


    public CpuGovFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cpu_gov, container, false);

        getActivity().setTitle(getString(R.string.k_cpu_gov));

        return v;
    }

}
