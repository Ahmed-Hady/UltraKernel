package com.ultrakernel.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ultrakernel.R;
import com.ultrakernel.util.ShellExecuter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.ultrakernel.util.Config.ANDROID_TOUCH2_DT2W;
import static com.ultrakernel.util.Config.ANDROID_TOUCH_DT2W;
import static com.ultrakernel.util.Config.DT2W_ENABLE;
import static com.ultrakernel.util.Config.DT2W_FT5X06;
import static com.ultrakernel.util.Config.DT2W_WAKEUP_GESTURE;
import static com.ultrakernel.util.Config.DT2W_WAKE_GESTURE;
import static com.ultrakernel.util.Config.DT2W_WAKE_GESTURE_2;
import static com.ultrakernel.util.Config.LGE_TOUCH_CORE_DT2W;
import static com.ultrakernel.util.Config.LGE_TOUCH_DT2W;
import static com.ultrakernel.util.Config.LGE_TOUCH_GESTURE;
import static com.ultrakernel.util.Config.TOUCH_PANEL_DT2W;
import static com.ultrakernel.util.Config.get_d;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static com.ultrakernel.util.Config.get_l;

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
        mList.add(new checkItem("Checking Information"));
    }
    @Override
    public boolean isEnabled(int position) {
        return false;
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
                PutBooleanPreferences("Root", TRUE);
            }else if (Shell.has_systemless_Root()) {
                PutBooleanPreferences("Root", TRUE);
            }else{PutBooleanPreferences("Root",FALSE);}

            if(getPreferences_bool("Root") == true){
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
                PutBooleanPreferences("bb", TRUE);
            }else if (Shell.has_Systemless_Busybox()) {
                PutBooleanPreferences("bb", TRUE);
            }else{PutBooleanPreferences("bb",FALSE);}


            if(getPreferences_bool("bb") == true) {
                text2.setText("Ok!");
            }else{
                text2.setText("Failed! :(");
                text2.setTextColor(R.color.accent_dark);
            }
        }

        if (((checkItem) getItem((position))).cmdName.contains("Information")){

            //MOTO
            try {
                if (get_l().contains("255")) {
                    PutBooleanPreferences("Moto",TRUE);
                } else if (get_l().contains("0")) {
                    PutBooleanPreferences("Moto",FALSE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //d2w
            if(new File(LGE_TOUCH_DT2W).exists()){
                PutStringPreferences("d2w",LGE_TOUCH_DT2W);
                PutBooleanPreferences("d2w_exist",TRUE);
            }else if(new File(LGE_TOUCH_CORE_DT2W).exists()){
                PutStringPreferences("d2w",LGE_TOUCH_CORE_DT2W);
                PutBooleanPreferences("d2w_exist",TRUE);
            }else if(new File(LGE_TOUCH_GESTURE).exists()){
                PutStringPreferences("d2w",LGE_TOUCH_GESTURE);
                PutBooleanPreferences("d2w_exist",TRUE);
            }else if(new File(ANDROID_TOUCH_DT2W).exists()) {
                PutStringPreferences("d2w",ANDROID_TOUCH_DT2W);
                PutBooleanPreferences("d2w_exist",TRUE);
            }else if(new File(ANDROID_TOUCH2_DT2W).exists()){
                PutStringPreferences("d2w",ANDROID_TOUCH2_DT2W);
                PutBooleanPreferences("d2w_exist",TRUE);
            } else if(new File(TOUCH_PANEL_DT2W).exists()){
                PutStringPreferences("d2w",TOUCH_PANEL_DT2W);
                PutBooleanPreferences("d2w_exist",TRUE);
            }else if(new File(DT2W_WAKEUP_GESTURE).exists()){
                PutStringPreferences("d2w",DT2W_WAKEUP_GESTURE);
                PutBooleanPreferences("d2w_exist",TRUE);
            } else if(new File(DT2W_ENABLE).exists()){
                PutStringPreferences("d2w",DT2W_ENABLE);
                PutBooleanPreferences("d2w_exist",TRUE);
            }else if(new File(DT2W_WAKE_GESTURE).exists()){
                PutStringPreferences("d2w",DT2W_WAKE_GESTURE);
                PutBooleanPreferences("d2w_exist",TRUE);
            }else if(new File(DT2W_WAKE_GESTURE_2).exists()){
                PutStringPreferences("d2w",DT2W_WAKE_GESTURE_2);
                PutBooleanPreferences("d2w_exist",TRUE);
            } else if(new File(DT2W_FT5X06).exists()){
                PutStringPreferences("d2w",DT2W_FT5X06);
                PutBooleanPreferences("d2w_exist",TRUE);
            }else{
                PutBooleanPreferences("d2w_exist",FALSE);
            }

            if(getPreferences_bool("d2w_exist")==true){
                try {
                    if (get_d().contains("1")) {
                        PutBooleanPreferences("d2w_enable", TRUE);
                    } else if (get_d().contains("0")) {
                        PutBooleanPreferences("d2w_enable", FALSE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            text2.setText("Ok!");
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

    //*********************************** Getting & Setting Info **************************************
    public void PutStringPreferences(String Name,String Function){
        SharedPreferences settings = mContext.getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, Function);
        editor.commit();
    }

    public String getStringPreferences(String Name){
        String o;
        SharedPreferences settings = mContext.getSharedPreferences(Name, 0); // 0 - for private mode
        o=settings.getString(Name,null);
        return o;
    }

    public void PutBooleanPreferences(String Name,Boolean Function){
        SharedPreferences settings = mContext.getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Name, Function);
        editor.commit();
    }

    public boolean getPreferences_bool(String Name){
        SharedPreferences settings = mContext.getSharedPreferences(Name, 0); // 0 - for private mode
        return settings.getBoolean(Name, Boolean.parseBoolean(null));
    }

//*************************************************************************************************
}
