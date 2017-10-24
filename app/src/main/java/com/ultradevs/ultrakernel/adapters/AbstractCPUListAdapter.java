package com.ultradevs.ultrakernel.adapters;

import android.widget.BaseAdapter;

import com.ultradevs.ultrakernel.utils.cpu_utils.CPUCoreInfo;

import java.io.File;
import java.util.ArrayList;

import static com.ultradevs.ultrakernel.utils.cpu_utils.CpuInfoUtils.PATH_CPUS;

/**
 * Created by: veli
 * Date: 10/21/16 12:40 AM
 */

public abstract class AbstractCPUListAdapter extends BaseAdapter
{
    private ArrayList<CPUCoreInfo> mCoreList = new ArrayList<>();

    public AbstractCPUListAdapter()
    {
        File cpuHome = new File(PATH_CPUS);

        for (String item : cpuHome.list())
        {
            // Until a device that has more than 9 CPU cores, this code will work :D
            if (!item.startsWith("cpu") || item.length() != 4)
                continue;

            CPUCoreInfo info = new CPUCoreInfo();

            info.coreId = getList().size();

            getList().add(info);
        }
    }

    @Override
    public int getCount()
    {
        return getList().size();
    }

    public ArrayList<CPUCoreInfo> getList()
    {
        return this.mCoreList;
    }
}
