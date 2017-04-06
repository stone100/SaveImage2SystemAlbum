package com.stone.saveimage2systemalbum;

import android.app.Application;

/**
 * @author stone
 * @date 17/3/29
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationProvider.IMPL.init(this);
    }
}
