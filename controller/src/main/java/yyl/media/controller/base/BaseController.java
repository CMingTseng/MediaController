package yyl.media.controller.base;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by yuyunlong on 2017/3/8/008.
 */

public abstract class BaseController implements ConfigurationListener {

    private String tag = "BaseController";

    protected static final ArrayList<ConfigurationListener> screenPlayerListeners = new ArrayList<>(1);
    protected static final LinkedList<ConfigurationListener> screenActivityQueue = new LinkedList();

    protected static final int FADE_OUT = 1;
    protected static final int UPDATA_PROGRESS = 2;
    protected static final int SHOW = 3;
    private boolean isShow;
    private int mTime = 3000;
    protected boolean isDestory;
    protected boolean isVideoOpen;
    private boolean isUpdate;

    public static boolean isFullState() {
        return isFullState;
    }

    private static boolean isFullState;
    protected BaseWidget widget;
    protected BaseWidget widgetSmall;
    protected BaseWidget widgetFull;

    private int DELAYEDTIME = 300;

    public BaseController(BaseWidget widget, BaseWidget widgetSmall, BaseWidget widgetFull) {
        this.widget = widget;
        this.widgetSmall = widgetSmall;
        this.widgetFull = widgetFull;
        widget.attachController(this);
        widgetSmall.attachController(this);
        widgetFull.attachController(this);
    }


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isDestory) {
                return;
            }
            switch (msg.what) {
                case FADE_OUT:
                    if (isDragging()) {
                        sendEmptyMessageDelayed(FADE_OUT, mTime);
                    } else {
                        hide();
                    }
                    break;
                case UPDATA_PROGRESS:
                    removeMessages(UPDATA_PROGRESS);
                    if (!isVideoOpen) {
                        isUpdate = false;
                        return;
                    }
                    isUpdate = true;
                    if (!isDragging()) {
                        onProgressUpdate();
                    }
                    sendEmptyMessageDelayed(UPDATA_PROGRESS, DELAYEDTIME);
                    break;
                case SHOW:
                    show();
                    break;
            }
        }
    };


    protected void openVideo(boolean openClose) {
        if (openClose) {
            isVideoOpen = true;
            screenPlayerListeners.clear();
            screenPlayerListeners.add(this);
            mHandler.sendEmptyMessageDelayed(SHOW, 500);
        } else {
            isVideoOpen = false;
            screenPlayerListeners.remove(this);
            mHandler.sendEmptyMessage(FADE_OUT);
            mHandler.removeMessages(UPDATA_PROGRESS);
        }
    }

    public void show() {
        mHandler.removeMessages(FADE_OUT);
        if (!isUpdate) {
            mHandler.sendEmptyMessage(UPDATA_PROGRESS);
        }
        isShow = true;
        widget.hide(isFullState);
        widgetSmall.show(isFullState);
        widgetFull.show(isFullState);
        mHandler.sendEmptyMessageDelayed(FADE_OUT, mTime);
    }

    public void hide() {
        mHandler.removeMessages(FADE_OUT);
        isShow = false;
        widget.show(isFullState);
        widgetSmall.hide(isFullState);
        widgetFull.hide(isFullState);
    }

    public void close() {
        isShow = false;
        widget.close();
        widgetSmall.close();
        widgetFull.close();
    }

    //是否按下了进度条
    private boolean isDragging() {
        return widgetSmall.isDragging() || widgetFull.isDragging();
    }


    private void onProgressUpdate() {
        if (isShow) {
            widgetSmall.onProgressUpdate();
            widgetFull.onProgressUpdate();
        } else {
            widget.onProgressUpdate();
        }
    }

    public void onSingleTap() {
        if (!isShow) {
            show();
        } else {
            hide();
        }
    }


    @Override
    public void changeScreen(boolean isFull) {
        //取出Stack的第一个activity切屏
        ConfigurationListener activityListener = screenActivityQueue.peekFirst();
        if (activityListener != null) {
            activityListener.changeScreen(isFull);
        }
        if (isVideoOpen) {
            widget.changeScreen(isFull);
            widgetSmall.changeScreen(isFull);
            widgetFull.changeScreen(isFull);
        }
        this.isFullState = isFull;
    }


    public void onAttachedToWindow() {
    }

    public void onDetachedFromWindow() {
        isDestory = true;
    }
}
