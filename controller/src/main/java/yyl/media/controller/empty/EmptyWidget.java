package yyl.media.controller.empty;

import yyl.media.controller.base.BaseWidget;

/**
 * Created by yuyunlong on 2017/3/8/008.
 */

public class EmptyWidget extends BaseWidget {

    public EmptyWidget() {
        super(null);
    }

    @Override
    public void show(boolean isFullState) {

    }

    @Override
    public void close() {

    }

    @Override
    public void hide(boolean isFullState) {

    }

    @Override
    public void onProgressUpdate() {

    }

    @Override
    public boolean isDragging() {
        return false;
    }

    @Override
    public void eventPlayInit(boolean isOpen) {

    }

    @Override
    public void eventBuffing(int buffingState, boolean isShow) {

    }

    @Override
    public void eventStop(boolean isPlayError) {

    }

    @Override
    public void eventError(int error, boolean show) {

    }

    @Override
    public void changeScreen(boolean isFull) {

    }
}
