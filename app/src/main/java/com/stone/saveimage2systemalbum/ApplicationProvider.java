package com.stone.saveimage2systemalbum;

import android.app.Application;

/**
 * @author stone
 * @date 17/3/29
 */
public interface ApplicationProvider {

    ApplicationProvider IMPL = ApplicationProviderImpl.get();

    /**
     * 初始化Context
     * @param context
     */
    void init(Application context);

    /**
     * 获取Context
     * @return
     */
    Application getApp();

}
