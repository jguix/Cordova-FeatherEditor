# FeatherEditor
A Cordova plugin providing access to the Feather Editor from Creative SDK.

As of today, works only for Android devices.

## Installation

### Cordova project

After creating your cordova project, and adding android platform download the plugin:

    $ cordova plugin add https://github.com/jguix/FeatherEditor
    $ cordova android build

This build won't succeed but you might need to run it to create some files in your Android project.

Download CreativeSDK, unzip and put the creativesdk-repo folder into your cordova's platforms/android directory.

Open the platforms/android project in Android Studio.

### CreativeSDK keys

Add your keys to the plugin file src/android/FeatherApplication.java

    private static final String CREATIVE_SDK_CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "YOUR_CLIENT_SECRET";

### AndroidManifest.xml

Remove the following property to the <manifest> tag
 
    android:smallScreens="true"

Check that the following property is set in the <Application> tag

    android:name="com.jguix.cordova.FeatherApplication" 

Add a provider inside the <Application> tag

    <provider
            android:name="com.aviary.android.feather.sdk.internal.cds.AviaryCdsProvider"
            android:authorities="${applicationId}.AviaryCdsProvider"
            android:exported="false" />
  
Sample complete AndroidManifest.xml
  
    <?xml version='1.0' encoding='utf-8'?>
    <manifest android:hardwareAccelerated="true" android:versionCode="1" android:versionName="0.0.1" package="io.cordova.hellocordova"     xmlns:android="http://schemas.android.com/apk/res/android">
    <!--supports-screens android:anyDensity="true" android:largeScreens="true" android:normalScreens="true" android:resizeable="true"      android:smallScreens="true" android:xlargeScreens="true" /--><!--Changed-->
    <supports-screens android:anyDensity="true" android:largeScreens="true" android:normalScreens="true" android:resizeable="true"     android:xlargeScreens="true" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <application
        android:name="com.jguix.cordova.FeatherApplication"
        android:hardwareAccelerated="true" android:icon="@drawable/icon" android:label="@string/app_name" android:supportsRtl="true">
        <activity android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale" android:label="@string/activity_name" android:launchMode="singleTop" android:name="MainActivity" android:theme="@android:style/Theme.Black.NoTitleBar" android:windowSoftInputMode="adjustResize">
            <intent-filter android:label="@string/launcher_name">
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <provider
            android:name="com.aviary.android.feather.sdk.internal.cds.AviaryCdsProvider"
            android:authorities="${applicationId}.AviaryCdsProvider"
            android:exported="false" /><!--Added-->
    </application>
    <uses-sdk android:minSdkVersion="14" android:targetSdkVersion="22" />
</manifest>

### build.gradle

Modify platforms/android/build.gradle

Add repository for creativesdk at root level

    // Allow plugins to declare Maven dependencies via build-extras.gradle.
    repositories {
      mavenCentral()
      jcenter()
      maven {
        url "${project.rootDir}/creativesdk-repo/release"  //ADD THE CORRECT LOCATION OF THE CREATIVESDK LIBRARY FILES
      }
    }

Specify packaging options at android level

    packagingOptions {
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/LICENSE.txt'
        exclude 'AndroidManifest.xml'
    }
    
Add creativesdk dependencies at root level

    dependencies {
      compile fileTree(dir: 'libs', include: '*.jar')
      // SUB-PROJECT DEPENDENCIES START
      debugCompile project(path: ":CordovaLib", configuration: "debug")
      releaseCompile project(path: ":CordovaLib", configuration: "release")
      // SUB-PROJECT DEPENDENCIES END
      compile 'com.adobe.creativesdk.foundation:auth:0.7.329'
      compile 'com.adobe.creativesdk:image:4.0.0'
    }
    
Gradle sync the project and run on your emulator or device.

## Roadmap

* Automate all manual changes above in the plugin.xml file of the plugin
* Create a call to sign with CreativeSDK keys and put them out of the FeatherApplication.java file
* Add configuration options to the Feather Editor
* Add iOS platform for the plugin (any help would be appreciated)
