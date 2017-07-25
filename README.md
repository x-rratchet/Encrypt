# `Encrypt`

## 概述 

 - Android平台加解密
 - 支持字符串、文件加解密
 - 依赖Facebook的Conceal

## 如何使用

### Step 1. 添加 `JitPack` 仓库到你的 `build` 文件

在你的项目根目录下的 `build.gradle` 添加仓库：

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}	
```

### Step 2. 添加 `Encrypt` 的引用

```
dependencies {
	compile 'com.facebook.conceal:conceal:1.1.3@aar'
	compile 'com.github.RRatChet:Encrypt:0.0.1'
}
```

## 使用方法

### 1 . 在项目的Application的onCreate()函数中初始化

```java
public class AndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EncryptHelper.getInstance().init(this);
    }
}
```

### 2 . 获取加密密钥

```java
String secretKeyValue = EncryptHelper.getInstance().obtainSecretKeyValue(this);
Log.d("encrypt", secretKeyValue);
```

### 3 . 加密字符串

```java
String value = EncryptHelper.getInstance().encryptString("这是一个测试的数据!");
Log.d("encrypt", value);
```

### 4 . 加解密文件

```java
File originalFile = new File(Environment.getExternalStorageDirectory(), "/test.zip");
// 加密后的文件
File encryptOriginalFile = new File(Environment.getExternalStorageDirectory(), "/test_encrypt.zip");
// 解密后的文件
File decryptOriginalFile = new File(Environment.getExternalStorageDirectory(), "/test_decrypt.zip");

File encryptFile = EncryptHelper.getInstance().encryptFile(originalFile, encryptOriginalFile);
Log.d("encrypt", encryptFile.getName());

File decryptFile = EncryptHelper.getInstance().decryptFile(encryptFile, decryptOriginalFile);
Log.d("encrypt", decryptFile.getName());
```







