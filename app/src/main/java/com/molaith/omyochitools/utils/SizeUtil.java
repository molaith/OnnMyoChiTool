package com.molaith.omyochitools.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by molaith on 2017/4/18.
 */

public class SizeUtil {
    public static final String WIDTH="design_width";
    public static final String HEIGHT="design_height";


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int convert2SuitablePX(Context context, int px, String widthorheight){
        try {
            if (widthorheight.equals(WIDTH)) {
                int designedW=context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getInt(WIDTH);
                int currengW= ScreenUtil.getScreenWidth(context);
                return ((px*currengW)/designedW);
            }else if (widthorheight.equals(HEIGHT)) {
                int designedH=context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getInt(HEIGHT);
                int currengH= ScreenUtil.getScreenHeight(context);
                return ((px*currengH)/designedH);
            }
        } catch (Exception e) {
        }
        return px;
    }
}
