package yyl.media.controller.base;

import android.graphics.SurfaceTexture;

/**
 * Created by yuyunlong on 2017/3/9/009.
 * 扩展播放器的接口---------
 * 实现此接口就是一个播放器的原型
 */

public abstract class BaseMediaPlayerImpl implements BaseMediaPlayer {


    public abstract void setVideoSizeChange(SizeChangeCallBack sizeChangeCallBack);

    public abstract void onSurfaceAvailable(SurfaceTexture surface, int width, int height);

    public abstract boolean onSurfaceDestroyed();



    //下层用不上
    @Override
    public void setMirror(boolean mirror) {

    }
    //下层用不上
    @Override
    public boolean getMirror() {
        return false;
    }



}
