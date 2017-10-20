package com.ultradevs.ultrakernel.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.fragments.deviceInfo.BatteryInfoFragment;
import com.ultradevs.ultrakernel.fragments.deviceInfo.KernelInfoFragment;
import com.ultradevs.ultrakernel.fragments.deviceInfo.SocInfoFragment;
import com.ultradevs.ultrakernel.fragments.deviceInfo.SystemInfoFragment;
import com.ultradevs.ultrakernel.fragments.kernel_features.cpugov.CpuGovFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SystemInfoFragment mSysInfo;
    private BatteryInfoFragment mBatInfo;
    private KernelInfoFragment mKernelInfo;
    private SocInfoFragment mSocInfo;
    private CpuGovFragment mCpuGov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Define Fragments */
        mSysInfo = new SystemInfoFragment();
        mBatInfo = new BatteryInfoFragment();
        mKernelInfo = new KernelInfoFragment();
        mSocInfo = new SocInfoFragment();
        mCpuGov = new CpuGovFragment();
        updateFragment(this.mSysInfo);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sys) {
            updateFragment(this.mSysInfo);
        } else if (id == R.id.nav_bat) {
            updateFragment(this.mBatInfo);
        } else if (id == R.id.nav_kernel) {
            updateFragment(this.mKernelInfo);
        } else if (id == R.id.nav_proc) {
            updateFragment(this.mSocInfo);
        } else if (id == R.id.nav_k_cpu_gov) {
            updateFragment(this.mCpuGov);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void updateFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.content, fragment);
        ft.commit();
    }
}
