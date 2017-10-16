package com.ultradevs.ultrakernel.adapters;

/**
 * Created by ahmedhady on 16/10/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;

import java.util.ArrayList;

public class BatteryStatusAdapter extends ArrayAdapter<bat_status_list> {
    public BatteryStatusAdapter(Context context, ArrayList<bat_status_list> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        bat_status_list infolist = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.info_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.title);
        TextView tvHome = (TextView) convertView.findViewById(R.id.summary);
        // Populate the data into the template view using the data object
        tvName.setText(infolist.title);
        tvHome.setText(infolist.summary);
        // Return the completed view to render on screen
        return convertView;
    }
}