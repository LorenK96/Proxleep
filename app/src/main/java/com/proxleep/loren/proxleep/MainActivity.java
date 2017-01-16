package com.proxleep.loren.proxleep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startNewService();
    }

    // Start the service
    public void startNewService() {
        startService(new Intent(this, ProximityService.class));
        finish();
    }
}