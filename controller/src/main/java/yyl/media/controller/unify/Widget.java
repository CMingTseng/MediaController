package yyl.media.controller.unify;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import yyl.media.controller.base.BaseController;
import yyl.media.controller.base.BaseMediaPlayer;
import yyl.media.controller.base.BaseWidget;
import yyl.media.controller.base.MediaListenerEvent;
import yyl.media.controller.utils.NetworkUtils;

/**
 * Created by yuyunlong on 2017/3/8/008.
 */

public class Widget extends BaseWidget {


    private ImageView yylSpeedDirection;
    private TextView yylSpeedText;
    private LinearLayout yylSpeedLayout;
    //@BindView(R.id.yyl_bottom_progress)
    private ProgressBar yylBottomProgress;
    // @BindView(R.id.yyl_video_cover)
    private ImageView yylVideoCover;
    //    @BindView(R.id.yyl_video_cover_icon)
    private ImageView yylVideoCoverIcon;
    // @BindView(R.id.yyl_cover_layout)
    private FrameLayout yylCoverLayout;
    // @BindView(R.id.yyl_buffering_text)
    private TextView yylBufferingText;
    //  @BindView(R.id.yyl_buffering_layout)
    private LinearLayout yylBufferingLayout;
    //  @BindView(R.id.yyl_error_layout)
    private LinearLayout yylErrorLayout;
    private LinearLayout yylTipLayout;
    //.getIdentifier(name, type, packageName);

    private Context context;

    public Widget(BaseMediaPlayer mPlayers, View rootView) {
        super(mPlayers);
        context = rootView.getContext();
        Resources resources = context.getResources();
        String defType = "id";
        String defPackage = rootView.getContext().getPackageName();
        yylSpeedDirection = (ImageView) rootView.findViewById(resources.getIdentifier("yyl_speed_direction", defType, defPackage));
        yylSpeedText = (TextView) rootView.findViewById(resources.getIdentifier("yyl_speed_direction", defType, defPackage));
        yylSpeedLayout = (LinearLayout) rootView.findViewById(resources.getIdentifier("yyl_speed_layout", defType, defPackage));
        yylSpeedDirection = (ImageView) rootView.findViewById(resources.getIdentifier("yyl_speed_direction", defType, defPackage));
        yylSpeedDirection = (ImageView) rootView.findViewById(resources.getIdentifier("yyl_speed_direction", defType, defPackage));
        yylSpeedDirection = (ImageView) rootView.findViewById(resources.getIdentifier("yyl_speed_direction", defType, defPackage));

        yylVideoCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCover();
            }
        });
        yylErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCover();
            }
        });

        yylTipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null)
                    mPlayer.startPlay();
            }
        });
    }

    public ImageView getVideoCover() {
        return yylVideoCover;
    }


    @Override
    public void show(boolean isFullState) {
        yylBottomProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void close() {
        yylBottomProgress.setVisibility(View.GONE);
    }

    @Override
    public void hide(boolean isFullState) {
        yylBottomProgress.setVisibility(View.GONE);
    }

    @Override
    public void onProgressUpdate() {
        if (yylBottomProgress != null) {
            long position = mPlayer.getCurrentPosition();
            long duration = mPlayer.getDuration();
            if (duration > 0) {
                long pos = 1000L * position / duration;
                yylBottomProgress.setProgress((int) pos);
                yylBottomProgress.setSecondaryProgress(mPlayer.getBufferPercentage());
            } else {
                yylBottomProgress.setProgress(0);
            }

        }


    }

    @Override
    public boolean isDragging() {
        return false;
    }


    @Override
    public void attachController(BaseController baseController) {

    }

    @Override
    public void eventPlayInit(boolean isOpen) {
        if (isOpen) {
            yylBottomProgress.setProgress(0);
            yylTipLayout.setVisibility(View.GONE);
            yylCoverLayout.setVisibility(View.GONE);
            yylBufferingLayout.setVisibility(View.VISIBLE);
            yylVideoCoverIcon.setVisibility(View.GONE);
            yylErrorLayout.setVisibility(View.GONE);
        } else {
            yylBottomProgress.setProgress(1000);
        }
    }

    @Override
    public void eventBuffing(int buffingState, boolean isShow) {
        if (buffingState == MediaListenerEvent.PLAYING_STATE) {
            yylCoverLayout.setVisibility(View.GONE);
            yylBufferingLayout.setVisibility(View.GONE);
        }
        if (isShow) {
            yylBufferingText.setVisibility(buffingState > 0 ? View.VISIBLE : View.GONE);
            yylBufferingText.setText(buffingState + "%");
            yylBufferingLayout.setVisibility(View.VISIBLE);
        } else {
            yylBufferingLayout.setVisibility(View.GONE);
        }
        if (yylErrorLayout.getVisibility() == View.VISIBLE) {
            yylErrorLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void eventStop(boolean isPlayError) {
        if (isPlayError) {
            yylCoverLayout.setVisibility(View.VISIBLE);
            yylVideoCoverIcon.setVisibility(View.GONE);
            yylBufferingLayout.setVisibility(View.GONE);
            yylErrorLayout.setVisibility(View.VISIBLE);
        } else {

        }
    }

    @Override
    public void eventError(int error, boolean show) {
        if (show) {
            yylCoverLayout.setVisibility(View.VISIBLE);
            yylVideoCoverIcon.setVisibility(View.GONE);
            yylBufferingLayout.setVisibility(View.GONE);
            yylErrorLayout.setVisibility(View.VISIBLE);
        }
    }

    private void onClickCover() {
        if (NetworkUtils.isWifiConnected(context)) {
            if (mPlayer != null)
                mPlayer.startPlay();
        } else {
            yylCoverLayout.setVisibility(View.GONE);
            yylTipLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void changeScreen(boolean isFull) {

    }
}
