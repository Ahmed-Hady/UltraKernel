package com.ultrakernel.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ultrakernel.R;
import com.ultrakernel.util.IntroPrefManager;

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
import static com.ultrakernel.util.ShellExecuter.hasBusybox;
import static com.ultrakernel.util.ShellExecuter.hasRoot;
import static com.ultrakernel.util.ShellExecuter.has_Systemless_Busybox;
import static com.ultrakernel.util.ShellExecuter.has_systemless_Root;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext, getRoot;
    private IntroPrefManager prefManager;

    public void PutBooleanPreferences(String Name,Boolean Function) {
        SharedPreferences settings = getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new IntroPrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
                if(position==1){
                    btnNext.setVisibility(View.GONE);
                    btnSkip.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public void callRoot(View v){
        Button root = (Button) findViewById(R.id.btngetroot);
        if(hasRoot()) {
            PutBooleanPreferences("Root", TRUE);
            eu.chainfire.libsuperuser.Shell.SU.run("echo Hello");
            btnNext.setVisibility(View.VISIBLE);
            root.setVisibility(View.GONE);
        }else if (has_systemless_Root()) {
            PutBooleanPreferences("Root", TRUE);
            eu.chainfire.libsuperuser.Shell.SU.run("echo Hello");
            btnNext.setVisibility(View.VISIBLE);
            root.setVisibility(View.GONE);
        }else{
            PutBooleanPreferences("Root",FALSE);
            TextView rootAlert = (TextView) findViewById(R.id.rootAlert);
            rootAlert.setVisibility(View.VISIBLE);
        }
    }

    public void callInfo(View v){
        Button info = (Button) findViewById(R.id.sysInfo);
                /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(hasBusybox()) {
                PutBooleanPreferences("bb", TRUE);
                }else if (has_Systemless_Busybox()) {
                    PutBooleanPreferences("bb", TRUE);
                }else{
                    PutBooleanPreferences("bb",FALSE);
                }


                //MOTO
                try {
                    if (get_l().contains("255")) {
                        PutBooleanPreferences("Moto", TRUE);
                    } else if (get_l().contains("0")) {
                        PutBooleanPreferences("Moto", FALSE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //d2w
                if (new File(LGE_TOUCH_DT2W).exists()) {
                    PutStringPreferences("d2w", LGE_TOUCH_DT2W);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(LGE_TOUCH_CORE_DT2W).exists()) {
                    PutStringPreferences("d2w", LGE_TOUCH_CORE_DT2W);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(LGE_TOUCH_GESTURE).exists()) {
                    PutStringPreferences("d2w", LGE_TOUCH_GESTURE);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(ANDROID_TOUCH_DT2W).exists()) {
                    PutStringPreferences("d2w", ANDROID_TOUCH_DT2W);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(ANDROID_TOUCH2_DT2W).exists()) {
                    PutStringPreferences("d2w", ANDROID_TOUCH2_DT2W);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(TOUCH_PANEL_DT2W).exists()) {
                    PutStringPreferences("d2w", TOUCH_PANEL_DT2W);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(DT2W_WAKEUP_GESTURE).exists()) {
                    PutStringPreferences("d2w", DT2W_WAKEUP_GESTURE);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(DT2W_ENABLE).exists()) {
                    PutStringPreferences("d2w", DT2W_ENABLE);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(DT2W_WAKE_GESTURE).exists()) {
                    PutStringPreferences("d2w", DT2W_WAKE_GESTURE);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(DT2W_WAKE_GESTURE_2).exists()) {
                    PutStringPreferences("d2w", DT2W_WAKE_GESTURE_2);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else if (new File(DT2W_FT5X06).exists()) {
                    PutStringPreferences("d2w", DT2W_FT5X06);
                    PutBooleanPreferences("d2w_exist", TRUE);
                } else {
                    PutBooleanPreferences("d2w_exist", FALSE);
                }

                if (getPreferences_bool("d2w_exist") == true) {
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

                if (new File(FORCE_FAST_CHARGE).exists()) {
                    PutBooleanPreferences("usbFCH_exist", TRUE);
                } else {
                    PutBooleanPreferences("usbFCH_exist", FALSE);
                }

                if (getPreferences_bool("usbFCH_exist") == true) {
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

                if (new File(ARCH_POWER).exists()) {
                    PutBooleanPreferences("archP_exist", TRUE);
                } else {
                    PutBooleanPreferences("archP_exist", FALSE);
                }

                if (getPreferences_bool("archP_exist") == true) {
                    try {
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

            }
        }, 0);
        info.setVisibility(View.GONE);
    }


    public boolean getPreferences_bool(String Name){
        SharedPreferences settings = getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getBoolean(Name, Boolean.parseBoolean(null));
    }

    public void PutStringPreferences(String Name,String Function){
        SharedPreferences settings = getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, Function);
        editor.commit();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
