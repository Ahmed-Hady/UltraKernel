package com.ultrakernel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ultrakernel.R;
import com.ultrakernel.util.ShellExecuter;

import java.io.IOException;
import java.util.ArrayList;

public class CheckAdapter extends BaseAdapter
{
    public static Boolean moto;
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
                //Request Root Permission
                try {
                    Process process = Runtime.getRuntime().exec(new String[] { "su", "-", "root"});
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        if (((checkItem) getItem((position))).cmdName.contains("Information")){
            if(Shell.hasRoot()) {
                try{
                final String get_l = (eu.chainfire.libsuperuser.Shell.SU.run("cat /sys/class/leds/charging/max_brightness")).toString();
                if (get_l.equals("[255]")) {
                    eu.chainfire.libsuperuser.Shell.SU.run("echo 1 > " + "/data/data/com.ultrakernel/moto_led");
                    moto = true;
                } else if (get_l.equals("[0]")) {
                    eu.chainfire.libsuperuser.Shell.SU.run("echo 0 > " + "/data/data/com.ultrakernel/moto_led");
                    moto = false;
                }
                } catch (Exception e) {}
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
