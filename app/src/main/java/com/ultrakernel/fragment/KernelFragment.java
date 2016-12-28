package com.ultrakernel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ultrakernel.R;

import java.io.File;

import static com.ultrakernel.util.Config.ANDROID_TOUCH2_DT2W;
import static com.ultrakernel.util.Config.ANDROID_TOUCH_DT2W;
import static com.ultrakernel.util.Config.Android_d_kernel;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_kernel, container, false);

        kerne_info = (TextView) view.findViewById(R.id.kInfo);
        kerne_info.setText("" + Android_d_kernel());

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

    return view;
    }

}
