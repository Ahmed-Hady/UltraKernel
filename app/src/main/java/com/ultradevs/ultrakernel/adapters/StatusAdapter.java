package com.ultradevs.ultrakernel.adapters;

/**
 * Created by ahmedhady on 16/10/17.
 */

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends ArrayAdapter<InfoList> {
    public StatusAdapter(Context context, ArrayList<InfoList> batInfo) {
        super(context, 0, batInfo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InfoList infolist = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.info_list_item, parent, false);
        }
        // Lookup view for data population
        TextView mTitle = (TextView) convertView.findViewById(R.id.title);
        TextView mSummary = (TextView) convertView.findViewById(R.id.summary);
        // Populate the data into the template view using the data object
        mTitle.setText(infolist.title);
        mSummary.setText(infolist.summary);
        // Return the completed view to render on screen
        return convertView;
    }
}


