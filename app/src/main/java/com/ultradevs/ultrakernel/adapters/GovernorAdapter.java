package com.ultradevs.ultrakernel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import java.util.ArrayList;

import static com.ultradevs.ultrakernel.activities.InitActivity.LOG_TAG;

/**
 * Created by: sumit
 * Date: 10/29/16 4:28 PM
 */

public class GovernorAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<GovernorItem> mList = new ArrayList<>();
    private String Scaling_gov_path="/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    private String get_gov(){
        ShellExecuter.command="cat sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
        return ShellExecuter.runAsRoot();
    }
    private String store_gov= get_gov();
    private String[] parts = store_gov.split("\\s+"); // escape .
    private String gov1;
    private String gov2;
    private String gov3;
    private String gov4;
    private String gov5;
    private String gov6;
    private String gov7;
    private String gov8;
    private String gov9;
    private String gov10;
    private String gov11;
    private String gov12;
    private String gov13;
    private String gov14;
    private String gov15;
    private String gov16;
    private String gov17;
    public GovernorAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        gov1=parts[0];
        gov2=parts[1];
        gov3=parts[2];
        gov4=parts[3];
        gov5=parts[4];
        try
        {
            gov6 = parts[5];
            gov7 = parts[6];
            gov8 = parts[7];
            gov9 = parts[8];
            gov10 = parts[9];
            gov11 = parts[10];
            gov12 = parts[11];
            gov13 = parts[12];
            gov14 = parts[13];
            gov15 = parts[14];
            gov16 = parts[15];
            gov17 = parts[16];
        }
        catch (Exception e)
        {

        }

        /*use null for now to hide useless spaces */
        if(gov1==null){}
        else
        mList.add(new GovernorItem(gov1, "chmod 777 " + Scaling_gov_path + " && echo " + gov1 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov2==null){}
        else
        mList.add(new GovernorItem(gov2, "chmod 777 " + Scaling_gov_path + " && echo " + gov2 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov3==null){}
        else
        mList.add(new GovernorItem(gov3, "chmod 777 " + Scaling_gov_path + " && echo " + gov3 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov4==null){}
        else
        mList.add(new GovernorItem(gov4, "chmod 777 " + Scaling_gov_path + " && echo " + gov4 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov5==null){}
        else
        mList.add(new GovernorItem(gov5, "chmod 777 " + Scaling_gov_path + " && echo " + gov5 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov6==null){}
        else
        mList.add(new GovernorItem(gov6, "chmod 777 " + Scaling_gov_path + " && echo " + gov6 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov7==null){}
        else
        mList.add(new GovernorItem(gov7, "chmod 777 " + Scaling_gov_path + " && echo " + gov7 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov8==null){}
        else
        mList.add(new GovernorItem(gov8, "chmod 777 " + Scaling_gov_path + " && echo " + gov8 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov9==null){}
        else
        mList.add(new GovernorItem(gov9, "chmod 777 " + Scaling_gov_path + " && echo " + gov9 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov10==null){}
        else
        mList.add(new GovernorItem(gov10, "chmod 777 " + Scaling_gov_path + " && echo " + gov10 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov11==null){}
        else
        mList.add(new GovernorItem(gov11, "chmod 777 " + Scaling_gov_path + " && echo " + gov11 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov12==null){}
        else
        mList.add(new GovernorItem(gov12, "chmod 777 " + Scaling_gov_path + " && echo " + gov12 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov13==null){}
        else
        mList.add(new GovernorItem(gov13, "chmod 777 " + Scaling_gov_path + " && echo " + gov13 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov14==null){}
        else
        mList.add(new GovernorItem(gov14, "chmod 777 " + Scaling_gov_path + " && echo " + gov14 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov15==null){}
        else
        mList.add(new GovernorItem(gov15, "chmod 777 " + Scaling_gov_path + " && echo " + gov15 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov16==null){}
        else
        mList.add(new GovernorItem(gov16, "chmod 777 " + Scaling_gov_path + " && echo " + gov16 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));
        if(gov17==null){}
        else
        mList.add(new GovernorItem(gov17, "chmod 777 " + Scaling_gov_path + " && echo " + gov17 + " > "  + Scaling_gov_path + " && chmod 644 " + Scaling_gov_path));

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
            convertView = mInflater.inflate(R.layout.list_governor, parent, false);

        TextView text = (TextView) convertView.findViewById(R.id.list_governor_text);

        text.setText(((GovernorItem) getItem(position)).cmdName);

        return convertView;
    }

    public String getText(int position){

        String text = ((GovernorItem) getItem(position)).cmdName;
        return text;
    }

    public class GovernorItem
    {
        public String cmdName;
        public String command;

        public GovernorItem(String cmdName, String command)
        {
            this.cmdName = cmdName;
            this.command = command;
        }
    }
}
