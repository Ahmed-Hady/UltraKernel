package com.ultradevs.ultrakernel.utils;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.ultradevs.ultrakernel.R;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;
/**
 * Created by: veli
 * Date: 10/20/16 3:31 PM
 */

public class ShellUtils
{
    // all activities should use the same session
    private static Shell.Interactive rootSession;

    public ShellUtils(final Context context)
    {
        if (rootSession == null)
        {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setTitle(context.getString(R.string.priv_title));
            dialog.setMessage(context.getString(R.string.priv_msg));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            // start the shell in the background and keep it alive as long as the app is running
            // Callback to report whether the shell was successfully started up
            rootSession = new Shell.Builder().
                    useSU().
                    setWantSTDERR(true).
                    setWatchdogTimeout(5).
                    setMinimalLogging(true).
                    open((commandCode, exitCode, output) -> {
                        // note: this will FC if you rotate the phone while the dialog is up
                        dialog.dismiss();

                        if (exitCode != Shell.OnCommandResultListener.SHELL_RUNNING)
                            reportError("Error opening root shell: exitCode " + exitCode, context);
                    });
        }
    }

    public void closeSession()
    {
        if (rootSession != null)
            rootSession.close();

        rootSession = null;
    }

    public Shell.Interactive getSession()
    {
        return ShellUtils.rootSession;
    }

    protected void reportError(String string, Context context)
    {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        rootSession = null;
    }
}
