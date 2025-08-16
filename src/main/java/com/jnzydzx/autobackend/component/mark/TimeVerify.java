package com.jnzydzx.autobackend.component.mark;

import com.jnzydzx.autobackend.component.date.Calendar;
import com.jnzydzx.autobackend.component.date.ClockSleep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TimeVerify {
    private boolean isWorkday = true;
    private final Spyder spyder;

    @Autowired
    public TimeVerify(Spyder spyder) {
        this.spyder = spyder;
    }

    /**
     * this function runs at the time below
     * 00:10, 00:40,
     * 01:10, 01:40,
     * 02:10, 02:40,
     * 03:10, 03:40,
     * 04:10, 04:40,
     * 05:10, 05:40,
     * 06:10, 06:40,
     * 07:10, 07:40
     */
    @Scheduled(cron = "0 10/30 0-7 * * ?", zone = "Asia/Shanghai")
    public void checkWorkdayOrHoliday() {
        System.out.println("TimeVerify - checkWorkdayOrHoliday: " + LocalTime.now());
        isWorkday = Calendar.verifyDate(LocalDate.now());
    }

    @Scheduled(cron = "0 5 8 * * ?", zone = "Asia/Shanghai")
    public void clockExecute() {
        System.out.println("TimeVerify - clockExecute: " + LocalTime.now());
        System.out.println("Is today a holiday? [" + !isWorkday + "].");
        if (isWorkday) {
            ClockSleep clock = new ClockSleep(spyder);
            new Thread(clock).start();
        }
    }
}
