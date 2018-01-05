package com.molaith.omyochitools.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * 获取屏幕相关信息工具
 *
 * @author molaith
 *
 */
public class ScreenUtil {
    private static int statusH=0;

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager m = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = m.getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT < 13) {
            return display.getWidth();
        } else {
            display.getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager m = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = m.getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT < 13) {
            return display.getHeight();
        } else {
            display.getSize(point);
        }
        return point.y;
    }

    /**
     * 获取通知栏高度
     * 必须先init
     * @return
     */
    public static int getStatusHeight(){
        return statusH;
    }

    /**
     *
     * @param context Activity Context only
     */
    public static void initStatusBarHeight(Activity context){
        if (statusH==0) {
            getStatusHeight(context);
        }
    }

    /**
     * 获取通知栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        statusH=statusHeight;
        return statusHeight;
    }
}
