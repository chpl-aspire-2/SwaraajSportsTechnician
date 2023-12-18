package com.swaraajsports.technician.exoplayer.utils;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class PublicFunctions {

    public static int convertDpToPixel(Context context, float dp) {
        return (int) (dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertPixelsToDp(Context context, float px) {
        return (int) (px / (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
