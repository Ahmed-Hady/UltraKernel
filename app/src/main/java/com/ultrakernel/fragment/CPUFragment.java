package com.ultrakernel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ultrakernel.R;
import com.ultrakernel.adapter.GovernorAdapter;

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

    //final GovernorAdapter adapter = new GovernorAdapter(getContext());

    //final ShellUtils shell = ((Activity)getActivity()).getShellSession();

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

        cur_gov = (TextView) rootview.findViewById(R.id.cur_gov);
        cur_gov.setText(cur_gov());

        final Spinner SpinnerGOV = (Spinner) rootview.findViewById(R.id.GOVS);

        GovernorAdapter adapter = new GovernorAdapter(getContext());

        SpinnerGOV.setAdapter(adapter);

        SpinnerGOV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                //Shell.SU.run("echo "+ parent.getItemAtPosition(pos).toString() + " >  /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        return rootview;
    }
}
