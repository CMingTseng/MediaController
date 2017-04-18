package yyl.media.controller.base;

/**
 * Created by yuyunlong on 2017/3/8/008.
 */

public interface MediaListenerEvent {
    int PLAYING_STATE = -10;

    /**
     * 初始化开始加载
     *
     * @param isOpen true加载视频中  false回到初始化  比如列表重新显示封面图
     */
    void eventPlayInit(boolean isOpen);

    /**
     * @param buffingState
     */
    void eventBuffing(int buffingState, boolean isShowBuffing);

    /**
     * @param isPlayError true报错停止
     */
    void eventStop(boolean isPlayError);

    /**
     * @param error true 报错停止
     */
    void eventError(int error, boolean showError);

}