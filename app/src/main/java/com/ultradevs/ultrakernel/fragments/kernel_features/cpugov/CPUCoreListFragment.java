package com.ultradevs.ultrakernel.fragments.kernel_features.cpugov;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.ultradevs.ultrakernel.activities.Activity;
import com.ultradevs.ultrakernel.adapters.CPUToolsCPUListAdapter;

import static com.ultradevs.ultrakernel.utils.SocInfoUtils.Ncores;

/**
 * Created by: veli
 * Date: 10/21/16 12:08 PM
 */

public class CPUCoreListFragment extends ListFragment
{
    private CPUToolsCPUListAdapter mAdapter;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new CPUToolsCPUListAdapter(((Activity)getActivity()).getShellSession(), getActivity());

        setListAdapter(mAdapter);
    }

    public void update()
    {
        this.mAdapter.notifyDataSetChanged();
    }
}
