[![](https://jitpack.io/v/sam38124/JzCrashHandler.svg)](https://jitpack.io/#sam38124/JzCrashHandler)
[![Platform](https://img.shields.io/badge/平台-%20Android%20-brightgreen.svg)](https://github.com/sam38124)
[![characteristic](https://img.shields.io/badge/特點-%20輕量級%20%7C%20簡單易用%20%20%7C%20穩定%20-brightgreen.svg)](https://github.com/sam38124)
# JzCustomDebug
此除錯系統必須搭配[JzCrashHandler](https://github.com/sam38124/JzCrashHandler) Api 下去使用，使用方式十分簡單，clone專案後編譯即可使用，你也可以根據你的需求再下去更改，採用Firebase推播技術，所以請勿更改包名，否族會導致app無法使用．

<img src="https://github.com/sam38124/JzCustomDebug/blob/master/i1.png" width = "200"  alt="i1" />  <img src="https://github.com/sam38124/JzCustomDebug/blob/master/i2.png" width = "200"  alt="i2" />  <img src="https://github.com/sam38124/JzCustomDebug/blob/master/i3.png" width = "200"  alt="i3" />

## 目錄
* [如何導入到專案](#Import)
* [快速使用](#Use)
<a name="Import"></a>
### 如何導入到項目
> 支持jcenter。 <br/>

#### jcenter導入方式
在app專案包的build.gradle中添加
```kotlin
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

在需要用到這個庫的module中的build.gradle中的dependencies中加入
```kotlin
dependencies {
    implementation 'com.github.sam38124: JzCrashHandler:2.0'
}
```
<a name="Use"></a>
## 快速使用
### 於Application中添加你的崩潰處理方式 
```kotlin
/*MainActivity::class.java為你要重新起動app的進入點*/
CrashHandle.newInstance(this,MainActivity::class.java).SetUp(CrashHandle.UPLOAD_CRASH_MESSAGE)
```
<a name="About"></a>
### 關於我
橙的電子android and ios developer

*line:sam38124

*gmail:sam38124@gmail.com
