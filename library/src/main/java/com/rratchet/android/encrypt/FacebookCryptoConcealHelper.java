package com.rratchet.android.encrypt;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

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
enum FacebookCryptoConcealHelper {

    /**
     * Instance facebook crypto conceal helper.
     */
    INSTANCE;

    private FacebookCryptoConcealHelper() {
    }

    private static final String DEFAULT_ENCODE = "utf-8";

    /**
     * The constant PREFIX_E.
     */
    public static final String PREFIX_E = "encrypt_", /**
     * Prefix d facebook crypto conceal helper.
     */
    PREFIX_D = "decrypt_";

    private static SharedPrefsBackedKeyChain msp;
    private static Crypto mCrypto;
    private static Entity mEntity;

    /**
     * Init.
     *
     * @param context the context
     */
    public static void init(Context context) {
        if (null == mCrypto) {
            msp = new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256);
            mCrypto = AndroidConceal.get().createDefaultCrypto(msp);
            String secretKey = SecretKeyHelper.getInstance().obtainSecretKeyValue(context);
            mEntity = Entity.create(secretKey);
        }
    }

    /**
     * 加密字节数组
     *
     * @param plainBytes 原始字节数组
     * @return 密文字节数组 byte [ ]
     */
    public static byte[] encryptByte(byte[] plainBytes) {
        if (!mCrypto.isAvailable()) {
            Log.e("system.out", "encryptByte error: mCrypto is unavailable");
            return null;
        }

        if (null == plainBytes || plainBytes.length <= 0) {
            Log.e("system.out", "encryptByte error: plainBytes is null or length <= 0");
            return null;
        }

        try {
            byte[] result = mCrypto.encrypt(plainBytes, mEntity);
            if (null == result || result.length == 0) {
                Log.e("system.out", "encryptByte error: result is null or length <= 0");
                return null;
            }
            return result;
        } catch (KeyChainException e) {
            e.printStackTrace();
        } catch (CryptoInitializationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密字节数组
     *
     * @param encryptBytes 密文字节数组
     * @return 原始字节数组 byte [ ]
     */
    public static byte[] decryptByte(byte[] encryptBytes) {
        if (!mCrypto.isAvailable()) {
            Log.e("system.out", "decryptByte error: mCrypto is unavailable");
            return null;
        }

        if (null == encryptBytes || encryptBytes.length <= 0) {
            Log.e("system.out", "decryptByte error: encryptBytes is null or length <= 0");
            return null;
        }

        try {
            byte[] result = mCrypto.decrypt(encryptBytes, mEntity);
            if (null == result || result.length == 0) {
                Log.e("system.out", "decryptByte error: result is null or length <= 0");
                return null;
            }
            return result;
        } catch (KeyChainException e) {
            e.printStackTrace();
        } catch (CryptoInitializationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密字符串
     *
     * @param plainText 原始字符串
     * @return 加密后字符串 string
     */
    public static String encryptString(String plainText) {
        if (TextUtils.isEmpty(plainText)) {
            Log.e("system.out", "encryptString error: plainText is empty");
            return null;
        }

        try {
            byte[] plainTextBytes = plainText.getBytes(DEFAULT_ENCODE);
            byte[] result = encryptByte(plainTextBytes);
            if (null == result || result.length <= 0) {
                Log.e("system.out", "encryptString error: encrypt result is null or length <= 0");
                return null;
            }
            String encryptText = Base64.encodeToString(result, Base64.DEFAULT);

            return encryptText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 解密字符串
     *
     * @param encryptText 密文
     * @return 原始字符串 string
     */
    public static String decryptString(String encryptText) {
        if (TextUtils.isEmpty(encryptText)) {
            Log.e("system.out", "decryptString error: encryptText is empty");
            return null;
        }

        byte[] encryptTextBytes = Base64.decode(encryptText, Base64.DEFAULT);
        byte[] data = decryptByte(encryptTextBytes);
        if (null == data || data.length <= 0) {
            Log.e("system.out", "decryptString error: decrypt result is null or length <= 0");
            return null;
        }
        try {
            String plainText = new String(data, DEFAULT_ENCODE);
            return plainText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 加密文件
     *
     * @param sourceFile 源文件
     * @return 加密文件 ，文件名：encrypt_xxx
     */
    public static File encryptFile(File sourceFile) {
        return encryptFile(sourceFile, null);
    }

    /**
     * 加密文件
     *
     * @param sourceFile  源文件
     * @param encryptFile 加密后的文件
     * @return 加密文件 file
     */
    public static File encryptFile(File sourceFile, File encryptFile) {
        if (!mCrypto.isAvailable()) {
            return null;
        }

        if (null == sourceFile) {
            return null;
        }

        if (encryptFile == null) {

            String encryptFilePath = String.format("%s%s%s%s", sourceFile.getParent(), File.separator, PREFIX_E, sourceFile.getName());

            encryptFile = new File(encryptFilePath);
        }

        String originFilePath = sourceFile.getAbsolutePath();

        if (encryptFile.exists()) {
            encryptFile.deleteOnExit();
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(originFilePath);

            OutputStream fileOS = new BufferedOutputStream(new FileOutputStream(encryptFile));
            OutputStream out = mCrypto.getCipherOutputStream(fileOS, mEntity);

            int read = 0;
            byte[] buffer = new byte[1024];
            while ((read = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            out.flush();
            fileOS.flush();
            out.close();
            fileOS.close();

            fileInputStream.close();

            return encryptFile;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CryptoInitializationException e) {
            e.printStackTrace();
        } catch (KeyChainException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密文件
     *
     * @param sourceFile 源文件(加密后的)
     * @return 解密文件, 文件名 ：decrypt_xxx
     */
    public static File decryptFile(File sourceFile) {
        return decryptFile(sourceFile, null);
    }

    /**
     * 解密文件
     *
     * @param sourceFile  源文件(加密后的)
     * @param decryptFile 解密后的文件
     * @return 解密文件\
     */
    public static File decryptFile(File sourceFile, File decryptFile) {
        if (!mCrypto.isAvailable()) {
            return null;
        }

        if (null == sourceFile) {
            return null;
        }


        String originFilePath = sourceFile.getAbsolutePath();

        if (decryptFile == null) {
            String fileName = sourceFile.getName();
            if (fileName.startsWith(PREFIX_E)) {
                fileName = fileName.substring(PREFIX_E.length(), fileName.length());
            }

            String decryptFilePath = String.format("%s%s%s%s", sourceFile.getParent(), File.separator, PREFIX_D, fileName);

            decryptFile = new File(decryptFilePath);
        }

        if (decryptFile.exists()) {
            decryptFile.deleteOnExit();
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(originFilePath);
            InputStream inputStream = mCrypto.getCipherInputStream(fileInputStream, mEntity);

            OutputStream out = new BufferedOutputStream(new FileOutputStream(decryptFile));
            int read = 0;
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            out.flush();
            out.close();

            inputStream.close();
            fileInputStream.close();
            return decryptFile;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CryptoInitializationException e) {
            e.printStackTrace();
        } catch (KeyChainException e) {
            e.printStackTrace();
        }

        return null;
    }
}
