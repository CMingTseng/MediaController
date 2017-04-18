package yyl.media.controller.unify;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import yyl.media.controller.base.BaseGestureListener;
import yyl.media.controller.base.ConfigurationListener;

/**
 * Created by yuyunlong on 2017/3/9/009.
 */

public class ViewTouchEvent extends GestureDetector.SimpleOnGestureListener implements ConfigurationListener {

    private BaseGestureListener listener;
    private static final int SWIPE_THRESHOLD = 60;//threshold of swipe
    public static final int SWIPE_LEFT = 1;
    public static final int SWIPE_RIGHT = 2;
    private int deviceWidth, deviceHeight;
    private GestureDetector mGestureDetector;

    public ViewTouchEvent() {

    }

    public void attachView(View fullTouchView) {
        if (fullTouchView == null) return;
        Context context = fullTouchView.getContext();
        mGestureDetector = new GestureDetector(context, this);
        deviceWidth = getDeviceWidth(context);
        deviceHeight = getDeviceHeight(context);
        fullTouchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    boolean isresult = mGestureDetector.onTouchEvent(event);
                    if (!isresult) {

                    }
                } else {
                    return mGestureDetector.onTouchEvent(event);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        listener.onSingleTap();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float deltaX = e1.getRawX() - e2.getRawX();
        float deltaY = e1.getRawY() - e2.getRawY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (Math.abs(deltaX) > SWIPE_THRESHOLD) {
                // listener.onHorizontalScroll(deltaX < 0);
            }
        } else {
            if (Math.abs(deltaY) > SWIPE_THRESHOLD) {
                if (e1.getX() < deviceWidth * 1.0 / 5) {//left edge
                    //      listener.onVerticalScroll(deltaY / deviceHeight, SWIPE_LEFT);
                } else if (e1.getX() > deviceWidth * 4.0 / 5) {//right edge
                    //     listener.onVerticalScroll(deltaY / deviceHeight, SWIPE_RIGHT);
                }
            }
        }
        return true;
    }

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.heightPixels;
    }

    @Override
    public void changeScreen(boolean isFull) {

    }
}
