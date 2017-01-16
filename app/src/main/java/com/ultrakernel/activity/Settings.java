package com.ultrakernel.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.ultrakernel.R;

public class Settings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    @SuppressLint("ValidFragment")
    public class MyPreferenceFragment extends PreferenceFragment {
        SharedPreferences mPrefs;

        @Override
        public void onStart() {
            super.onStart();
            mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            final SwitchPreference AutoUp = (SwitchPreference) findPreference("autoup");

            if (AutoUp != null) {
                //Update the operations like storing, updating UI etc... on pref change.
                AutoUp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isChecked) {

                        boolean isCheckedON = ((Boolean) isChecked).booleanValue();

                        SharedPreferences.Editor e = mPrefs.edit();

                        if (isCheckedON) {
                            AutoUp.setSummary("App periodically Updates is ON"); //update summary text dynamically
                            e.putBoolean("autoup", true);
                            e.commit();
                        }else{
                            AutoUp.setSummary("App periodically Updates is OFF");
                            e.putBoolean("autoup", false);
                            e.commit();
                        }
                        return true;
                    }
                });


            }
        }

    }
}