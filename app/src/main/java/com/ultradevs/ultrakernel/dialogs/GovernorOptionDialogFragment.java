package com.ultradevs.ultrakernel.dialogs;

/**
 * Created by sumit on 29/10/16.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.adapters.GovernorAdapter;
import com.ultradevs.ultrakernel.utils.ShellExecuter;

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
                ShellExecuter.shell(((GovernorAdapter.GovernorItem) adapter.getItem(which)).command,true);
            }
        });

        return dialogBuilder.create();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        TextView curr = (TextView) getActivity().findViewById(R.id.cpugov);
        curr.setText(kernel_Current_Gov());
    }
}
