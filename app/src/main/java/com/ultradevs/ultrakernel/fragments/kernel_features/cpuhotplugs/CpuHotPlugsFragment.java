package com.ultradevs.ultrakernel.fragments.kernel_features.cpuhotplugs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.PrefAdapter;
import com.ultradevs.ultrakernel.adapters.SwitchPrefList;

import java.util.ArrayList;

import static com.ultradevs.ultrakernel.utils.SocInfoUtils.SocName;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuHotPlugsFragment extends Fragment {

    TextView mSocName;

    ArrayList<SwitchPrefList> arrayOfSwitchPref = new ArrayList<SwitchPrefList>();
    public PrefAdapter adapter;

    public ListView HotplugList;

    public CpuHotPlugsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cpu_hotplugs, container, false);

        adapter = new PrefAdapter(getContext(), arrayOfSwitchPref);
        HotplugList = v.findViewById(R.id.hotplugs_list);
        HotplugList.setAdapter(adapter);

        mSocName = (TextView) v.findViewById(R.id.socversion);
        mSocName.setText(SocName());

        return v ;
    }

}
