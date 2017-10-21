package com.ultradevs.ultrakernel.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.stericson.RootTools.RootTools;
import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.InitAdapter;
import com.ultradevs.ultrakernel.adapters.initList;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.ultradevs.ultrakernel.utils.ShellExecuter.getRoot;
import static com.ultradevs.ultrakernel.utils.ShellExecuter.isRoot;

public class InitActivity extends Activity {

    ListView LVinit;
    ArrayList<initList> arrayOfinit = new ArrayList<initList>();
    public InitAdapter adapter;

    int error = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        adapter = new InitAdapter(getBaseContext(), arrayOfinit);
        LVinit = findViewById(R.id.initlist);
        LVinit.setAdapter(adapter);

        if(RootTools.isRootAvailable()==true){
            adapter.add(new initList("Root Status", Boolean.TRUE, "Device is Rooted", R.color.light_green));

            getRoot();

            if(RootTools.isAccessGiven()==true){
                adapter.add(new initList("Root Access", Boolean.TRUE, "Root Access Available", R.color.light_green));
            }else{
                adapter.add(new initList("Root Access", Boolean.FALSE, "Root Access Denied", R.color.light_red));
                error += 1;
            }

        }else{
            adapter.add(new initList("Root Status", Boolean.FALSE, "Device isn't Root", R.color.light_red));
            error += 1;
        }

        if(RootTools.isBusyboxAvailable()==true){
            adapter.add(new initList("BusyBox", Boolean.TRUE, "Installed", R.color.light_green));
        }else{
            adapter.add(new initList("BusyBox", Boolean.FALSE, "Not Installed", R.color.light_red));
            error += 1;
        }

        if(error > 2){

        }else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    InitActivity.this.finish();
                }
            }, 2500);
        }

    }

}
