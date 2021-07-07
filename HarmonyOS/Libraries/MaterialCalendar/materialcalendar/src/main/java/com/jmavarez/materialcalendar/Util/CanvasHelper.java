package com.jmavarez.materialcalendar.Util;

import ohos.agp.components.Component;
import ohos.app.Context;

public class CanvasHelper {

    public static int dpToPx(Context context, int dp) {
        return dp * context.getResourceManager().getDeviceCapability().screenDensity / 160;
    }

    public static float dpToPx(Context context, float dp) {
        return dp * context.getResourceManager().getDeviceCapability().screenDensity / 160;
    }

    public static float dpToPx(Component view, int size) {
        return dpToPx(view.getContext(), size);
    }

    public static int pxToDp(Context context, int size) {
         return size / (context.getResourceManager().getDeviceCapability().screenDensity / 160);
    }
}
