package com.ultradevs.ultrakernel.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ultradevs.ultrakernel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemInfoFragment extends Fragment {


    public SystemInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sysinfo, container, false);

        //ImageView img = v.findViewById(R.id.sysLogo);

        return v;
    }

}
