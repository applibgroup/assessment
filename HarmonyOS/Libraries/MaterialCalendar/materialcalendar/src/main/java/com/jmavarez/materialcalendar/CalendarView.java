package com.jmavarez.materialcalendar;

import com.jmavarez.materialcalendar.Interface.CalendarCallback;
import com.jmavarez.materialcalendar.Interface.DayViewDecorator;
import com.jmavarez.materialcalendar.Interface.OnDateChangedListener;
import com.jmavarez.materialcalendar.Interface.OnMonthChangedListener;
import com.jmavarez.materialcalendar.Util.CalendarDay;
import com.jmavarez.materialcalendar.Util.LogUtil;
import com.jmavarez.materialcalendar.Util.WrapContentViewPager;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.PlainArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class CalendarView extends WrapContentViewPager implements OnDateChangedListener,OnMonthChangedListener, CalendarCallback, PageSlider.PageChangedListener {
    private static final int PAGER_ITEMS = 60;
    private CalendarAdapter adapter;
    private OnDateChangedListener onDateChangedListener;
    private boolean indicatorsVisible;
    private OnMonthChangedListener onMonthChangedListener;
    public Date selection;
    private ArrayList<CalendarDay> eventDates;

    public CalendarAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CalendarAdapter adapter) {
        this.adapter = adapter;
    }

    public OnDateChangedListener getOnDateChangedListener() {
        return onDateChangedListener;
    }

    public OnMonthChangedListener getOnMonthChangedListener() {
        return onMonthChangedListener;
    }

    public int getCalendarColor() {
        return calendarColor;
    }

    public void setCalendarColor(int calendarColor) {
        this.calendarColor = calendarColor;
    }

    public boolean isStartsOnSunday() {
        return startsOnSunday;
    }

    public void setStartsOnSunday(boolean startsOnSunday) {
        this.startsOnSunday = startsOnSunday;
    }

    private int calendarColor;
    private boolean startsOnSunday;
    private static final String CalendarView_mc_color = "CalendarView_mc_color";
    private static final String CalendarView_mc_startsOnSunday = "CalendarView_mc_startsOnSunday";
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0xD000F00, "CALENDAR_VIEW");

    public CalendarView(Context context) {
        super(context);
        this.startsOnSunday = false;
        init();
    }

    public CalendarView(Context context, AttrSet attrs)  {
        super(context, attrs);
        this.startsOnSunday = false;
        calendarColor=attrs.getAttr(CalendarView_mc_color).isPresent() ?
                attrs.getAttr(CalendarView_mc_color).get().getColorValue().getValue() : 0x3F51B5;
        startsOnSunday= attrs.getAttr(CalendarView_mc_startsOnSunday).isPresent() &&
                    attrs.getAttr(CalendarView_mc_startsOnSunday).get().getBoolValue() ;
        init();
    }

    private void init() {

        eventDates = new ArrayList<>();
        this.selection = new Date();
        this.indicatorsVisible = true;
        this.adapter = new CalendarAdapter(this, this);
        setProvider(this.adapter);
        setCurrentPage((30),false);//
        addPageChangedListener(this);
        if(this.getBackgroundElement()==null)
        {
            ShapeElement element=new ShapeElement();
            element.setRgbColor(new RgbColor(63,81,181));
            setBackground(element);
        }

    }

    public void reset() {
        setCurrentPage(30, true);
        onDateChanged(new Date());
        CalendarView.this.onMonthChangedListener.onMonthChanged(CalendarDay.today().getDate());
    }

    public void setDate(Date date) {
        onDateChanged(date);
    }

    @Override
    public Date getDateSelected() {
        return this.selection;
    }

    @Override
    public ArrayList<CalendarDay> getEvents() {
        return this.eventDates;
    }

    @Override
    public boolean getIndicatorsVisible() {
        return this.indicatorsVisible;
    }

    @Override
    public void onDateChanged(Date date) {
        this.selection = date;
        if (this.onDateChangedListener != null) {
            this.onDateChangedListener.onDateChanged(date);
        }
        refreshSelection();
    }

    private void refreshSelection() {
        Calendar c = Calendar.getInstance();
        c.setTime(getDateSelected());

        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        for (int i = 0; i < this.adapter.mComponents.size(); i++) {
            MonthView v=null;
            if(i>=this.adapter.mComponents.size()){
                LogUtil.debug("Calendar_view","The size of mComponents : "+this.adapter.mComponents.size()+" \n"+"Value of i : "+i);
                }
            else
            v = this.adapter.getmComponents().valueAt(i);
            if (v != null) {
                v.refreshSelection(day, month, year);
            }
        }
    }

    public void setIndicatorsVisibility(boolean visible) {
        if (this.indicatorsVisible != visible) {
            this.indicatorsVisible = visible;
            if (this.indicatorsVisible) {
                refreshEvents();
                return;
            }

            for (int i = 0; i < this.adapter.mComponents.size(); i++) {
                MonthView v=null;
                if(i>=this.adapter.mComponents.size()){
                    LogUtil.debug("Calendar_view","The size of mComponents : "+this.adapter.mComponents.size()+" \n"+"Value of i : "+i);
                }
                else
                v = this.adapter.getmComponents().valueAt(i);
                if (v != null) {
                    v.hideIndicators();
                }
            }
        }
    }

    private void refreshEvents() {
               for (int i = 0; i < this.adapter.mComponents.size(); i++) {
            MonthView view=null;
            if(i>=this.adapter.mComponents.size()){
                LogUtil.debug("Calendar_view","The size of mComponents : "+this.adapter.mComponents.size()+" \n"+"Value of i : "+i);
            }
            else
            view = this.adapter.getmComponents().valueAt(i);
            if (view != null) {
                view.refreshEvents();
            }
        }
    }

    public void addEvent(CalendarDay date) {
        if (date == null) {
            return;
        }
        this.eventDates.add(date);
        refreshEvents();
    }

    public void addEvents(Collection dates) {
        if (dates == null) {
            return;
        }
        this.eventDates.addAll(dates);
        refreshEvents();
    }

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.onDateChangedListener = listener;
    }

    public void setOnMonthChangedListener(OnMonthChangedListener listener) {
        this.onMonthChangedListener = listener;
    }


    // Needs work
    public void scrollToMonth(CalendarDay day) {
        for (int i = 0; i < this.adapter.getmComponents().size(); i++) {
            MonthView monthView = this.adapter.getmComponents().valueAt(i);
            if (monthView != null) {
                if (day.equalsMonth(monthView.getCalendarDay())) {
                    int pos = 30 + (day.getMonth() - CalendarDay.from(this.selection).getMonth());
                    setCurrentPage(pos,true);
                    if(CalendarView.this.onMonthChangedListener!=null)
                    {
                        CalendarView.this.onMonthChangedListener.onMonthChanged(day.getDate());
                    }
                    onDateChanged(day.getDate());
                    return;
                }
            }
        }
    }

    @Override
    public void onPageSliding(int i, float v, int i1) {

    }

    @Override
    public void onPageSlideStateChanged(int i) {
        if (i == 0) {
            CalendarView.this.adapter.onScroll();
        }

    }

    @Override
    public void onPageChosen(int i) {

    }

    @Override
    public void onMonthChanged(Date date) {

    }

    public class CalendarAdapter extends PageSliderProvider {

        public Date mDateStart;

        public PlainArray<MonthView> mComponents;
        public ArrayList<DayViewDecorator> dayViewDecorators;
        private CalendarCallback mCallback;
        private OnDateChangedListener mListener;
        private int mPositionStart;
        public final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0xD000F00, "on_scroll");

        public CalendarAdapter(CalendarCallback callback,OnDateChangedListener listener) {
            this.mListener = listener;
            this.mCallback = callback;
            this.mDateStart = new Date();
            this.mPositionStart = 30 ;
            this.mComponents = new PlainArray<>();
            this.dayViewDecorators = new ArrayList<>();
        }


        @Override
        public int getCount() {
            return CalendarView.PAGER_ITEMS;
        }

        @Override
        public Object createPageInContainer(ComponentContainer componentContainer, int position) {
            Calendar c=Calendar.getInstance();
            c.setTime(this.mDateStart);
            c.add(Calendar.MONTH, position - this.mPositionStart+1);
            c.set(Calendar.DAY_OF_MONTH, 1);
            MonthView v = new MonthView(CalendarView.this.getContext(), CalendarDay.from(c), CalendarView.this.startsOnSunday, this.mCallback, this.mListener, getCurrentPage());
            c.setTime(CalendarView.this.getDateSelected());
            v.refreshSelection(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            this.mComponents.put(position, v);
            componentContainer.addComponent(v);
            return v;
        }

        @Override
        public void destroyPageFromContainer(ComponentContainer componentContainer, int position, Object object) {

            this.mComponents.remove(position);
            componentContainer.removeComponent((Component) object);
        }

        @Override
        public boolean isPageMatchToObject(Component component, Object object) {
            return component == object;
        }

        public PlainArray<MonthView> getmComponents() {
            return this.mComponents;
        }
        public void onScroll() {
            Calendar c = Calendar.getInstance();
            c.setTime(this.mDateStart);
            c.add(Calendar.MONTH, CalendarView.this.getCurrentPage() - this.mPositionStart);
            HiLog.debug(label, "" + CalendarView.this.getCurrentPage() + " " + this.mPositionStart);
            if (CalendarView.this.onMonthChangedListener != null) {
                CalendarView.this.onMonthChangedListener.onMonthChanged(c.getTime());
            }

            HiLog.debug(label, CalendarDay.from(c).toString() + " - " + (CalendarView.this.getCurrentPage() - this.mPositionStart) + " Month: " + (c.get(Calendar.MONTH) + 1));
        }
    }
}