package receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ultradevs.ultrakernel.services.OnBootApply;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            context.startService(new Intent(context, OnBootApply.class));
        }
    }
}