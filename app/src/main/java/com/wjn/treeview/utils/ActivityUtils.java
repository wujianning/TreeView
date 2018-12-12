package com.wjn.treeview.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by wujna on 2018/2/11.
 * Activity 相关操作的工具类
 */

public class ActivityUtils {

    /**
     * 判断当前Activity是否处于正常状态 显示Dialog时 判断Dialog依附的当前Activity是否处于正常状态
     * */

    public static boolean isActivityIsAlive(Activity activity) {
        if (null == activity) {
            return false;
        }

        if (activity.isFinishing()) {
            return false;
        }
        return true;
    }

    /**
     * 获取屏幕宽度
     * */

    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取屏幕高度
     * */

    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 根据手机分辨率从DP转成PX
     */

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     */

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕密度 0.75 1.0 1.5 2.0 3.0
     * */

    public static float getScreenDensity(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float density  = dm.density;
        return  density;
    }

}
