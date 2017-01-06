package com.ultrakernel;

/**
 * Created by sumit on 29/10/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.ultrakernel.activity.MainActivity;
import com.ultrakernel.adapter.CheckAdapter;
import com.ultrakernel.util.ShellExecuter;

public class CheckSplash extends Activity {

    private ShellExecuter Shell;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_check_splash);

        final CheckAdapter adapter = new CheckAdapter(getBaseContext());
        final ListView splash = (ListView) findViewById(R.id.splash_list);
        splash.setAdapter(adapter);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(Shell.hasRoot()) {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(CheckSplash.this,MainActivity.class);
                CheckSplash.this.startActivity(mainIntent);
                CheckSplash.this.finish();
                }else{
                    setContentView(R.layout.activity_error_splash);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}