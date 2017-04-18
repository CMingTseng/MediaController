package yyl.media.controller.base;

/**
 * Created by yyl on 2017/3/8/008.
 * 生命周期的控制
 * 和videoView surface控制
 */

public interface BaseMediaPlayer extends MediaPlayerControl {

    void setMediaListenerEvent(MediaListenerEvent mediaListenerEvent);

    void onStop();

    boolean isRotation();

    long getPlayOverTime();

    void onDestory();

    /**
     * 镜面
     */
    void setMirror(boolean mirror);

    boolean getMirror();
}
