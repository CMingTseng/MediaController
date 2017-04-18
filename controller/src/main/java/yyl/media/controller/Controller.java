package yyl.media.controller;

import yyl.media.controller.base.BaseController;
import yyl.media.controller.base.BaseMediaPlayer;
import yyl.media.controller.base.BaseWidget;
import yyl.media.controller.base.ConfigurationListener;
import yyl.media.controller.base.MediaListenerEvent;
import yyl.media.controller.empty.EmptyWidget;

/**
 * Created by yuyunlong on 2017/3/8/008.
 */
public class Controller extends BaseController implements MediaListenerEvent {


    public Controller(BaseWidget widget, BaseWidget widgetSmall, BaseWidget widgetFull) {
        super(widget, widgetSmall, widgetFull);
    }


    @Override
    public void eventPlayInit(boolean isOpen) {
        openVideo(isOpen);
        widget.eventPlayInit(isOpen);
        widgetSmall.eventPlayInit(isOpen);
        widgetFull.eventPlayInit(isOpen);
    }

    @Override
    public void eventBuffing(int buffingState, boolean isShow) {
        widget.eventBuffing(buffingState, isShow);
        widgetSmall.eventBuffing(buffingState, isShow);
        widgetFull.eventBuffing(buffingState, isShow);
    }

    @Override
    public void eventStop(boolean isPlayError) {
        if (isPlayError) {
            close();
        }
        widget.eventStop(isPlayError);
        widgetSmall.eventStop(isPlayError);
        widgetFull.eventStop(isPlayError);
    }

    @Override
    public void eventError(int error, boolean showError) {
        if (showError) {
            close();
        }
        widget.eventError(error, showError);
        widgetSmall.eventError(error, showError);
        widgetFull.eventError(error, showError);
    }


    public static final class Builder {
        private BaseWidget widget;
        private BaseWidget widgetSmall;
        private BaseWidget widgetFull;
        private BaseMediaPlayer baseMediaPlayer;

        public Builder() {
            EmptyWidget widgetEmpty = new EmptyWidget();
            this.widget = widgetEmpty;
            this.widgetSmall = widgetEmpty;
            this.widgetFull = widgetEmpty;
        }

        public Builder setWidget(BaseWidget widget) {
            this.widget = widget;
            return this;
        }

        public Builder setWidgetSmall(BaseWidget widgetSmall) {
            this.widgetSmall = widgetSmall;
            return this;
        }

        public Builder setWidgetFull(BaseWidget widgetFull) {
            this.widgetFull = widgetFull;
            return this;
        }

        public Builder setBaseMediaPlayer(BaseMediaPlayer baseMediaPlayer) {
            this.baseMediaPlayer = baseMediaPlayer;
            return this;
        }


        public Controller build() {
            Controller controller = new Controller(widget, widgetSmall, widgetFull);
            baseMediaPlayer.setMediaListenerEvent(controller);
            return controller;
        }
    }

    public static void onAttachActivity(ConfigurationListener activity) {
        screenActivityQueue.push(activity);
    }

    public static void onDetachedActivity(ConfigurationListener activity) {
        screenActivityQueue.remove(activity);
    }

    public static void changeScreenState(boolean isFullState) {
        for (ConfigurationListener screenListener : screenPlayerListeners) {
            screenListener.changeScreen(isFullState);
        }
    }

    public static boolean canBack() {
        if (isFullState()) {
            changeScreenState(false);
            return false;
        }
        return true;
    }
}
