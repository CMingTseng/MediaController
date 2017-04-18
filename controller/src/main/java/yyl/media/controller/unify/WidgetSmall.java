package yyl.media.controller.unify;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import yyl.media.controller.base.BaseMediaPlayer;
import yyl.media.controller.base.BaseWidget;
import yyl.media.controller.utils.StringUtils;

/**
 * Created by yuyunlong on 2017/3/8/008.
 */

public class WidgetSmall extends BaseWidget {

    // @Bind(R.id.yyl_play_pause_small)
    ImageButton mPauseButton;
    // @Bind(R.id.yyl_time_current_small)
    TextView mCurrentTime;

    //   @Bind(R.id.yyl_seekbar_small)
    SeekBar mProgress;
    //    @Bind(R.id.yyl_time_total_small)
    TextView mEndTime;
    // @Bind(R.id.yyl_down_layout_small)
    LinearLayout down_layout;

    View touchView;

    private boolean mDragging;
    private boolean mInstantSeeking = false;
    private boolean mShowing;

    private Context context;

    public WidgetSmall(BaseMediaPlayer mPlayer, View rootView) {
        super(mPlayer);
        this.context = rootView.getContext();
    }

    private void initView() {
        mEndTime.getPaint().setTextSkewX(-0.25f);
        mCurrentTime.getPaint().setTextSkewX(-0.25f);
        updatePausePlay();
        mProgress.setOnSeekBarChangeListener(mSeekListener);
        mProgress.setPadding(0, 0, 0, 0);
        mProgress.setMax(1000);
        mProgress.setThumbOffset(0);
        touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseController.onSingleTap();
            }
        });
    }

    private long setProgress() {
        if (!mShowing || mDragging)
            return 0;
        long position = mPlayer.getCurrentPosition();
        long duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
                int percent = mPlayer.getBufferPercentage();
                mProgress.setSecondaryProgress(percent * 10);
            }
        }
        if (mEndTime != null)
            mEndTime.setText(StringUtils.generateTime(mPlayer.getDuration()));
        if (mCurrentTime != null)
            mCurrentTime.setText(StringUtils.generateTime(position));
        return position;
    }

    private void updatePausePlay() {
        if (mPauseButton == null)
            return;
        if (mPlayer.isPlaying()) {
            mPauseButton.setSelected(true);
        } else {
            mPauseButton.setSelected(false);
        }

    }

    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {
            mDragging = true;
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser)
                return;
            long newposition = (mPlayer.getDuration() * progress) / 1000;
            String time = StringUtils.generateTime(newposition);
            if (mInstantSeeking)
                mPlayer.seekTo(newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(time);
        }

        public void onStopTrackingTouch(SeekBar bar) {
            if (!mInstantSeeking)
                mPlayer.seekTo((mPlayer.getDuration() * bar.getProgress()) / 1000);
            mDragging = false;
        }
    };

    @Override
    public void show(boolean isFullState) {
        if (mShowing) {
            return;
        }
        mShowing = true;
        startOpenAnimation(context);
    }

    @Override
    public void close() {
        mShowing = false;
        down_layout.setVisibility(View.GONE);
    }

    @Override
    public void hide(boolean isFullState) {
        if (mShowing) {
            mShowing = false;
            startHideAnimation(context);
        }
    }

    @Override
    public void onProgressUpdate() {
        if (!mShowing) return;
        updatePausePlay();
        setProgress();
    }

    @Override
    public boolean isDragging() {
        return mDragging;
    }


    @Override
    public void eventPlayInit(boolean isOpen) {

    }

    @Override
    public void eventBuffing(int buffingState, boolean isShowBuffing) {

    }

    @Override
    public void eventStop(boolean isPlayError) {

    }

    @Override
    public void eventError(int error, boolean showError) {

    }

    @Override
    public void changeScreen(boolean isFull) {
        touchView.setVisibility(isFull ? View.GONE : View.VISIBLE);
    }

    private void startOpenAnimation(Context context) {
        if (down_layout.getVisibility() != View.VISIBLE) {
            down_layout.setVisibility(View.VISIBLE);
        }
        Animation anima = AnimationUtils.loadAnimation(context, context.getResources().getIdentifier("in_from_down", "anim", context.getPackageName()));
        down_layout.startAnimation(anima);
    }


    private void startHideAnimation(Context context) {
        Animation out_from_down = AnimationUtils.loadAnimation(context, context.getResources().getIdentifier("out_from_down", "anim", context.getPackageName()));
        out_from_down.setAnimationListener(animationListener);
        down_layout.startAnimation(out_from_down);
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            down_layout.setVisibility(View.GONE);
            // projection_small.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
