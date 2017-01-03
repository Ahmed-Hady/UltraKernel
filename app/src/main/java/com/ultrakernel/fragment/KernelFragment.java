package com.ultrakernel.fragment;


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

import java.io.File;

import eu.chainfire.libsuperuser.Shell;

import static com.ultrakernel.util.CPUInfo.cur_gov;
import static com.ultrakernel.util.Config.ANDROID_TOUCH2_DT2W;
import static com.ultrakernel.util.Config.ANDROID_TOUCH_DT2W;
import static com.ultrakernel.util.Config.Android_d_kernel;
import static com.ultrakernel.util.Config.Android_d_manuf;
import static com.ultrakernel.util.Config.DT2W_ENABLE;
import static com.ultrakernel.util.Config.DT2W_FT5X06;
import static com.ultrakernel.util.Config.DT2W_WAKEUP_GESTURE;
import static com.ultrakernel.util.Config.DT2W_WAKE_GESTURE;
import static com.ultrakernel.util.Config.DT2W_WAKE_GESTURE_2;
import static com.ultrakernel.util.Config.LGE_TOUCH_CORE_DT2W;
import static com.ultrakernel.util.Config.LGE_TOUCH_DT2W;
import static com.ultrakernel.util.Config.LGE_TOUCH_GESTURE;
import static com.ultrakernel.util.Config.TOUCH_PANEL_DT2W;
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
                        Thread.sleep(1000);
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

        if(new File(LGE_TOUCH_DT2W).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        }else if(new File(LGE_TOUCH_CORE_DT2W).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        }else if(new File(LGE_TOUCH_GESTURE).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        }else if(new File(ANDROID_TOUCH_DT2W).exists()) {
            d2w.setVisibility(RelativeLayout.VISIBLE);
        }else if(new File(ANDROID_TOUCH2_DT2W).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        } else if(new File(TOUCH_PANEL_DT2W).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        }else if(new File(DT2W_WAKEUP_GESTURE).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        } else if(new File(DT2W_ENABLE).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        }else if(new File(DT2W_WAKE_GESTURE).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        }else if(new File(DT2W_WAKE_GESTURE_2).exists()){
            d2w.setVisibility(RelativeLayout.VISIBLE);
        } else if(new File(DT2W_FT5X06).exists()){
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
        }else{
            moto_L.setVisibility(RelativeLayout.GONE);
        }

        motoL = (Switch) view.findViewById(R.id.motoL);


        Thread l = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final String get_l = (Shell.SU.run("cat /sys/class/leds/charging/max_brightness")).toString();

                                if (get_l.equals("[255]")) {
                                    motoL.setChecked(true);
                                }else if (get_l.equals("[0]")){
                                    motoL.setChecked(false);
                                }

                                motoL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView,
                                                                 boolean isChecked) {
                                        if(isChecked){
                                            Shell.SU.run("echo 255 > /sys/class/leds/charging/max_brightness");
                                        }else if(!isChecked){
                                            Shell.SU.run("echo 0 > /sys/class/leds/charging/max_brightness");
                                        }

                                    }
                                });
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
        };

        l.start();

    return view;
    }
}
