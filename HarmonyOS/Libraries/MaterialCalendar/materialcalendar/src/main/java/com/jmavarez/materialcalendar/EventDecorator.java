package com.jmavarez.materialcalendar;
import com.jmavarez.materialcalendar.Interface.DayViewDecorator;
import com.jmavarez.materialcalendar.Util.CalendarDay;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.text.SimpleDateFormat;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
    private final int color;
    private final HashSet<CalendarDay> calendarDays;
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG");

    public EventDecorator(int color, HashSet<CalendarDay> calendarDays) {
        this.color = color;
        this.calendarDays = new HashSet<>(calendarDays);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        HiLog.debug(label,"Day " + new SimpleDateFormat("d/MM/yyyy").format(day.getDate())+ " " + calendarDays.contains(day));
        return calendarDays.contains(day);
    }

    @Override
    public void decorate(DayView view) {

    }
}
