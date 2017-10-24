package com.ultradevs.ultrakernel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ultradevs.ultrakernel.utils.cpu_utils.CpuShellUtils;

/**
 * Created by: veli
 * Date: 10/20/16 4:05 PM
 */

public class Activity extends AppCompatActivity
{
    public static CpuShellUtils mShellInstance;

    public CpuShellUtils getShellSession()
    {
        if (this.mShellInstance == null || this.mShellInstance.getSession() == null)
            loadShell();

        return this.mShellInstance;
    }

    protected void loadShell()
    {
        this.mShellInstance = new CpuShellUtils(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
