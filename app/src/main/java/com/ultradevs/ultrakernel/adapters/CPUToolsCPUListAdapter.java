package com.ultradevs.ultrakernel.adapters;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.ultradevs.ultrakernel.R;
import com.ultradevs.ultrakernel.utils.ShellUtils;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

import static com.ultradevs.ultrakernel.utils.cpu_utils.CpuInfoUtils.PATH_CPUS;

/**
 * Created by: veli
 * Date: 10/21/16 12:51 AM
 */

public class CPUToolsCPUListAdapter extends AbstractCPUListAdapter
{
    private ShellUtils mShell;
    public Context mContext;
    private LayoutInflater mInflater;

    public CPUToolsCPUListAdapter(ShellUtils shell, Context context)
    {
        super();

        this.mShell = shell;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    private CompoundButton.OnCheckedChangeListener createSwitchListener(final int cpuId)
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mShell.getSession().addCommand("echo " + ((isChecked) ? 1 : 0) + " > " + PATH_CPUS + "/cpu" + cpuId + "/online\n");
            }
        };
    }

    @Override
    public Object getItem(int position)
    {
        return getList().get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        if (view == null)
            view = mInflater.inflate(R.layout.list_cpu, parent, false);

        final SwitchCompat switchView = (SwitchCompat)view.findViewById(R.id.list_cpu_switch);

        switchView.setText("Core #" + position);

        updateCpuState(switchView, position);
        switchView.setOnCheckedChangeListener(createSwitchListener(position));

        return view;
    }

    private void updateCpuState(final SwitchCompat switchView, final int cpuId)
    {
        mShell.getSession().addCommand("cat " + PATH_CPUS + "/cpu" + cpuId + "/online\n", cpuId, new Shell.OnCommandResultListener()
        {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output)
            {
                if (commandCode == cpuId)
                {
                    if (output.size() > 0)
                    {
                        // catch if bash send wrong type of string
                        try
                        {
                            boolean currentState = 1 == Integer.valueOf(output.get(0));
                            switchView.setChecked(currentState);
                        } catch (Exception e)
                        {
                        }
                    }
                }
            }
        });
    }
}
