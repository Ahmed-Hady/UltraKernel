package com.ultradevs.ultrakernel.adapters;

/**
 * Created by ahmedhady on 16/10/17.
 */

import android.content.Context;
import android.graphics.SweepGradient;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;

import java.util.ArrayList;

public class PrefAdapter extends ArrayAdapter<SwitchPrefList> {
    public PrefAdapter(Context context, ArrayList<SwitchPrefList> switchPerf) {
        super(context, 0, switchPerf);
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
        mTitle.setChecked(switchlist.check);
        mSummary.setText(switchlist.summary);
        // Return the completed view to render on screen
        return convertView;
    }
}


