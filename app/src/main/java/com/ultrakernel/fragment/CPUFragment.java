package com.ultrakernel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultrakernel.R;

import static com.ultrakernel.util.CPUInfo.HW;
import static com.ultrakernel.util.CPUInfo.PROCESSOR;

/**
 * A simple {@link Fragment} subclass.
 */
public class CPUFragment extends Fragment {


    public CPUFragment() {
        // Required empty public constructor
    }

    private TextView Cpuhw, CpuP;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_cpu, container, false);

        Cpuhw = (TextView) rootview.findViewById(R.id.Cpuhw);
        Cpuhw.setText("" + HW());

        CpuP = (TextView) rootview.findViewById(R.id.CpuP);
        CpuP.setText("" + PROCESSOR());
        return rootview;
    }

}
