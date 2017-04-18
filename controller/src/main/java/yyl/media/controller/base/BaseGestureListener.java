package yyl.media.controller.base;

/**
 * Created by yuyunlong on 2017/3/10/010.
 */

public interface BaseGestureListener {

    void onSingleTap();

    void onHorizontalScroll(float percent, boolean fromLeftToRight);

    void onVerticalScroll(float percent, boolean fromUpToDown);
}
