package com.jmavarez.materialcalendar;

import com.jmavarez.materialcalendar.Util.CalendarDay;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.StackLayout;
import ohos.agp.components.Text;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;

import static com.jmavarez.materialcalendar.Util.CanvasHelper.dpToPx;
import static com.jmavarez.materialcalendar.Util.CanvasHelper.pxToDp;

public class DayView extends StackLayout {
    public static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_INDICATOR_MARGIN_BOTTOM = 2;
    private static final int DEFAULT_INDICATOR_SIZE = 4;
    private static final int DEFAULT_TEXT_SIZE = 12;

    private static Element _indicatorDrawable;
    private static Element _selectionDrawable;

    private static Integer _indicatorMarginBottom;
    private static Integer _indicatorSize;
    private static Integer _measuredHeight;
    private static Integer _colorPrimary;

    private Context context;
    private Component indicator;
    private CalendarDay day;
    private boolean indicatorVisible;
    private boolean selected;

    public Component getIndicator() {
        return indicator;
    }

    public void setIndicator(Component indicator) {
        this.indicator = indicator;
    }

    private DisplayManager metrics;
    private Text tvDay;

    public Text getTvDay() {
        return tvDay;
    }

    public void setTvDay(Text tvDay) {
        this.tvDay = tvDay;
    }

    public static Element get_indicatorDrawable() {
        return _indicatorDrawable;
    }

    public static void set_indicatorDrawable(Element _indicatorDrawable) {
        DayView._indicatorDrawable = _indicatorDrawable;
    }

    public static Element get_selectionDrawable() {
        return _selectionDrawable;
    }

    public static void set_selectionDrawable(Element _selectionDrawable) {
        DayView._selectionDrawable = _selectionDrawable;
    }

    public static Integer get_indicatorMarginBottom() {
        return _indicatorMarginBottom;
    }

    public static void set_indicatorMarginBottom(Integer _indicatorMarginBottom) {
        DayView._indicatorMarginBottom = _indicatorMarginBottom;
    }

    public static Integer get_indicatorSize() {
        return _indicatorSize;
    }

    public static void set_indicatorSize(Integer _indicatorSize) {
        DayView._indicatorSize = _indicatorSize;
    }

    public static Integer get_measuredHeight() {
        return _measuredHeight;
    }

    public static void set_measuredHeight(Integer _measuredHeight) {
        DayView._measuredHeight = _measuredHeight;
    }

    public void setDay(CalendarDay day) {
        this.day = day;
    }

    public boolean isIndicatorVisible() {
        return indicatorVisible;
    }

    public void setIndicatorVisible(boolean indicatorVisible) {
        this.indicatorVisible = indicatorVisible;
    }

    public DisplayManager getMetrics() {
        return metrics;
    }

    public void setMetrics(DisplayManager metrics) {
        this.metrics = metrics;
    }

    static {
        _colorPrimary = null;
        _measuredHeight = null;
        _indicatorSize = null;
        _indicatorMarginBottom = null;
        _selectionDrawable = null;
        _indicatorDrawable = null;
    }



    public DayView(Context context) {
        super(context);
    }
    public DayView(Context context, CalendarDay day) {
        super(context);

        this.day = day;
        this.context=context;
        init();
    }

    public DayView(Context context, AttrSet attrs) {
        super(context, attrs);
    }

    public DayView(Context context, AttrSet attrs, int defStyleAttr) {
          super(context, attrs, String.valueOf(defStyleAttr));
    }

    private static Element generateCircleCanvas(Color color) {

        ShapeElement element=new ShapeElement();
        element.setShape(ShapeElement.OVAL);
        element.setRgbColor(new RgbColor(color.getValue()));
        return element;
    }

    private void init() {

        this.selected = false;
        this.indicatorVisible = false;
        this.tvDay = new Text(getContext());
        this.tvDay.setText(String.format("%d", this.day.getDay()));
        this.tvDay.setTextSize(dpToPx(getContext(),DEFAULT_TEXT_SIZE));
        this.tvDay.setTextColor(Color.WHITE);
        this.tvDay.setTextAlignment(TextAlignment.CENTER);
        StackLayout.LayoutConfig layoutConfig;
        layoutConfig = new StackLayout.LayoutConfig(getDefaultMeasuredHeight(),getDefaultMeasuredHeight());
        layoutConfig.alignment=LayoutAlignment.HORIZONTAL_CENTER;
        this.tvDay.setLayoutConfig(layoutConfig);

        this.indicator = new Component(getContext());
        StackLayout.LayoutConfig indicator_config = new StackLayout.LayoutConfig(getDefaultIndicatorSize(),getDefaultIndicatorSize());
        indicator_config.alignment=LayoutAlignment.BOTTOM + LayoutAlignment.HORIZONTAL_CENTER;
        indicator_config.setMargins(0, 0, 0, getDefaultIndicatorMarginBottom());
        this.indicator.setLayoutConfig(indicator_config);

        this.addComponent(this.tvDay);
        this.addComponent(this.indicator);

    }

    public CalendarDay getDay() {
        return this.day;
    }

    public String getText() {
        return this.tvDay.getText();
    }

    public boolean isSelected() {
        return this.selected;
    }

    @SuppressWarnings("deprecation")
    public void setSelected(boolean selected) {
        if (this.selected != selected) {
            this.selected = selected;

            if (this.selected) {
                Element element=getSelectionDrawable();
                this.tvDay.setBackground(element);
                this.tvDay.setTextColor(Color.MAGENTA);
                this.indicator.setVisibility(Component.HIDE);
                Font font=Font.DEFAULT_BOLD;
                this.tvDay.setFont(font);
                return;
            }

            this.tvDay.setBackground(null);
            this.tvDay.setTextColor(Color.WHITE);
            Font font=Font.DEFAULT;this.tvDay.setFont(font);
            refreshIndicatorVisibility();
        }
    }

    @SuppressWarnings("deprecation")
    public void setIndicatorVisibility(boolean visible) {
        if (this.indicatorVisible != visible) {
            this.indicatorVisible = visible;

            if (this.indicatorVisible && this.indicator.getBackgroundElement() == null) {
                Element element=getIndicatorDrawable();
                this.indicator.setBackground(element);
            }

            refreshIndicatorVisibility();
        }
    }

    private void refreshIndicatorVisibility() {
        Component indicator = this.indicator;

        if (!this.selected && this.indicatorVisible) {
            indicator.setVisibility(Component.VISIBLE);
            return;
        }

        indicator.setVisibility(Component.HIDE);
    }


    private int getDefaultMeasuredHeight() {
        if (_measuredHeight == null) {
            _measuredHeight = dpToPx(context, DEFAULT_HEIGHT);
        }
        return _measuredHeight;
    }

    private int getDefaultIndicatorSize() {
        if (_indicatorSize == null) {
            _indicatorSize = dpToPx(context, DEFAULT_INDICATOR_SIZE);
        }
        return _indicatorSize;
    }

    private int getDefaultIndicatorMarginBottom() {
        if (_indicatorMarginBottom == null) {
            _indicatorMarginBottom = dpToPx(context,DEFAULT_INDICATOR_MARGIN_BOTTOM);
        }
        return _indicatorMarginBottom;
    }

    private Element getSelectionDrawable() {
        if (_selectionDrawable == null) {
            _selectionDrawable = generateCircleCanvas(Color.WHITE);
        }
        return _selectionDrawable;
    }

    private Element getIndicatorDrawable() {
        if (_indicatorDrawable == null) {
            _indicatorDrawable = generateCircleCanvas(Color.MAGENTA);
        }
        return _indicatorDrawable;
    }
}

