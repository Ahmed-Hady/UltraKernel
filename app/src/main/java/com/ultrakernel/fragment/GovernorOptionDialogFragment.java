package com.ultrakernel.fragment;

/**
 * Created by sumit on 29/10/16.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ultrakernel.activity.Activity;
import com.ultrakernel.adapter.GovernorAdapter;
import com.ultrakernel.util.ShellUtils;

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

        final ShellUtils shell = ((Activity)getActivity()).getShellSession();

        dialogBuilder.setTitle("Choose Governor");
        //dialogBuilder.setMessage("What would like to do?");

        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                shell.getSession().addCommand(((GovernorAdapter.GovernorItem) adapter.getItem(which)).command);
            }
        });

        return dialogBuilder.create();
    }
}
