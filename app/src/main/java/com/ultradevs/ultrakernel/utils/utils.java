package com.ultradevs.ultrakernel.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.stericson.RootTools.RootTools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import eu.chainfire.libsuperuser.Shell;

import static com.ultradevs.ultrakernel.activities.InitActivity.LOG_TAG;

/**
 * Created by ahmedhady on 23/10/17.
 */

public class utils {

    public static double roundOneDecimals(double d)
    {
        DecimalFormat oneDForm = new DecimalFormat("#.#");
        return Double.valueOf(oneDForm.format(d));
    }
    public static double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
