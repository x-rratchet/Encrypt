package com.rratchet.android.encrypt.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.rratchet.android.encrypt.EncryptHelper;

import java.io.File;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String secretKeyValue = EncryptHelper.getInstance().obtainSecretKeyValue(this);
        Log.d("encrypt", secretKeyValue);

        String value = EncryptHelper.getInstance().encryptString("这是一个测试的数据!");
        Log.d("encrypt", value);

        File originalFile = new File(Environment.getExternalStorageDirectory(), "/test.zip");
        File encryptOriginalFile = new File(Environment.getExternalStorageDirectory(), "/test_encrypt.zip");
        File decryptOriginalFile = new File(Environment.getExternalStorageDirectory(), "/test_decrypt.zip");

        File encryptFile = EncryptHelper.getInstance().encryptFile(originalFile, encryptOriginalFile);
        Log.d("encrypt", encryptFile.getName());

        File decryptFile = EncryptHelper.getInstance().decryptFile(encryptFile, decryptOriginalFile);
        Log.d("encrypt", decryptFile.getName());
    }
}
