package com.rratchet.android.encrypt;

import android.content.Context;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-24
 * 版本:      V1.0
 * 描述:      description
 *
 * </pre>
 */

public class SecretKeyHelper {


    private static volatile SecretKeyHelper mInstance;
    private String secretKeyValue = null;

    private SecretKeyHelper() {
    }

    public static SecretKeyHelper getInstance() {
        if (mInstance == null) {
            synchronized (SecretKeyHelper.class) {
                if (mInstance == null) {
                    mInstance = new SecretKeyHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取密钥值
     *
     * @param context the context
     * @return the string
     */
    public String obtainSecretKeyValue(Context context) {

        if (secretKeyValue == null || "".equals(secretKeyValue)) {
            secretKeyValue = EncryptHelper.getInstance().createSecretKey(context);
        }
        return secretKeyValue;
    }
}
