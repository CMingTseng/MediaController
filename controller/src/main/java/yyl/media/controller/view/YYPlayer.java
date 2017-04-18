package yyl.media.controller.view;


import android.content.Context;
import android.util.AttributeSet;

import yyl.media.controller.Controller;
import yyl.media.controller.R;
import yyl.media.controller.base.BaseMediaPlayer;
import yyl.media.controller.unify.Widget;
import yyl.media.controller.unify.WidgetFull;
import yyl.media.controller.unify.WidgetSmall;

/**
 * Created by yuyunlong on 2017/3/9/009.
 */

public class YYPlayer extends FrameLayoutScale {
    private BaseMediaPlayer mediaPlayer;
    private Controller controller;

    public YYPlayer(Context context) {
        super(context);
        init(context);

    }

    public YYPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YYPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.yyl_player_main, this);
        mediaPlayer = (BaseMediaPlayer) findViewWithTag("yyl.media.controller.VideoTextureView");
        controller = new Controller.Builder()
                .setBaseMediaPlayer(mediaPlayer)
                .setWidget(new Widget(mediaPlayer, this))
                .setWidgetSmall(new WidgetSmall(mediaPlayer, this))
                .setWidgetFull(new WidgetFull(mediaPlayer, this))
                .build();
    }

    public void setPath(String path) {
        mediaPlayer.setPath(path);
    }

    public void startPlay() {
        mediaPlayer.startPlay();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        controller.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        controller.onDetachedFromWindow();
    }

}
