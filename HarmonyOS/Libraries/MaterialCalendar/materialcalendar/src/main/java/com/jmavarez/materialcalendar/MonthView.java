package com.jmavarez.materialcalendar;

import com.jmavarez.materialcalendar.Interface.CalendarCallback;
import com.jmavarez.materialcalendar.Interface.OnDateChangedListener;
import com.jmavarez.materialcalendar.Util.CalendarDay;
import com.jmavarez.materialcalendar.Util.CalendarUtils;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.StackLayout;
import ohos.agp.components.Text;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import static com.jmavarez.materialcalendar.Util.CanvasHelper.dpToPx;

public class MonthView extends ComponentContainer implements Component.EstimateSizeListener,ComponentContainer.ArrangeListener {
    private static final int DEFAULT_DAYS_IN_WEEK = 7;

    final ClickedListener dayClickListener ;
    final Display display;
    private final boolean starsOnSunday;
    private CalendarCallback callback;
    private ArrayList<DayView> dayViews;
    private OnDateChangedListener mListener;
    private CalendarDay calendarDay;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPagerPosition() {
        return pagerPosition;
    }

    public void setPagerPosition(int pagerPosition) {
        this.pagerPosition = pagerPosition;
    }

    private int offset;
    private int pagerPosition;

    public MonthView(Context context, CalendarDay calendarDay, boolean startsOnSunday, @NotNull CalendarCallback callback, OnDateChangedListener listener, int pagerPosition) {
        super(context);
        setArrangeListener(this::onArrange);
        setEstimateSizeListener(this::onEstimateSize);
        this.calendarDay = calendarDay;
        this.display=(DisplayManager.getInstance().getDefaultDisplay(context)).get();
        this.dayClickListener = new ClickListeners();
        this.mListener = listener;
        this.callback = callback;
        this.starsOnSunday = startsOnSunday;
        this.pagerPosition = pagerPosition;
        init();
    }

    public CalendarDay getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }

    private void init() {
        this.dayViews = new ArrayList();

        this.offset = CalendarUtils.getDayOfWeek(this.calendarDay.getCalendar(), this.starsOnSunday) - 1;
        setLayoutConfig(new LayoutConfig(LayoutConfig.MATCH_PARENT,LayoutConfig.MATCH_PARENT));
        addHeaders();
        int lastDay = CalendarUtils.getEndOfMonth(this.calendarDay.getCalendar());
        for (int i = 1; i <= lastDay; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(this.calendarDay.getYear(), this.calendarDay.getMonth(), i);
            CalendarDay cur = CalendarDay.from(calendar);
            addDayComponent(cur);
        }
        refreshEvents();
    }

    public void refreshEvents() {
        if (this.callback != null && this.callback.getIndicatorsVisible() && this.callback.getEvents() != null && this.callback.getEvents().size() > 0) {
            Iterator it = this.dayViews.iterator();
            int year = this.calendarDay.getYear();
            int month = this.calendarDay.getMonth() + 1;

            while (it.hasNext()) {
                DayView v = (DayView) it.next();
                int day = v.getDay().getDay();
                boolean shouldDecorate = false;
                CalendarDay today = CalendarDay.from(day, month, year);

                for (int i = 0; i < this.callback.getEvents().size(); i++) {
                    CalendarDay event = this.callback.getEvents().get(i);
                    if (event.equals(today)) {
                        shouldDecorate = true;
                    }
                }

                v.setIndicatorVisibility(shouldDecorate);
            }
        }
    }


 void addHeaders() {
        int i = 1;
        while (i <= DEFAULT_DAYS_IN_WEEK) {
            int actual = this.starsOnSunday ? i == 1 ? DEFAULT_DAYS_IN_WEEK : i - 1 : i;
            Text text = new Text(getContext());

            try {
                text.setText(new CalendarUtils.Day(Integer.valueOf(actual)).getShortName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Font font_bold=Font.DEFAULT_BOLD;
            StackLayout.LayoutConfig config=new StackLayout.LayoutConfig();
            config.alignment= LayoutAlignment.CENTER;
            text.setTextAlignment(TextAlignment.CENTER);
            text.setLayoutConfig(config);
            text.setFont(font_bold);
            text.setTextColor(Color.WHITE);
            text.setTextSize( dpToPx(getContext(),12));
            addComponent(text);
            i++;
        }
    }

    private void addDayComponent(CalendarDay day) {
        DayView dayView = new DayView(getContext(), day);
        dayView.setClickedListener(this.dayClickListener);
        this.dayViews.add(dayView);
        addComponent(dayView);
    }

    public void refreshSelection(int day, int month, int year) {
        boolean select;
        if (this.calendarDay.getMonth() == month && this.calendarDay.getYear() == year) {
            select = true;
        } else {
            select = false;
        }

        Iterator it = this.dayViews.iterator();

        while (it.hasNext()) {
            boolean z;

            DayView v = (DayView) it.next();

            if (select && v.getDay().getDay() == day        ) {
                z = true;
            } else {
                z = false;
            }

            v.setSelected(z);
        }
    }


    public void hideIndicators() {
        Iterator it = this.dayViews.iterator();

        while (it.hasNext()) {
            ((DayView) it.next()).setIndicatorVisibility(false);
        }
    }

    @Override
    public boolean onEstimateSize(int widthEstimatedSize, int heightEstimatedSize) {
        int specWidthSize = EstimateSpec.getSize(widthEstimatedSize);
        if(EstimateSpec.getMode(widthEstimatedSize)==EstimateSpec.UNCONSTRAINT){
            throw new IllegalStateException("CalendarPagerView should never be left to decide it's size");
        }


        int measureTileWidth = (specWidthSize - (dpToPx(getContext(),8)*2)) / DEFAULT_DAYS_IN_WEEK;
        int measureTileHeight = dpToPx(getContext(),30);
        setEstimatedSize(specWidthSize,(measureTileHeight*DEFAULT_DAYS_IN_WEEK)+dpToPx(getContext(),20))    ;
        int count = getChildCount();

        for (int i=0;i<count;i++)
        {
            getComponentAt(i).estimateSize(EstimateSpec.getSizeWithMode(measureTileWidth,EstimateSpec.PRECISE),
                    EstimateSpec.getSizeWithMode(measureTileHeight,EstimateSpec.PRECISE));

        }
        return true;
    }

    @Override
    public boolean onArrange(int left, int top, int right, int bottom) {
        int marginTop=dpToPx(getContext(),8);
        int marginLeft=dpToPx(getContext(),8);
        int count = getChildCount();
        int offset=this.offset;
        int headOffset=0;
        int childTop=marginTop;
        boolean Arranged=false;
        for(int i=0;i<count;i++ )
        {
            Component child=this.getComponentAt(i);
            int width=child.getEstimatedWidth();
            int height=child.getEstimatedHeight();
            int childleft;
            if(child instanceof Text){
                childleft=(width*headOffset)+marginLeft;
                child.arrange(childleft,childTop,width,height);
                headOffset++;
                Arranged=true;
            }
            else if(child instanceof DayView){
                childleft=(width*offset)+marginLeft;
                child.arrange(childleft,height+childTop,width,(height));
                offset++;
                if(offset >= DEFAULT_DAYS_IN_WEEK){
                    offset=0;
                    childTop += height;
                }
                Arranged=true;
            }
        }
        return Arranged;
    }

    class ClickListeners implements ClickedListener {

        public ClickListeners() {

        }

        @Override
        public void onClick(Component component) {
            if (component instanceof DayView) {
                CalendarDay day = ((DayView) component).getDay();
                if (day != null) {
                    component.setSelected(true);
                    if (MonthView.this.mListener != null) {
                        MonthView.this.mListener.onDateChanged(day.getDate());
                    }
                }
            }
        }
    }
}

