package com.proxleep.loren.proxleep;

import android.app.ActivityManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;

/**
 * Created by Loren on 11/28/2014.
 */

public class ProximityService extends Service
        implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;

    private DevicePolicyManager deviceManager;
    private boolean active = false;
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        deviceManager = (DevicePolicyManager)getSystemService(
                Context.DEVICE_POLICY_SERVICE);

        final ComponentName compName = new ComponentName(this, Admin.class);

        final Intent intent = new Intent(DevicePolicyManager
                .ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "To lock the screen");

        active = deviceManager.isAdminActive(compName);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    boolean hover = false;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) { // Hover
            timer = new Timer(this);
            hover = true;
            if (powerManager().isInteractive()){
                timer.start();
            }
        } else // No hover
        {
            if (hover) {
                hover = false;
                if (powerManager().isInteractive())
                {
                    timer.interrupt();
                }
                toggleScreen();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    public void toggleScreen()
    {
        if (powerManager().isInteractive()) // Put it to sleep
        {
            if (active)
                deviceManager.lockNow();
        } else { // Otherwise wake it up
            final PowerManager.WakeLock wl = powerManager().newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "");
            wl.acquire();
        }
    }

    private PowerManager powerManager()
    {
        return (PowerManager) getSystemService(Context.POWER_SERVICE);
    }
}