package yyl.media.controller.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import yyl.media.controller.Controller;
import yyl.media.controller.R;


/**
 * Created by yyl on 2016/10/19/019.
 */

public class FrameLayoutScale extends FrameLayout {

    protected float videoScale = 9f / 16f;
    protected float lastScale = videoScale;
    private String tag = "FrameLayoutScale";

    public FrameLayoutScale(Context context) {
        this(context, null);
    }

    public FrameLayoutScale(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameLayoutScale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LayoutScaleYYL);
            videoScale = a.getFloat(R.styleable.LayoutScaleYYL_scale, videoScale);
            a.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (width != 0 && videoScale > 0) {
            //视频显示比例16/9
            getLayoutParams().height = (int) (width * videoScale);
            setMeasuredDimension(width, (int) (width * videoScale));
        } else if (width != 0) {
            //没有显示比例 就全屏播放视频
            getLayoutParams().height = LayoutParams.MATCH_PARENT;
            setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
        }
    }

    public void setScale(float videoScale) {
        this.videoScale = videoScale;
    }

    public void setScaleFull(boolean full) {
        if (full) {
            videoScale = -1;
        } else {
            videoScale = lastScale;
        }
        requestLayout();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setScaleFull(Controller.isFullState());
    }

}
