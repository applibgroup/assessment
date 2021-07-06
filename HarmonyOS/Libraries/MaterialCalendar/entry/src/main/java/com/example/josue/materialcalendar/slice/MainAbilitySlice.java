package com.example.josue.materialcalendar.slice;

import com.example.josue.materialcalendar.ResourceTable;
import com.github.clans.fab.FloatingActionButton;
import com.jmavarez.materialcalendar.CalendarView;
import com.jmavarez.materialcalendar.Util.CalendarDay;
import com.jmavarez.materialcalendar.Util.CalendarUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class MainAbilitySlice extends AbilitySlice {

    CalendarView calendarView;
    Text dateString;
    Text monthString;
    Component emptyEvents;
    FloatingActionButton floatingActionButton;

    HashSet<CalendarDay> calendarDays;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        calendarView =(CalendarView) findComponentById(ResourceTable.Id_calendarView);
        monthString = (Text) findComponentById(ResourceTable.Id_month);
        dateString = (Text) findComponentById(ResourceTable.Id_date);
        floatingActionButton = (FloatingActionButton) findComponentById(ResourceTable.Id_fab);

        calendarView.setIndicatorsVisibility(true);
        calendarDays = new HashSet<>();
        CalendarDay calendarDay = CalendarDay.from(new Date());

        // Testing Calendar indicators
        for (int i = 1; i < CalendarUtils.getEndOfMonth(calendarDay.getCalendar()) + 1; i++) {
            if (i % 2 == 0) {
                CalendarDay day = CalendarDay.from(i, calendarDay.getMonth() + 1, calendarDay.getYear());
                calendarDays.add(day);
            }
        }

        calendarView.addEvents(calendarDays);

        calendarView.setOnMonthChangedListener(date -> {
            String month = new SimpleDateFormat("MMMM yyyy").format(date);
            monthString.setText(month);
        });

        calendarView.setOnDateChangedListener(date -> {
            String d = new SimpleDateFormat("dd/MM/yyyy").format(date);
            dateString.setText(d);
        });

        floatingActionButton.setClickedListener(component -> calendarView.reset());
        String date = new SimpleDateFormat("MMMM yyyy").format(calendarView.getDateSelected());
        monthString.setText(date);
        String d = new SimpleDateFormat("dd/MM/yyyy").format(calendarView.getDateSelected());
        dateString.setText(d);

//        emptyEvents.setVisibility(View.GONE);
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
