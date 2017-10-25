package com.ultradevs.ultrakernel.adapters;

/**
 * Created by ahmedhady on 16/10/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.SweepGradient;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import java.util.ArrayList;

import static com.ultradevs.ultrakernel.utils.ShellExecuter.shell;

public class PrefAdapter extends ArrayAdapter<SwitchPrefList> {
    public PrefAdapter(Context context, ArrayList<SwitchPrefList> switchPerf) {
        super(context, 0, switchPerf);
    }

    public static ShellExecuter mShell;

    public void PutBooleanPreferences(String Name,Boolean Function){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SwitchPrefList switchlist = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.switch_pref_list_item, parent, false);
        }
        // Lookup view for data population
        Switch mTitle = (Switch) convertView.findViewById(R.id.title);
        TextView mSummary = (TextView) convertView.findViewById(R.id.summary);
        // Populate the data into the template view using the data object
        mTitle.setText(switchlist.title);

        mShell.command = "su -c cat " + switchlist.path;

        if(mShell.runAsRoot().equals("0")){
            mTitle.setChecked(false);
        } else if(mShell.runAsRoot().equals("1")){
            mTitle.setChecked(true);
        }
        mSummary.setText(switchlist.summary);

        mTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    shell("echo 1 > " + switchlist.path, true);
                    PutBooleanPreferences(switchlist.key,true);
                } else if (b == false) {
                    shell("echo 0 > " + switchlist.path, true);
                    PutBooleanPreferences(switchlist.key,false);
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}


