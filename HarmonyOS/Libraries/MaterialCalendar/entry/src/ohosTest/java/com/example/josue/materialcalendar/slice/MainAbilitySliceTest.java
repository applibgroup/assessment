package com.example.josue.materialcalendar.slice;

import com.jmavarez.materialcalendar.CalendarView;
import com.jmavarez.materialcalendar.DayView;
import com.jmavarez.materialcalendar.Interface.CalendarCallback;
import com.jmavarez.materialcalendar.Interface.OnDateChangedListener;
import com.jmavarez.materialcalendar.Interface.OnMonthChangedListener;
import com.jmavarez.materialcalendar.MonthView;
import com.jmavarez.materialcalendar.Util.CalendarDay;
import com.jmavarez.materialcalendar.Util.CalendarUtils;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.render.Canvas;
import ohos.agp.utils.Color;
import ohos.app.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.manipulation.Ordering;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class MainAbilitySliceTest {
    CalendarView calendarView;
    Context mContext;
    OnDateChangedListener dateChangedListener;
    CalendarCallback calendarCallback;
    CalendarDay day;
    @Before
    public void setUp() throws Exception {
        mContext= AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        calendarView=new CalendarView(mContext);
        day=CalendarDay.today();
        calendarCallback=new CalendarCallback() {
            @Override
            public Date getDateSelected() {
                return null;
            }

            @Override
            public ArrayList<CalendarDay> getEvents() {
                return null;
            }

            @Override
            public boolean getIndicatorsVisible() {
                return false;
            }
        };
        dateChangedListener=new OnDateChangedListener() {
            @Override
            public void onDateChanged(Date date) {

            }
        };
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setCalendarView(){
        CalendarView calendarView1;
        calendarView1=new CalendarView(mContext);
        assertNotNull(calendarView1);
    }

    @Test
    public void testdayEqual(){
        CalendarDay testday = CalendarDay.today();
        testday.equals(day);
    }
    @Test
    public void testdayEqualMonth(){
        CalendarDay testday = CalendarDay.today();
        testday.equalsMonth(day);
    }

    @Test
    public void shouldSetTvDay(){
        DayView dayView=new DayView(mContext);
        Text text=new Text(mContext);
        dayView.setTvDay(text);
        assertEquals(text,dayView.getTvDay());
    }


    @Test
    public void shouldSetIndicator(){
        DayView dayView=new DayView(mContext);
        Component indicator=new Component(mContext);
        dayView.setIndicator(indicator);
        assertEquals(indicator,dayView.getIndicator());
    }
    @Test
    public void shouldselectedTrue(){
            DayView dayView=new DayView(mContext);
            Text text=new Text(mContext);
            dayView.setIndicator(new Component(mContext));
            dayView.setTvDay(text);
            dayView.setSelected(true);
            assertTrue(dayView.isSelected());
    }

    @Test
    public void shouldselectedfalse(){
        DayView dayView=new DayView(mContext);
        dayView.setSelected(false);
        assertFalse(dayView.isSelected());
    }

    @Test
    public void shouldIndiactorVisibilityTrue(){
        DayView dayView=new DayView(mContext);
        dayView.setIndicatorVisible(true);
        assertTrue(dayView.isIndicatorVisible());
    }

    @Test
    public void shouldIndicatorVisibilityFalse(){
        DayView dayView=new DayView(mContext);
        dayView.setIndicatorVisible(false);
        assertFalse(dayView.isIndicatorVisible());
    }

    @Test
    public void shouldStartsOnSaturdayFalse(){
        calendarView.setStartsOnSunday(false);
        assertFalse(calendarView.isStartsOnSunday());
    }
    @Test
    public void shouldStartsOnSaturdayTrue(){
        calendarView.setStartsOnSunday(true);
        assertTrue(calendarView.isStartsOnSunday());
    }

    @Test
    public void shouldGetColored(){
        calendarView.setCalendarColor(Color.BLACK.getValue());
        assertEquals(Color.BLACK.getValue(),calendarView.getCalendarColor());
    }

    @Test
    public void shouldSetOndateChangedListener(){
        OnDateChangedListener listener=new OnDateChangedListener() {
            @Override
            public void onDateChanged(Date date) {

            }
        };
        calendarView.setOnDateChangedListener(listener);
        assertEquals(listener,calendarView.getOnDateChangedListener());
    }


    @Test
    public void shouldSetOnMonthChangedListener(){
        OnMonthChangedListener listener=new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(Date date) {

            }
        };
        calendarView.setOnMonthChangedListener(listener);
        assertEquals(listener,calendarView.getOnMonthChangedListener());

    }

    @Test
    public void shouldSetAdapter(){

        CalendarView calendarView1=new CalendarView(mContext);
        CalendarView.CalendarAdapter adapter=calendarView.getAdapter();
        calendarView1.setAdapter(adapter);
        assertEquals(adapter,calendarView1.getAdapter());

    }

    @Test
    public void shouldSetIndicatorDrawable(){
        DayView dayView=new DayView(mContext);
        ShapeElement shapeElement=new ShapeElement();
        shapeElement.setShape(ShapeElement.OVAL);
        dayView.set_indicatorDrawable(shapeElement);
        assertEquals(shapeElement,dayView.get_indicatorDrawable());
    }

    @Test
    public void shouldSetSelectionDrawable(){
        DayView dayView=new DayView(mContext);
        ShapeElement shapeElement=new ShapeElement();
        shapeElement.setShape(ShapeElement.OVAL);
        dayView.set_selectionDrawable(shapeElement);
        assertEquals(shapeElement,dayView.get_selectionDrawable());
    }

    @Test
    public void shouldSetIndicatorMargin(){
        DayView dayView=new DayView(mContext);
        dayView.set_indicatorMarginBottom(20);
        assertEquals((long) 20,(long) dayView.get_indicatorMarginBottom());

    }


    @Test
    public void shouldSetIndicatorSize(){
        DayView dayView=new DayView(mContext);
        dayView.set_indicatorSize(20);
        assertEquals((long) 20,(long) dayView.get_indicatorSize());
    }

    @Test
    public void shouldSetMeasuredHeight(){
        DayView dayView=new DayView(mContext);
        dayView.set_measuredHeight(20);
        assertEquals((long) 20,(long) dayView.get_measuredHeight());
    }

    @Test
    public void shouldSetOffSet(){
        MonthView monthView=new MonthView(mContext,day,false,calendarCallback,dateChangedListener,30);
        monthView.setOffset(20);
        assertEquals((long) 20,(long) monthView.getOffset());
    }

    @Test
    public void shouldSetPagePosition(){
        MonthView monthView=new MonthView(mContext,day,false,calendarCallback,dateChangedListener,30);
        monthView.setPagerPosition(20);
        assertEquals((long) 20,(long) monthView.getPagerPosition());
    }















}