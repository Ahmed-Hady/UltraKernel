package com.ultrakernel.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.ultrakernel.R;
import com.ultrakernel.fragment.CPUToolsFragment;
import com.ultrakernel.fragment.Creditsfragement;
import com.ultrakernel.fragment.KernelFragment;
import com.ultrakernel.fragment.SystemInfo_fragement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eu.chainfire.libsuperuser.Shell;
import me.drakeet.materialdialog.MaterialDialog;

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
import static com.ultrakernel.util.Config.UpdaterUrl;
import static com.ultrakernel.util.Config.get_d;
import static com.ultrakernel.util.Config.get_l;
import static com.ultrakernel.util.ShellCommands.RAM_IMP;
import static com.ultrakernel.util.ShellCommands.boost_system;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Creditsfragement mCredits;
    private SystemInfo_fragement mSystemInfo;
    private KernelFragment mKernel;
    private CPUToolsFragment mCpu;
    private AppUpdaterUtils mAppUpdater;
    private PanterDialog UpdateDialog;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Get INFO */
        CheckStart();

        /*Fragments*/
        mCredits = new Creditsfragement();
        mSystemInfo = new SystemInfo_fragement();
        mKernel = new KernelFragment();
        mCpu = new CPUToolsFragment();

        /*default fragment*/
        updateFragment(this.mSystemInfo);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.menu_fab);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(190);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

//        if(!getPreferences_bool("autoup"))
//            PutBooleanPreferences("autoup",Boolean.TRUE);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if (pref.getBoolean("night", Boolean.parseBoolean(null)) == true) {
            //setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme);
        }

        if (pref.getBoolean("autoup", Boolean.parseBoolean(null)) == true) {
            Updater();
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void Updater() {
        mAppUpdater = new AppUpdaterUtils(this);
        mAppUpdater
                .setUpdateFrom(UpdateFrom.XML)
                .setUpdateXML(UpdaterUrl)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(final Update update, Boolean isUpdateAvailable) {
                        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                        Log.d("AppUpdater", update.getLatestVersion() + ", " + update.getUrlToDownload() + ", " + Boolean.toString(isUpdateAvailable));
                        if (isUpdateAvailable == true) {
                            UpdateDialog = new PanterDialog(MainActivity.this);
                            UpdateDialog.setTitle("Update Found")
                                    .setHeaderBackground(R.color.colorPrimaryDark)
                                    .setMessage("Changelog :- \n\n" + update.getReleaseNotes())
                                    .setPositive("Download", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Uri uri = Uri.parse(String.valueOf(update.getUrlToDownload()));
                                            DownloadManager.Request request = new DownloadManager.Request(uri);
                                            String fileName = uri.getLastPathSegment();
                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            Long reference = downloadManager.enqueue(request);
                                            UpdateDialog.dismiss();

                                        }
                                    })
                                    .setNegative("DISMISS")
                                    .isCancelable(false)
                                    .withAnimation(Animation.POP)
                                    .show();

                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater", "Something went wrong");
                    }
                });
        mAppUpdater.start();
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_systemInfo) {
            final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.menu_fab);
            fabMenu.setVisibility(FloatingActionsMenu.VISIBLE);
            updateFragment(mSystemInfo);
        } else if (id == R.id.nav_kernel_tweaks) {
            final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.menu_fab);
            fabMenu.setVisibility(FloatingActionsMenu.GONE);
            updateFragment(mKernel);
        } else if (id == R.id.nav_cpu_tweaks) {
            final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.menu_fab);
            fabMenu.setVisibility(FloatingActionsMenu.GONE);
            updateFragment(mCpu);
        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_credits) {
            updateFragment(mCredits);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void updateFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit);

        ft.replace(R.id.content_frame, fragment);
        ft.commit();

    }

    public void boost(View v) {
        if (getPreferences_bool("bb") == true) {
            boost_system(this);
        } else {
            mMaterialDialog.show();
        }
    }

    public void kill_drains(View v) {
        RAM_IMP(this);
    }

    MaterialDialog mMaterialDialog = new MaterialDialog(this)
            .setTitle("Error!")
            .setMessage("BusyBox Not Found.\n")
            .setPositiveButton("INSTALL", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String appPackageName = "stericson.busybox";
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    mMaterialDialog.dismiss();
                }
            })
            .setNegativeButton("NO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            });

    public void fstrim(View v) {
        try {
            fstrim_tmp();
            Toast.makeText(getApplicationContext(), Shell.SU.run("cd " + package_path + " && fstrim -v /system").toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), Shell.SU.run("cd " + package_path + " && fstrim -v /data").toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), Shell.SU.run("cd " + package_path + " && fstrim -v /cache && rm -rf fstrim && cd").toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final String package_path = "/data/data/com.ultrakernel/";
    public final String fstrim = "fstrim";

    public void fstrim_tmp() throws IOException {

        OutputStream myOutput = new FileOutputStream(package_path + fstrim);
        byte[] buffer = new byte[1024];
        int length;
        InputStream myInput = getBaseContext().getAssets().open("fstrim");
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();

    }
    public boolean getPreferences_bool(String Name){
        SharedPreferences settings = getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getBoolean(Name, Boolean.parseBoolean(null));
    }
    public void CheckStart() {


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //if(Shell.hasBusybox()) {
                PutBooleanPreferences("bb", TRUE);
                //}else if (Shell.has_Systemless_Busybox()) {
                //    PutBooleanPreferences("bb", TRUE);
                //}else{
                //    PutBooleanPreferences("bb",FALSE);
                //}


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
    }
    //********************************* Getting & Setting Info ***********************************
    public void PutStringPreferences(String Name,String Function){
        SharedPreferences settings = getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, Function);
        editor.commit();
    }

    public void PutBooleanPreferences(String Name,Boolean Function){
        SharedPreferences settings = getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }
    //********************************************************************************************

}

