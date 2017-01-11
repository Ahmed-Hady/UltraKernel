package com.ultrakernel.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ultrakernel.R;

import eu.chainfire.libsuperuser.Shell;

import static com.ultrakernel.util.CPUInfo.cur_gov;
import static com.ultrakernel.util.Config.Android_d_kernel;
import static com.ultrakernel.util.Config.Android_d_manuf;
/**
 * A simple {@link Fragment} subclass.
 */
public class KernelFragment extends Fragment {

    private TextView kerne_info;

    public KernelFragment() {
        // Required empty public constructor
    }

    private LinearLayout d2w;

    private LinearLayout moto_L;

    private Button GovernorButton;

    private Switch motoL;

    //********************************* Getting & Setting Info ***********************************
    public void PutStringPreferences(String Name,String Function){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, Function);
        editor.commit();
    }

    public String getStringPreferences(String Name){
        String o;
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0); // 0 - for private mode
        o=settings.getString(Name,null);
        return o;
    }

    public void PutBooleanPreferences(String Name,Boolean Function){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }

    public boolean getPreferences_bool(String Name){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getBoolean(Name, Boolean.parseBoolean(null));
    }

    public void RemovePreferences(String Name){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(Name);
        editor.commit();
    }
    //********************************************************************************************

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_kernel, container, false);

//************************************************************************
                        //Kernel Info
//************************************************************************

        kerne_info = (TextView) view.findViewById(R.id.kInfo);
        kerne_info.setText("" + Android_d_kernel());


//************************************************************************
                        //GOVERNORS
//************************************************************************
        //Get GOVs

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView get_gov = (TextView) view.findViewById(R.id.cur_gov);
                                get_gov.setText(cur_gov());
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
        };

        t.start();
        //Change gov
        GovernorButton=(Button)view.findViewById(R.id.change_gov);
        GovernorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GovernorOptionDialogFragment fragment = new GovernorOptionDialogFragment();
                fragment.show(getActivity().getSupportFragmentManager(), "power_dialog_fragment");
            }
        });

//************************************************************************
                        //d2w section
//***********************************************************************
        d2w=(LinearLayout)view.findViewById(R.id.d2w);

        if(getPreferences_bool("d2w_exist") == true) {
            d2w.setVisibility(RelativeLayout.VISIBLE);
        }else{
            d2w.setVisibility(RelativeLayout.GONE);
        }


//************************************************************************
        //Moto Led section
//***********************************************************************
        moto_L=(LinearLayout)view.findViewById(R.id.motoLed);

        String MOTO = "motorola";

        if (Android_d_manuf().toLowerCase().indexOf(MOTO.toLowerCase()) != -1){
            moto_L.setVisibility(RelativeLayout.VISIBLE);
            motoL = (Switch) view.findViewById(R.id.motoL);
            motoL.setChecked(getPreferences_bool("Moto"));
                                    motoL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView,
                                                                     boolean isChecked) {
                                            if(isChecked){
                                                Shell.SU.run("echo 255 > /sys/class/leds/charging/max_brightness");
                                                PutBooleanPreferences("Moto",Boolean.TRUE);
                                            }else if(!isChecked){
                                                Shell.SU.run("echo 0 > /sys/class/leds/charging/max_brightness");
                                                PutBooleanPreferences("Moto",Boolean.FALSE);
                                            }

                                        }
                                    });

        }else{
            moto_L.setVisibility(RelativeLayout.GONE);
            RemovePreferences("Moto");
        }

    return view;
    }
}
