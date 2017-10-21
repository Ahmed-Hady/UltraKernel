package com.ultradevs.ultrakernel.adapters;

/**
 * Created by ahmedhady on 16/10/17.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;

import java.util.ArrayList;

public class InitAdapter extends ArrayAdapter<initList> {
    public InitAdapter(Context context, ArrayList<initList> init) {
        super(context, 0, init);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        initList initList = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.init_list_item, parent, false);
        }
        // Lookup view for data population
        CheckBox mTitle = (CheckBox) convertView.findViewById(R.id.title);
        TextView mSummary = (TextView) convertView.findViewById(R.id.summary);
        RelativeLayout mframe = (RelativeLayout) convertView.findViewById(R.id.listitem_frame);

        // Populate the data into the template view using the data object
        mTitle.setText(initList.title);
        mTitle.setChecked(initList.checked);
        mSummary.setText(initList.summary);
        mframe.setBackgroundColor(getContext().getColor(initList.bg));

        // Return the completed view to reender on screen
        return convertView;
    }
}


