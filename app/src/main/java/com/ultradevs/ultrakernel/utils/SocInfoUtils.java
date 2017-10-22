package com.ultradevs.ultrakernel.utils;

import android.content.Context;
import android.content.res.Resources;

import com.ultradevs.ultrakernel.R;

import java.util.ResourceBundle;

/**
 * Created by ahmedhady on 20/10/17.
 */

public class SocInfoUtils {

    private static ShellExecuter mShell;

    public static String SocName(){
        mShell.command = "grep -m1 Hardware /proc/cpuinfo";
        String[] splitter = mShell.runAsRoot().split(":");
        return splitter[1].trim();
    }
    public static String Ncores(){
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }
    public static String CoresInWords(Context context){
        if(Ncores().contains("2")){
            return context.getString(R.string.dual_core).toString();
        } else if(Ncores().contains("4")){
            return context.getString(R.string.quad_core).toString();
        } else if(Ncores().contains("6")){
            return context.getString(R.string.hexa_core).toString();
        } else if(Ncores().contains("8")){
            return context.getString(R.string.octa_core).toString();
        } else if(Ncores().contains("10")){
            return context.getString(R.string.deca_core).toString();
        }
        return null;
    }
    public static String getProcManuf(Context context) {
        String[] man = SocName().split(" ");
        return man[0];
    }
}
