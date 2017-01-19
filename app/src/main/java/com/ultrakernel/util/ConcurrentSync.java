package com.ultrakernel.util;

/**
 * Created by: veli
 * Date: 10/21/16 12:17 AM
 */

abstract public class ConcurrentSync extends Thread
{
    protected abstract void onRun();
    protected abstract boolean onCondition();

    @Override
    public void run()
    {
        super.run();

        while (isInterrupted() || onCondition())
        {
            this.onRun();
        }
    }
}
