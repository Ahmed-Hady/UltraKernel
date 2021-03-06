package com.ultradevs.ultrakernel.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.InitAdapter;
import com.ultradevs.ultrakernel.adapters.initList;
import com.ultradevs.ultrakernel.utils.RootUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.ultradevs.ultrakernel.utils.ShellExecuter.isRoot;

public class InitActivity extends Activity {

    ListView LVinit;
    ArrayList<initList> arrayOfinit = new ArrayList<initList>();
    public InitAdapter adapter;

    int error = 0;

    public final static String LOG_TAG = "UltraKernel";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        adapter = new InitAdapter(getBaseContext(), arrayOfinit);
        LVinit = findViewById(R.id.initlist);
        LVinit.setAdapter(adapter);

        if(RootUtils.rootAccess()==true){
            adapter.add(new initList("Root Status", Boolean.TRUE, "Device is Rooted", R.color.light_green));
            RootUtils.getSU();
            adapter.add(new initList("Root Access", Boolean.TRUE, "Root Access Available", R.color.light_green));

        }else{
            adapter.add(new initList("Root Status", Boolean.FALSE, "Device isn't Root", R.color.light_red));
            error += 2;
        }

        if(RootUtils.busyboxInstalled()==true){
            adapter.add(new initList("BusyBox", Boolean.TRUE, "Installed", R.color.light_green));
        }else{
            adapter.add(new initList("BusyBox", Boolean.FALSE, "Not Installed", R.color.light_red));
            error += 1;
        }

        if(error >= 2){
            TextView loading = (TextView) findViewById(R.id.loading);
            loading.setText("Error .. Check the error list");
            loading.setTextColor(getColor(R.color.red));
        }else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    InitActivity.this.finish();
                }
            }, 2000);
        }

    }

}
