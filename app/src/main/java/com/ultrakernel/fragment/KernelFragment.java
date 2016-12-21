package com.ultrakernel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultrakernel.R;

import static com.ultrakernel.util.Config.Android_d_kernel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KernelFragment extends Fragment {

    private TextView kerne_info;

    public KernelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_kernel, container, false);

        kerne_info = (TextView) view.findViewById(R.id.kInfo);
        kerne_info.setText("" + Android_d_kernel());
        return view;
    }

}
