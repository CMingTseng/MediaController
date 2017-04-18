package yyl.media.controller.unify;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import yyl.media.controller.R;
import yyl.media.controller.base.BaseGestureListener;
import yyl.media.controller.base.BaseMediaPlayer;
import yyl.media.controller.base.BaseWidget;
import yyl.media.controller.utils.StringUtils;

/**
 * Created by yuyunlong on 2017/3/8/008.
 */

public class WidgetFull extends BaseWidget implements BaseGestureListener {

    ImageButton mPauseButton;
    TextView mCurrentTime;
    SeekBar mProgress;
    TextView mEndTime;
    LinearLayout down_layout;


    View yylFullTouch;
    ImageButton yylFullBack;
    TextView yylFullTitle;
    LinearLayout yylFullUpLayout;
    private boolean mDragging;
    private boolean mInstantSeeking = false;
    private boolean mShowing;

    private Context context;

    public WidgetFull(BaseMediaPlayer mPlayer, View rootView) {
        super(mPlayer);
        this.context = rootView.getContext();
    }


    private void findView(){

    }

    private void initView() {
        mEndTime.getPaint().setTextSkewX(-0.25f);
        mCurrentTime.getPaint().setTextSkewX(-0.25f);
        updatePausePlay();
        mProgress.setOnSeekBarChangeListener(mSeekListener);
        mProgress.setPadding(0, 0, 0, 0);
        mProgress.setMax(1000);
        mProgress.setThumbOffset(0);
        yylFullTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
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
        yylFullTouch.setVisibility(isFull ? View.VISIBLE : View.GONE);
    }

    private void startOpenAnimation(Context context) {
        down_layout.setVisibility(View.VISIBLE);
        yylFullUpLayout.setVisibility(View.VISIBLE);
        Animation in_from_down = AnimationUtils.loadAnimation(context, context.getResources().getIdentifier("in_from_down", "anim", context.getPackageName()));
        down_layout.startAnimation(in_from_down);
        Animation in_from_up = AnimationUtils.loadAnimation(context, context.getResources().getIdentifier("in_from_up", "anim", context.getPackageName()));
        yylFullUpLayout.startAnimation(in_from_up);

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

    @Override
    public void onSingleTap() {

    }

    @Override
    public void onHorizontalScroll(float percent, boolean fromLeftToRight) {

    }

    @Override
    public void onVerticalScroll(float percent, boolean fromUpToDown) {

    }
}
