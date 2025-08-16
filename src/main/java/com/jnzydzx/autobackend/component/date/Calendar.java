package com.jnzydzx.autobackend.component.date;

import com.nlf.calendar.Holiday;
import com.nlf.calendar.util.HolidayUtil;

import java.time.LocalDate;


public class Calendar {
    /**
     * Verify whether it is a workday or not
     * @param date Current date
     * @return True: work-day | False: holiday
     */
    public static boolean verifyDate(LocalDate date) {
        Holiday holiday = HolidayUtil.getHoliday(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        if (holiday != null) {
            return holiday.isWork();
        }
        return true;
    }
}
