package com.rratchet.android.encrypt;

import android.content.Context;

import java.io.File;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-24
 * 版本:      V1.0
 * 描述:      加密助手
 *
 * </pre>
 */
public class EncryptHelper {

    private static volatile EncryptHelper mInstance;

    static {
        System.loadLibrary("encrypt-secret-key");
    }

    private EncryptHelper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EncryptHelper getInstance() {
        if (mInstance == null) {
            synchronized (EncryptHelper.class) {
                if (mInstance == null) {
                    mInstance = new EncryptHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置调试
     *
     * @param isDebug 是否调试
     */
    public native void setDebug(boolean isDebug);

    /**
     * 创建密钥
     *
     * @param context the context
     * @return the 密钥
     */
    public native String createSecretKey(Context context);

    /**
     * 初始化
     *
     * @param context the context
     */
    public void init(Context context) {
        FacebookCryptoConcealHelper.init(context);
    }

    /**
     * 获取密钥值
     *
     * @param context the context
     * @return 密钥值
     */
    public String obtainSecretKeyValue(Context context) {
        return SecretKeyHelper.getInstance().obtainSecretKeyValue(context);
    }

    /**
     * 加密字节数组
     *
     * @param plainBytes 原始字节数组
     * @return 密文字节数组 byte [ ]
     */
    public byte[] encryptByte(byte[] plainBytes) {
        return FacebookCryptoConcealHelper.encryptByte(plainBytes);
    }

    /**
     * 解密字节数组
     *
     * @param encryptBytes 密文字节数组
     * @return 原始字节数组 byte [ ]
     */
    public byte[] decryptByte(byte[] encryptBytes) {
        return FacebookCryptoConcealHelper.decryptByte(encryptBytes);
    }

    /**
     * 加密字符串
     *
     * @param plainText 原始字符串
     * @return 加密后字符串 string
     */
    public String encryptString(String plainText) {
        return FacebookCryptoConcealHelper.encryptString(plainText);
    }

    /**
     * 解密字符串
     *
     * @param encryptText 密文
     * @return 原始字符串 string
     */
    public String decryptString(String encryptText) {
        return FacebookCryptoConcealHelper.decryptString(encryptText);
    }

    /**
     * 加密文件
     *
     * @param sourceFile 源文件
     * @return 加密文件 ，文件名：encrypt_xxx
     */
    public File encryptFile(File sourceFile) {
        return FacebookCryptoConcealHelper.encryptFile(sourceFile);
    }

    /**
     * 加密文件
     *
     * @param sourceFile  源文件
     * @param encryptFile 加密后的文件
     * @return 加密文件 file
     */
    public File encryptFile(File sourceFile, File encryptFile) {
        return FacebookCryptoConcealHelper.encryptFile(sourceFile, encryptFile);
    }

    /**
     * 解密文件
     *
     * @param sourceFile 源文件(加密后的)
     * @return 解密文件, 文件名 ：decrypt_xxx
     */
    public File decryptFile(File sourceFile) {
        return FacebookCryptoConcealHelper.decryptFile(sourceFile);
    }

    /**
     * 解密文件
     *
     * @param sourceFile  源文件(加密后的)
     * @param decryptFile 解密后的文件
     * @return 解密文件\
     */
    public File decryptFile(File sourceFile, File decryptFile) {
        return FacebookCryptoConcealHelper.decryptFile(sourceFile, decryptFile);
    }

}
