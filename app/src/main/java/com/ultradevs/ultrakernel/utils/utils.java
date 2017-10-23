package com.ultradevs.ultrakernel.utils;

import java.text.DecimalFormat;

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
