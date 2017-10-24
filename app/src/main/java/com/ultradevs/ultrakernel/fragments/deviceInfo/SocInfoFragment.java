package com.ultradevs.ultrakernel.fragments.deviceInfo;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.InfoList;
import com.ultradevs.ultrakernel.adapters.StatusAdapter;

import java.util.ArrayList;

import static com.ultradevs.ultrakernel.utils.SocInfoUtils.CoresInWords;
import static com.ultradevs.ultrakernel.utils.SocInfoUtils.Ncores;
import static com.ultradevs.ultrakernel.utils.SocInfoUtils.SocName;
import static com.ultradevs.ultrakernel.utils.SocInfoUtils.getProcManuf;
import static com.ultradevs.ultrakernel.utils.SystemInfoUtils.Android_ABI;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocInfoFragment extends Fragment {

    ArrayList<InfoList> arrayOfSoc = new ArrayList<InfoList>();
    public StatusAdapter adapter;
    ListView socinfolist;
    TextView soc_name;

    public SocInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_soc_info, container, false);

        getActivity().setTitle(R.string.SocInfo);

        adapter = new StatusAdapter(getContext(), arrayOfSoc);
        socinfolist = v.findViewById(R.id.soc_info_list);
        socinfolist.setAdapter(adapter);

        soc_name = v.findViewById(R.id.txt_soc_version);

        soc_name.setText(SocName());

        adapter.clear();
        adapter.add(new InfoList(getString(R.string.soc_n_cores), CoresInWords(getContext()) + " (" + Ncores() + " Cores)"));
        adapter.add(new InfoList(getString(R.string.soc_api), Android_ABI()));
        adapter.add(new InfoList(getString(R.string.soc_manuf), getProcManuf(getContext())));

        return v;
    }

}
