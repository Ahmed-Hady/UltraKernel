package com.ultrakernel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ultrakernel.R;
import com.ultrakernel.util.ShellExecuter;

import java.util.ArrayList;

public class CheckAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<checkItem> mList = new ArrayList<>();

    private ShellExecuter Shell;

    public CheckAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

        mList.add(new checkItem("Checking Root Access"));
        mList.add(new checkItem("Checking BusyBox"));
        mList.add(new checkItem("Checking DT2W Existence"));
        mList.add(new checkItem("Checking Information"));
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_check, parent, false);

        TextView text = (TextView) convertView.findViewById(R.id.list_check_text);
        TextView text2 = (TextView) convertView.findViewById(R.id.check_text);

        text.setText(((checkItem) getItem(position)).cmdName);

        if (((checkItem) getItem(position)).cmdName.contains("Root")){
            if(Shell.hasRoot()) {
                text2.setText("Ok!");
            }else{
                text2.setText("Failed! :(");
                text2.setTextColor(R.color.accent_dark);
            }
        }

        if (((checkItem) getItem(position)).cmdName.contains("BusyBox")){
            if(Shell.hasBusybox()) {
                text2.setText("Ok!");
            }else{
                text2.setText("Failed! :(");
                text2.setTextColor(R.color.accent_dark);
            }
        }

        return convertView;
    }

    public class checkItem
    {
        public String cmdName;
        public checkItem(String cmdName)
        {
            this.cmdName = cmdName;
        }
    }
}
