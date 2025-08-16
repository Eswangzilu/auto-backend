package com.jnzydzx.autobackend.component.date;

import com.jnzydzx.autobackend.component.mark.Spyder;

import java.time.LocalTime;
import java.util.Random;

public class ClockSleep implements Runnable {
    private final Spyder spyder;

    public ClockSleep(Spyder spyder) {
        this.spyder = spyder;
    }

    @Override
    public void run() {
        // 8:05 - 8:15
        int blockTime = (new Random(System.currentTimeMillis()).nextInt(6) + 5) * 60 * 1000;
        try {
            Thread.sleep(blockTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("ClockSleep run: " + LocalTime.now());
            spyder.makeClock();
        }
    }
}
