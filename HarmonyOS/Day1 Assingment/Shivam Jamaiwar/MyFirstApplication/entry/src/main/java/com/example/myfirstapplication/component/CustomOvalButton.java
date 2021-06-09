package com.example.myfirstapplication.component;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.Rect;
import ohos.agp.utils.RectFloat;
import ohos.agp.utils.TextAlignment;
import ohos.app.Context;

public class CustomOvalButton extends Component implements Component.DrawTask {

    private static final String BOOTSTRAP_BUTTON_COLOR = "buttonColor";
    private static final String BOOTSTRAP_BUTTON_TEXT = "text";
    private static final String BOOTSTRAP_BUTTON_TEXT_COLOR = "textColor";
    private static final String BOOTSTRAP_BUTTON_TEXT_SIZE = "textSize";

    // Paint for drawing the circle
    private Paint ovalPaint, textPaint;

    private String text = "Okay";
    private Color buttonColor = Color.GREEN;
    private Color textColor = Color.BLACK;
    private int textSize = 50;

    public CustomOvalButton(Context context) {
        super(context);
        init(null);
    }

    public CustomOvalButton(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init(attrSet);
    }

    public CustomOvalButton(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(attrSet);
    }

    private void init(AttrSet attrSet) {
        ovalPaint = new Paint();
        textPaint = new Paint();
        if (attrSet != null) {
            this.text = attrSet.getAttr(BOOTSTRAP_BUTTON_TEXT).isPresent() ? attrSet.getAttr(
                    BOOTSTRAP_BUTTON_TEXT).get().getStringValue() : "Okay";
            this.buttonColor = attrSet.getAttr(BOOTSTRAP_BUTTON_COLOR).isPresent() ? attrSet.getAttr(
                    BOOTSTRAP_BUTTON_COLOR).get().getColorValue() : Color.GREEN;
            this.textColor = attrSet.getAttr(BOOTSTRAP_BUTTON_TEXT_COLOR).isPresent() ? attrSet.getAttr(
                    BOOTSTRAP_BUTTON_TEXT_COLOR).get().getColorValue() : Color.BLACK;
            this.textSize = attrSet.getAttr(BOOTSTRAP_BUTTON_TEXT_SIZE).isPresent() ? attrSet.getAttr(
                    BOOTSTRAP_BUTTON_TEXT_SIZE).get().getIntegerValue() : 50;
        }
        addDrawTask(this::onDraw);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {

        component.setWidth(textSize * 6);
        //set oval properties
        ovalPaint.setColor(buttonColor);
        ovalPaint.setStyle(Paint.Style.FILL_STYLE);
        ovalPaint.setAntiAlias(true);

        canvas.drawRoundRect(
                new RectFloat(0, 0, component.getWidth(), component.getHeight()),
                component.getHeight() / 2,
                component.getHeight() / 2,
                ovalPaint
        );

        //set the text color using the color specified
        ovalPaint.setColor(textColor);
        //set text properties
        ovalPaint.setTextAlign(TextAlignment.CENTER);
        ovalPaint.setTextSize(textSize);

        //draw the text using the string attribute and chosen properties
        canvas.drawText(ovalPaint, text, component.getWidth() / 2, (component.getHeight() + component.getMarginTop()) / 2);

    }

}
