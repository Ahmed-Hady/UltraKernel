package com.ultrakernel.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.javiersantos.appupdater.AppUpdater;
import com.ultrakernel.util.ShellUtils;

import static com.ultrakernel.util.Config.UpdaterUrl;


/**
 * Created by: veli
 * Date: 10/20/16 4:05 PM
 */

public class Activity extends AppCompatActivity
{
    public static ShellUtils mShellInstance;
    private AppUpdater mAppUpdater;

    public ShellUtils getShellSession()
    {
        if (this.mShellInstance == null || this.mShellInstance.getSession() == null)
            loadShell();

        return this.mShellInstance;
    }

    protected void loadShell()
    {
        this.mShellInstance = new ShellUtils(this);
    }

    public void OnInit(){
        Updater();
    }
    public void Updater(){
        mAppUpdater=new AppUpdater(this);
        mAppUpdater.setUpdateXML(UpdaterUrl)
                    .setTitleOnUpdateAvailable("Update available")
                    .setContentOnUpdateAvailable("Check out the latest version available of my app!")
                    .setTitleOnUpdateNotAvailable("Update not available")
                    .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
                    .setButtonUpdate("Update now?")
                    .setButtonDismiss("Maybe later")
                    .setButtonDoNotShowAgain("Huh, not interested");

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        OnInit();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mShellInstance != null)
        {
            mShellInstance.closeSession();
            mShellInstance = null;
        }
    }
}
