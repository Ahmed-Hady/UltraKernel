package com.ultrakernel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.ultrakernel.R;
import com.ultrakernel.adapter.GovernorAdapter;

import eu.chainfire.libsuperuser.Shell;

import static com.ultrakernel.util.CPUInfo.HW;
import static com.ultrakernel.util.CPUInfo.PROCESSOR;
import static com.ultrakernel.util.CPUInfo.cur_gov;

/**
 * A simple {@link Fragment} subclass.
 */
public class CPUFragment extends Fragment {

    public CPUFragment() {
        // Required empty public constructor
    }

    private TextView Cpuhw, CpuP, cur_gov;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_cpu, container, false);

        Cpuhw = (TextView) rootview.findViewById(R.id.Cpuhw);
        Cpuhw.setText("" + HW());

        CpuP = (TextView) rootview.findViewById(R.id.CpuP);
        CpuP.setText("" + PROCESSOR());

        final Spinner SpinnerGOV = (Spinner) rootview.findViewById(R.id.GOVS);
        final GovernorAdapter adapter = new GovernorAdapter(getContext());

        SpinnerGOV.setAdapter(adapter);

        SpinnerGOV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                
                Shell.SU.run("echo "+ adapter.getText(pos) + " >  /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
                TastyToast.makeText(getContext(), "Governor is " + cur_gov(), TastyToast.LENGTH_LONG, TastyToast.INFO);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        return rootview;
    }
}
