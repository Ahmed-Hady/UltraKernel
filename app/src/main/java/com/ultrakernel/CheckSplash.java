package com.ultrakernel;

/**
 * Created by sumit on 29/10/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.ultrakernel.activity.MainActivity;
import com.ultrakernel.util.ShellExecuter;

import java.io.File;

import static com.ultrakernel.util.Config.ANDROID_TOUCH2_DT2W;
import static com.ultrakernel.util.Config.ANDROID_TOUCH_DT2W;
import static com.ultrakernel.util.Config.ARCH_POWER;
import static com.ultrakernel.util.Config.DT2W_ENABLE;
import static com.ultrakernel.util.Config.DT2W_FT5X06;
import static com.ultrakernel.util.Config.DT2W_WAKEUP_GESTURE;
import static com.ultrakernel.util.Config.DT2W_WAKE_GESTURE;
import static com.ultrakernel.util.Config.DT2W_WAKE_GESTURE_2;
import static com.ultrakernel.util.Config.FORCE_FAST_CHARGE;
import static com.ultrakernel.util.Config.LGE_TOUCH_CORE_DT2W;
import static com.ultrakernel.util.Config.LGE_TOUCH_DT2W;
import static com.ultrakernel.util.Config.LGE_TOUCH_GESTURE;
import static com.ultrakernel.util.Config.TOUCH_PANEL_DT2W;
import static com.ultrakernel.util.Config.get_d;
import static com.ultrakernel.util.Config.get_l;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class CheckSplash extends Activity {

    int ERRORS;

    final String[] descriptionData = {"Root", "BusyBox", "Information", "Starting"};

    private ShellExecuter Shell;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3500;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_check_splash);

        final StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.check);
        stateProgressBar.setStateDescriptionData(descriptionData);


        stateProgressBar.setStateSize(40f);
        stateProgressBar.setStateNumberTextSize(20f);
        stateProgressBar.setStateLineThickness(10f);

        stateProgressBar.enableAnimationToCurrentState(true);

        stateProgressBar.setDescriptionTopSpaceIncrementer(10f);
        stateProgressBar.setStateDescriptionSize(18f);


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(Shell.hasRoot()) {
                    PutBooleanPreferences("Root", TRUE);
                    eu.chainfire.libsuperuser.Shell.SU.run("echo Hello");
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                }else if (Shell.has_systemless_Root()) {
                    PutBooleanPreferences("Root", TRUE);
                    eu.chainfire.libsuperuser.Shell.SU.run("echo Hello");
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                }else{
                    PutBooleanPreferences("Root",FALSE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                    stateProgressBar.setForegroundColor(ContextCompat.getColor(getBaseContext(), R.color.error));
                    ERRORS += 1;
                }

                if(Shell.hasBusybox()) {
                    PutBooleanPreferences("bb", TRUE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                }else if (Shell.has_Systemless_Busybox()) {
                    PutBooleanPreferences("bb", TRUE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                }else{
                    PutBooleanPreferences("bb",FALSE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                    stateProgressBar.setForegroundColor(ContextCompat.getColor(getBaseContext(), R.color.error));
                    ERRORS += 1;
                }


                //MOTO
                try {
                    if (get_l().contains("255")) {
                        PutBooleanPreferences("Moto",TRUE);
                    } else if (get_l().contains("0")) {
                        PutBooleanPreferences("Moto",FALSE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //d2w
                if(new File(LGE_TOUCH_DT2W).exists()){
                    PutStringPreferences("d2w",LGE_TOUCH_DT2W);
                    PutBooleanPreferences("d2w_exist",TRUE);
                }else if(new File(LGE_TOUCH_CORE_DT2W).exists()){
                    PutStringPreferences("d2w",LGE_TOUCH_CORE_DT2W);
                    PutBooleanPreferences("d2w_exist",TRUE);
                }else if(new File(LGE_TOUCH_GESTURE).exists()){
                    PutStringPreferences("d2w",LGE_TOUCH_GESTURE);
                    PutBooleanPreferences("d2w_exist",TRUE);
                }else if(new File(ANDROID_TOUCH_DT2W).exists()) {
                    PutStringPreferences("d2w",ANDROID_TOUCH_DT2W);
                    PutBooleanPreferences("d2w_exist",TRUE);
                }else if(new File(ANDROID_TOUCH2_DT2W).exists()){
                    PutStringPreferences("d2w",ANDROID_TOUCH2_DT2W);
                    PutBooleanPreferences("d2w_exist",TRUE);
                } else if(new File(TOUCH_PANEL_DT2W).exists()){
                    PutStringPreferences("d2w",TOUCH_PANEL_DT2W);
                    PutBooleanPreferences("d2w_exist",TRUE);
                }else if(new File(DT2W_WAKEUP_GESTURE).exists()){
                    PutStringPreferences("d2w",DT2W_WAKEUP_GESTURE);
                    PutBooleanPreferences("d2w_exist",TRUE);
                } else if(new File(DT2W_ENABLE).exists()){
                    PutStringPreferences("d2w",DT2W_ENABLE);
                    PutBooleanPreferences("d2w_exist",TRUE);
                }else if(new File(DT2W_WAKE_GESTURE).exists()){
                    PutStringPreferences("d2w",DT2W_WAKE_GESTURE);
                    PutBooleanPreferences("d2w_exist",TRUE);
                }else if(new File(DT2W_WAKE_GESTURE_2).exists()){
                    PutStringPreferences("d2w",DT2W_WAKE_GESTURE_2);
                    PutBooleanPreferences("d2w_exist",TRUE);
                } else if(new File(DT2W_FT5X06).exists()){
                    PutStringPreferences("d2w",DT2W_FT5X06);
                    PutBooleanPreferences("d2w_exist",TRUE);
                }else{
                    PutBooleanPreferences("d2w_exist",FALSE);
                }

                if(getPreferences_bool("d2w_exist")==true){
                    try {
                        if (get_d().contains("1")) {
                            PutBooleanPreferences("d2w_enable", TRUE);
                        } else if (get_d().contains("0")) {
                            PutBooleanPreferences("d2w_enable", FALSE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Fast Charge

                if(new File(FORCE_FAST_CHARGE).exists()) {
                    PutBooleanPreferences("usbFCH_exist", TRUE);
                }else{
                    PutBooleanPreferences("usbFCH_exist", FALSE);
                }

                if(getPreferences_bool("usbFCH_exist")==true){
                    String get_fch = (eu.chainfire.libsuperuser.Shell.SU.run("cat " + FORCE_FAST_CHARGE)).toString();
                    try {
                        if (get_fch.contains("1")) {
                            PutBooleanPreferences("usbFCH_enable", TRUE);
                        } else if (get_fch.contains("0")) {
                            PutBooleanPreferences("usbFCH_enable", FALSE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // ARCH POWER

                if(new File(ARCH_POWER).exists()) {
                    PutBooleanPreferences("archP_exist", TRUE);
                }else{
                    PutBooleanPreferences("archP_exist", FALSE);
                }

                if(getPreferences_bool("archP_exist")==true){
                    try{
                        String getArch = (eu.chainfire.libsuperuser.Shell.SH.run("cat " + ARCH_POWER)).toString();

                        if (getArch.contains("1")) {
                            PutBooleanPreferences("archP_enable", TRUE);
                        } else if (getArch.contains("0")) {
                            PutBooleanPreferences("archP_enable", FALSE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);

                if (ERRORS == 2){
                    TextView plsWait = (TextView) findViewById(R.id.wait);
                    TextView errorS = (TextView) findViewById(R.id.ERROR_S);
                    TextView E1 = (TextView) findViewById(R.id.E1);
                    TextView E2 = (TextView) findViewById(R.id.E2);
                    plsWait.setVisibility(View.GONE);
                    stateProgressBar.setVisibility(View.GONE);
                    errorS.setVisibility(View.VISIBLE);
                    E1.setVisibility(View.VISIBLE);
                    E2.setVisibility(View.VISIBLE);
                }else{
                    Intent mainIntent = new Intent(CheckSplash.this,MainActivity.class);
                    CheckSplash.this.startActivity(mainIntent);
                    CheckSplash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    //*********************************** Getting & Setting Info **************************************
    public void PutStringPreferences(String Name,String Function){
        SharedPreferences settings = getBaseContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, Function);
        editor.commit();
    }

    public String getStringPreferences(String Name){
        String o;
        SharedPreferences settings = getBaseContext().getSharedPreferences(Name, 0); // 0 - for private mode
        o=settings.getString(Name,null);
        return o;
    }

    public void PutBooleanPreferences(String Name,Boolean Function){
        SharedPreferences settings = getBaseContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }

    public boolean getPreferences_bool(String Name){
        SharedPreferences settings = getBaseContext().getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getBoolean(Name, Boolean.parseBoolean(null));
    }

//*************************************************************************************************
}
