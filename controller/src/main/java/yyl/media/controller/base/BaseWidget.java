package yyl.media.controller.base;



/**
 * Created by yyl on 2017/3/8/008.
 */

public abstract class BaseWidget implements MediaListenerEvent, ConfigurationListener {

    protected BaseMediaPlayer mPlayer;
    protected BaseController baseController;

    public BaseWidget(BaseMediaPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    public abstract void show(boolean isFullState);

    public abstract void close();

    public abstract void hide(boolean isFullState);

    public abstract void onProgressUpdate();

    public abstract boolean isDragging();

    public void attachController(BaseController baseController) {
        this.baseController = baseController;
    }

}
