package com.applib.jawahar_venugopal.hmosassignment.customview;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.Rect;
import ohos.app.Context;

public class CustomButton extends Component implements Component.DrawTask {

    private static final String BUTTON_COLOR_ATTR = "button_color";
    private static final String BUTTON_TEXT_ATTR = "button_text";
    private static final String BUTTON_TEXT_SIZE_ATTR = "button_text_size";
    private static final String BUTTON_TEXT_COLOR_ATTR = "button_text_color";

    private Color mButtonColor = Color.BLUE;
    private String mButtonText = "Text";
    private int mButtonTextSize = 50;
    private Color mButtonTextColor = Color.BLACK;


    private Paint mButtonPaint;
    private Paint mButtonTextPaint;

    public CustomButton(Context context) {
        super(context);
        init(null);
    }

    public CustomButton(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init(attrSet);
    }

    public CustomButton(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(attrSet);
    }

    private void init(AttrSet attrSet) {
        if (attrSet != null) {
            mButtonColor = attrSet.getAttr(BUTTON_COLOR_ATTR).isPresent() ? attrSet.getAttr(BUTTON_COLOR_ATTR).get().getColorValue() : mButtonColor;
            mButtonText = attrSet.getAttr(BUTTON_TEXT_ATTR).isPresent() ? attrSet.getAttr(BUTTON_TEXT_ATTR).get().getStringValue() : mButtonText;
            mButtonTextSize = attrSet.getAttr(BUTTON_TEXT_SIZE_ATTR).isPresent() ? attrSet.getAttr(BUTTON_TEXT_SIZE_ATTR).get().getIntegerValue() : mButtonTextSize;
            mButtonTextColor = attrSet.getAttr(BUTTON_TEXT_COLOR_ATTR).isPresent() ? attrSet.getAttr(BUTTON_TEXT_COLOR_ATTR).get().getColorValue() : mButtonTextColor;
        }
        initPaint();
        addDrawTask(this);
    }

    private void initPaint() {
        mButtonPaint = new Paint();
        mButtonPaint.setColor(mButtonColor);
        mButtonPaint.setStyle(Paint.Style.FILL_STYLE);

        mButtonTextPaint = new Paint();
        mButtonTextPaint.setColor(mButtonTextColor);
        mButtonTextPaint.setTextSize(mButtonTextSize);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        int width = getEstimatedWidth();
        int height = getEstimatedHeight();

        Rect buttonRect = new Rect(0, 0, width, height);
        canvas.drawRect(buttonRect, mButtonPaint);

        Rect textBounds = mButtonTextPaint.getTextBounds(mButtonText);
        float xPos = buttonRect.getCenterX() - (textBounds.getWidth() / 2);
        float yPos = buttonRect.getCenterY() + (textBounds.getHeight() / 2);
        canvas.drawText(mButtonTextPaint, mButtonText, xPos, yPos);
    }
}
