package com.jnzydzx.service;


import com.nlf.calendar.Holiday;
import com.nlf.calendar.util.HolidayUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Calendar {
    /**
     * This function is used to judge whether a date is a work-day or not
     * True:    work-day
     * False:   holiday
     *
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public boolean verifyDate(int year, int month, int day) {
        Holiday holiday = HolidayUtil.getHoliday(year, month, day);
        if (holiday != null) {
            return holiday.isWork();
        }
        return true;
    }
}
