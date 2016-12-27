package com.ultrakernel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ultrakernel.R;

import static com.ultrakernel.util.Config.Android_d_kernel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KernelFragment extends Fragment {

    private TextView kerne_info;
    private Button GovernorButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_kernel, container, false);

        kerne_info = (TextView) view.findViewById(R.id.kInfo);
        GovernorButton=(Button)view.findViewById(R.id.tmp_button);
        GovernorButton.setText("Choose Governor");
        GovernorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GovernorOptionDialogFragment fragment = new GovernorOptionDialogFragment();
                fragment.show(getActivity().getSupportFragmentManager(), "power_dialog_fragment");
            }
        });
        kerne_info.setText("" + Android_d_kernel());
        return view;
    }

}
