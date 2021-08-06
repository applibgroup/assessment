package com.devpatel10.loginsignup.component;

import ohos.aafwk.ability.OnClickListener;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.RectFloat;
import ohos.app.Context;

public class ButtonComponent extends Component implements Component.DrawTask {
    private static final float CIRCLE_STROKE_WIDTH = 100f;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 100;
    private static final int BUTTON_CORNER_RADIUS = 10;
    public static final Color BUTTON_BACKGROUND = Color.CYAN;
    public static final Color BUTTON_BOUNDARY = Color.BLUE;

    private Paint buttonPaint;
    private Paint boundaryPaint;
    private int width;
    private int height;
    private int radius;
    private String text;
    private Color background ;
    private Color buttonBorder;
    private ClickedListener listener;

    @Override
    public void setClickedListener(ClickedListener listener) {
        super.setClickedListener(listener);
    }

    public ButtonComponent(Context context) {
        super(context);
        init();
        onEstimateSize(width,height);
        setClickedListener(listener);

        addDrawTask(this);
    }

    private void init(){
        buttonPaint = new Paint();
        buttonPaint.setColor(BUTTON_BACKGROUND);
        boundaryPaint= new Paint();
        boundaryPaint.setColor(BUTTON_BOUNDARY);
        boundaryPaint.setStrokeWidth(CIRCLE_STROKE_WIDTH);
        radius=BUTTON_CORNER_RADIUS;
        text="Button";
        listener=null;
        width=BUTTON_WIDTH;
        height=BUTTON_HEIGHT;

    }
    public boolean onEstimateSize(int widthEstimateConfig, int heightEstimateConfig) {
        int width = Component.EstimateSpec.getSize(widthEstimateConfig);
        int height = Component.EstimateSpec.getSize(heightEstimateConfig);
        setEstimatedSize(
                Component.EstimateSpec.getChildSizeWithMode(width, width, Component.EstimateSpec.PRECISE),
                Component.EstimateSpec.getChildSizeWithMode(height, height, Component.EstimateSpec.PRECISE)
        );
        return true;
    }
    public void onDraw(Component component, Canvas canvas) {
        int left=0;
        int right=left+BUTTON_WIDTH;
        int bottom=0;
        int top=bottom+BUTTON_HEIGHT;
        RectFloat r= new RectFloat(0,200,1000,0);
        canvas.drawRoundRect(r,radius,radius,buttonPaint);
    }

}
