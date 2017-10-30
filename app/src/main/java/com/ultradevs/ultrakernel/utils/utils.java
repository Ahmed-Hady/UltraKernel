package com.ultradevs.ultrakernel.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

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
    public static boolean existFile(String file) {
        return existFile(file, true);
    }

    public static boolean existFile(String file, boolean root) {
        return existFile(file, root ? RootUtils.getSU() : null);
    }

    public static boolean existFile(String file, RootUtils.SU su) {
        return su == null ? new File(file).exists() : new RootFile(file, su).exists();
    }

    public static String readFile(String file) {
        return readFile(file, true);
    }

    public static String readFile(String file, boolean root) {
        return readFile(file, root ? RootUtils.getSU() : null);
    }

    public static String readFile(String file, RootUtils.SU su) {
        if (su != null) return new RootFile(file, su).readFile();

        StringBuilder s = null;
        FileReader fileReader = null;
        BufferedReader buf = null;
        try {
            fileReader = new FileReader(file);
            buf = new BufferedReader(fileReader);

            String line;
            s = new StringBuilder();
            while ((line = buf.readLine()) != null) s.append(line).append("\n");
        } catch (FileNotFoundException ignored) {
            Log.e(LOG_TAG, "File does not exist " + file);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to read " + file);
        } finally {
            try {
                if (fileReader != null) fileReader.close();
                if (buf != null) buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s == null ? null : s.toString().trim();
    }
    public static int strToInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }
}
