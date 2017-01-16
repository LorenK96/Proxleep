package com.proxleep.loren.proxleep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by loren on 05/12/14.
 */
public class Startup extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            final Intent serviceIntent = new Intent(context, ProximityService.class);
            context.startService(serviceIntent);
        }
    }
}
