package yyl.media.controller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

import yyl.media.controller.base.MediaListenerEvent;
import yyl.media.controller.base.BaseMediaPlayer;
import yyl.media.controller.base.BaseMediaPlayerImpl;
import yyl.media.controller.base.SizeChangeCallBack;
import yyl.media.controller.utils.LogUtils;

/**
 * Created by yuyunlong on 2017/3/8/008.
 */

public class VideoTextureView extends TextureView implements BaseMediaPlayer, TextureView.SurfaceTextureListener, SizeChangeCallBack {
    private final String tag = "TextureViewCallBack";
    private BaseMediaPlayerImpl mediaPlayerBase;

    public VideoTextureView(Context context) {
        super(context);
        init(context, null);
    }

    public VideoTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VideoTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        String videoViewClass = null;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VideoTextureViewYYL);
            videoViewClass = a.getString(R.styleable.VideoTextureViewYYL_videoViewClass);
            a.recycle();
        }
        if (TextUtils.isEmpty(videoViewClass)) {
            videoViewClass = "yyl.media.controller.AndroidMediaPlayer";
        }

        if (isInEditMode()) {
            return;
        }
        mediaPlayerBase = getMediaPlayer(attrs);
        mediaPlayerBase.setVideoSizeChange(this);
        setSurfaceTextureListener(this);

    }

    private BaseMediaPlayerImpl getMediaPlayer(AttributeSet attrs) {


        return null;
    }


    @Override
    public void setMediaListenerEvent(MediaListenerEvent mediaListenerEvent) {
        mediaPlayerBase.setMediaListenerEvent(mediaListenerEvent);
    }


    @Override
    public void onStop() {
        mediaPlayerBase.onStop();
    }

    @Override
    public boolean isRotation() {
        return mediaPlayerBase.isRotation();
    }

    @Override
    public long getPlayOverTime() {
        return mediaPlayerBase.getPlayOverTime();
    }

    @Override
    public void onDestory() {
        mediaPlayerBase.onDestory();
    }


    @Override
    public boolean isPrepare() {
        return mediaPlayerBase.isPrepare();
    }

    @Override
    public boolean canControl() {
        return mediaPlayerBase.canControl();
    }

    @Override
    public void startPlay() {
        mediaPlayerBase.startPlay();
    }

    @Override
    public void setPath(String path) {
        mediaPlayerBase.setPath(path);
    }

    @Override
    public void start() {
        mediaPlayerBase.start();
    }

    @Override
    public void pause() {
        mediaPlayerBase.pause();
    }

    @Override
    public long getDuration() {
        return mediaPlayerBase.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return mediaPlayerBase.getCurrentPosition();
    }

    @Override
    public void seekTo(long pos) {
        mediaPlayerBase.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayerBase.isPlaying();
    }

    @Override
    public void setMirror(boolean mirror) {
        this.mirror = mirror;
        if (mirror) {
            setScaleX(-1f);
        } else {
            setScaleX(1f);
        }
        mediaPlayerBase.setMirror(mirror);
    }

    private boolean mirror;

    @Override
    public boolean getMirror() {
        return mirror;
    }

    @Override
    public int getBufferPercentage() {
        return mediaPlayerBase.getBufferPercentage();
    }

    @Override
    public boolean setPlaybackSpeedMedia(float speed) {
        return mediaPlayerBase.setPlaybackSpeedMedia(speed);
    }

    @Override
    public float getPlaybackSpeed() {
        return mediaPlayerBase.getPlaybackSpeed();
    }

    @Override
    public void setLoop(boolean isLoop) {
        mediaPlayerBase.setLoop(isLoop);
    }

    @Override
    public boolean isLoop() {
        return mediaPlayerBase.isLoop();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mediaPlayerBase.onSurfaceAvailable(surface, width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        adjustAspectRatio();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return mediaPlayerBase.onSurfaceDestroyed();
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private int videoWidth, videoHeight;

    @Override
    public void onVideoSizeChanged(int width, int height) {
        this.videoWidth = width;
        this.videoHeight = height;
        adjustAspectRatio();
    }
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (isInEditMode()) {
            return;
        }
        if (visibility == GONE) {
            onStop();
        } else if (visibility == INVISIBLE) {
            onStop();
        }
    }
    private void adjustAspectRatio() {
        if (videoWidth * videoHeight == 0) return;

        int viewWidth = getWidth();
        int viewHeight = getHeight();
        double videoRatio = (double) viewWidth / (double) viewHeight;//显示比例
        double aspectRatio = (double) videoWidth / (double) videoHeight;//视频比例
        int newWidth, newHeight;
        if (videoWidth > videoHeight) {//正常比例16：9
            if (videoRatio > aspectRatio) {//16:9>16:10
                newWidth = (int) (viewHeight * aspectRatio);
                newHeight = viewHeight;
            } else {//16:9<16:8
                newWidth = viewWidth;
                newHeight = (int) (viewWidth / aspectRatio);
            }
        } else {//非正常可能是 90度视频
            //16:9>1:9
            newWidth = (int) (viewHeight * aspectRatio);
            newHeight = viewHeight;
        }

        float xoff = (viewWidth - newWidth) / 2f;
        float yoff = (viewHeight - newHeight) / 2f;
        Matrix txform = new Matrix();
        getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight
                / viewHeight);
        // txform.postRotate(-90); //
        txform.postTranslate(xoff, yoff);
        setTransform(txform);
        LogUtils.i(tag, "video=" + videoWidth + "x" + videoHeight + " view="
                + viewWidth + "x" + viewHeight + " adjustView=" + newWidth + "x"
                + newHeight + " off=" + xoff + "," + yoff);
    }

}
