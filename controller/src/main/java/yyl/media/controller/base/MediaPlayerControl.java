package yyl.media.controller.base;

/**
 * Created by yyl on 2016/2/16/016.
 * 控制器
 */
public interface MediaPlayerControl {

    boolean isPrepare();

    boolean canControl();

    /**
     * init入口
     */
    void startPlay();

    void setPath(String path);

    void start();

    void pause();

    long getDuration();

    long getCurrentPosition();

    void seekTo(long pos);

    boolean isPlaying();

    /**
     * 第二级缓冲
     *
     * @return
     */
    int getBufferPercentage();

    /**
     * 速度  0.25--4.0
     */
    boolean setPlaybackSpeedMedia(float speed);

    float getPlaybackSpeed();

    /**
     * 循环
     */
    void setLoop(boolean isLoop);

    boolean isLoop();
}
