package com.ultradevs.ultrakernel.utils;

/**
 * Created by ahmedhady on 30/10/17.
 */

public class RootFile {

    private final String mFile;
    private RootUtils.SU mSU;

    public RootFile(String file, RootUtils.SU su) {
        mFile = file;
        mSU = su;
    }
    public boolean exists() {
        String output = mSU.runCommand("[ -e " + mFile + " ] && echo true");
        return output != null && output.equals("true");
    }

    public String readFile() {
        return mSU.runCommand("cat '" + mFile + "'");
    }

    @Override
    public String toString() {
        return mFile;
    }
}
