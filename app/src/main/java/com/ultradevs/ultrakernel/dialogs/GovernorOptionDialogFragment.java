package com.ultradevs.ultrakernel.dialogs;

/**
 * Created by sumit on 29/10/16.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.GovernorAdapter;
import com.ultradevs.ultrakernel.utils.RootUtils;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

import static com.ultradevs.ultrakernel.activities.InitActivity.LOG_TAG;
import static com.ultradevs.ultrakernel.fragments.deviceInfo.KernelInfoFragment.kernel_Current_Gov;

/**
 * Created by: veli
 * Date: 10/23/16 4:17 PM
 */

public class GovernorOptionDialogFragment extends DialogFragment
{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final GovernorAdapter adapter = new GovernorAdapter(getActivity());

        dialogBuilder.setTitle("Choose Governor");

        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.i(LOG_TAG, "Current Governor: " + kernel_Current_Gov());
                RootUtils.runCommand(((GovernorAdapter.GovernorItem) adapter.getItem(which)).command);
            }
        });

        return dialogBuilder.create();
    }

    public void PutStringPreferences(String Name,String Function){
        SharedPreferences settings = getContext().getSharedPreferences(Name, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, Function);
        editor.commit();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        TextView curr = (TextView) getActivity().findViewById(R.id.cpugov);
        Log.i(LOG_TAG, "Set Governor: " + kernel_Current_Gov());
        curr.setText(kernel_Current_Gov());
        PutStringPreferences("cur_gov", kernel_Current_Gov());
    }
}
