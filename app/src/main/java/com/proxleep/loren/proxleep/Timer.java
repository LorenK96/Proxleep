package com.proxleep.loren.proxleep;

import java.security.Provider;

/**
 * Created by loren on 05/12/14.
 */
public class Timer extends Thread{

    private int time = 0;
    private ProximityService t;

    public Timer(ProximityService inp)
    {
        t = inp;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            if (time == 2) {
                time = 0;
                t.toggleScreen();
                break;
            } else if (time == -1)
            {
                break;
            } else {
                this.time++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void interrupt()
    {
        time = -1;
        Thread.currentThread().interrupt();
    }
}
