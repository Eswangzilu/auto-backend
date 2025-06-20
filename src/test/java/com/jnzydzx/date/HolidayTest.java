package com.jnzydzx.date;

import static org.junit.jupiter.api.Assertions.*;

import com.nlf.calendar.Holiday;
import com.nlf.calendar.util.HolidayUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

public class HolidayTest {
    @Test
    public void vacationTest() {

        ArrayList<Holiday> holidays = new ArrayList<>();
        holidays.add(HolidayUtil.getHoliday(2025, 9, 28));
        holidays.add(HolidayUtil.getHoliday(2025, 9, 29));
        holidays.add(HolidayUtil.getHoliday(2025, 10, 1));

        assertEquals(HolidayUtil.getHoliday(2025, 9, 28).isWork(), true);

        for (Holiday holiday : holidays) {
            if (holiday != null) {
                System.out.println(holiday + " -- " + holiday.isWork());
            }
        }
    }

    @Test
    public void localTimeTest() {
        LocalTime now = LocalTime.now();
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.getSecond());
    }
}
