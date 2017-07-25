package com.rratchet.android.encrypt.app;

import android.app.Application;

import com.rratchet.android.encrypt.EncryptHelper;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-25
 * 版本:      V1.0
 * 描述:      description
 *
 * </pre>
 */

public class AndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EncryptHelper.getInstance().init(this);
    }
}
