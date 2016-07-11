package example.hans.friendlylol;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by hans6 on 10-07-2016.
 */
public class BaseApplication extends Application{

    private static Bus mEventBus;

    public static Bus getEventBus() {
        return mEventBus;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mEventBus = new Bus(ThreadEnforcer.ANY);
    }
}
