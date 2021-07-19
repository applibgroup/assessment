package com.applib.loginsignupapp.component;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.TextAlignment;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TextFieldValidated extends TextField implements Component.DrawTask {


    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "VALIDATION_FIELD_TAG");
    private static final  String BOOTSTRAP_VALIDATION_TYPE = "textType";
    private static final String BOOTSTARP_VALIDATION_ERROR_LABEL = "errorText";
    private static final String BOOTSTARP_VALIDATION_ERROR_LABEL_SIZE = "errorTextSize";
    private static final String BOOTSTARP_VALIDATION_ERROR_LABEL_COLOR = "errorTextColor";

    private String textType="all";
    private String errorText = "*Not Validated";
    private int errorTextSize = 15;
    private Color errorTextColor = Color.RED;

    private TextFieldValidated self;
    private Integer originalHeight=null;
    private Integer originalWidth=null;
    private Boolean isValidated;
    private Paint paint;

    private static final Map<String,String> regexMap;
    static {
        regexMap =  new HashMap<>();
        regexMap.put("all",".*");
        regexMap.put("email","^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$");
        //Regex for Indian Phone Numbers
        regexMap.put("phone","^(\\+91[\\-\\s]?)?[0]?(91)?[56789]\\d{9}$");
        regexMap.put("name","^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        regexMap.put("password","^.{5,}$");
    }




    public TextFieldValidated(Context context) {
        super(context);
    }

    public TextFieldValidated(Context context, AttrSet attrSet) {
        super(context, attrSet);
        self = this;
        init(attrSet);
    }

    private void init(AttrSet attrSet) {
        paint= new Paint();
        if(attrSet != null){
            this.errorText = attrSet.getAttr(BOOTSTARP_VALIDATION_ERROR_LABEL).isPresent()
                    ? attrSet.getAttr(BOOTSTARP_VALIDATION_ERROR_LABEL).get().getStringValue()
                    : "*Not Validated";
            this.errorTextSize = attrSet.getAttr(BOOTSTARP_VALIDATION_ERROR_LABEL_SIZE).isPresent()
                    ? attrSet.getAttr(BOOTSTARP_VALIDATION_ERROR_LABEL_SIZE).get().getDimensionValue()
                    : 15;
            this.errorTextColor = attrSet.getAttr(BOOTSTARP_VALIDATION_ERROR_LABEL_COLOR).isPresent()
                    ? attrSet.getAttr(BOOTSTARP_VALIDATION_ERROR_LABEL_COLOR).get().getColorValue()
                    : Color.RED;

            if(attrSet.getAttr(BOOTSTRAP_VALIDATION_TYPE).isPresent()){
                String typeValue = attrSet.getAttr(BOOTSTRAP_VALIDATION_TYPE).get().getStringValue();
                if(regexMap.containsKey(typeValue))
                    textType=typeValue;
            }
        }

        this.addTextObserver(new TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                //HiLog.warn(LABEL, "Text Changed to: "+s );
                String typeRegex = regexMap.get(textType);
                Pattern pattern = Pattern.compile(typeRegex);
                if(s!=null && pattern.matcher(s).matches()) {
                    isValidated = true;
                } else {
                    isValidated = false;
                }
                HiLog.warn(LABEL, "Calling DrawTask.onDraw()" );
                addDrawTask(self::onDraw);
            }
        });
    }

    public boolean isValidated(){
        String text = getText();
        String typeRegex = regexMap.get(textType);
        Pattern pattern = Pattern.compile(typeRegex);
        if(text!=null && pattern.matcher(text).matches()) {
            isValidated = true;
        } else {
            isValidated = false;
        }
        addDrawTask(self::onDraw);
        return isValidated;
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        HiLog.warn(LABEL, "OnDraw Called" );

        if(originalHeight==null) {
            originalHeight = getHeight();
            originalWidth = getWidth();
            canvas.save();
        } else
            HiLog.warn(LABEL, getHeight() + " :: " + originalHeight );

        if(isValidated!=null) {
            if(!isValidated) {
                int verticalMargin = getMarginBottom() + getMarginTop();
                paint.setColor(errorTextColor);
                paint.setTextAlign(TextAlignment.CENTER);
                paint.setTextSize(errorTextSize);

                if(originalWidth<(int) paint.measureText(errorText))
                    this.setWidth((int) paint.measureText(errorText));

                float x = getScaleX()+getWidth()/2;
                float y = getScaleY() + originalHeight - getMarginBottom() + errorTextSize/2;
                canvas.drawText(paint, errorText, x, y);


                this.setHeight(originalHeight + errorTextSize);
                HiLog.warn(LABEL, "After Draw:  " + getHeight() + " :: " + originalHeight);
                HiLog.warn(LABEL, "After Draw:  " + getWidth() + " :: " + originalWidth);
            } else {
                this.setHeight(originalHeight);
                this.setWidth(originalWidth);
                canvas.restoreToCount(1);
            }
        }
    }
}
