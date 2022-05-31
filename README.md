# RunningCompose

Simple app tracking using jetpack compose and room for save data, and services location.

### Instruccions

1. Create or select your ptoject in https://console.cloud.google.com/
2. Enable *Maps SDK for Android* and generate your api key
3. Up your api key in *local.properties* in yout app file, remember name.In this case is *MAPS_API_KEY*

```kotlin
MAPS_API_KEY="your_api_key_xxxx"
```



4. Verify that has this in your *AndroidManifest*

 <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="${MAPS_API_KEY}" />
 
 This is possible for *id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin'* in your file *build.gradle*


### Note 

This project is not complete, as the map compositing library does not have the *snapshot* functions, to take a picture of the tracking.
the image will be replaced by the app logo in the main screen items



## Screenshots
### Splash
<p>
  <img src="https://i.imgur.com/LwWd8D4.png" alt="splash" width="200"/>
</p>

### MainScreen
<p>
  <img src="https://i.imgur.com/bc6aUAR.png" alt="runs" width="200"/>
  <img src="https://i.imgur.com/SZcQ6Ef.png" alt="statistics" width="200"/>
  <img src="https://i.imgur.com/aywclCV.png" alt="settings" width="200"/>
</p>

### TrackingScreen
<p>
  <img src="https://i.imgur.com/85Nl8ou.png" alt="empty tracking" width="200"/>
  <img src="https://i.imgur.com/yPlHZNI.png" alt="init tracking" width="200"/>
  <img src="https://i.imgur.com/8Pep8ps.png" alt="notification tracking" width="200"/>
  <img src="https://i.imgur.com/Vw44TEK.png" alt="cancel tracking" width="200"/>
</p>

### No Empty Screen
<p>
  <img src="https://i.imgur.com/fvIS1V5.png" alt="single-tracking" width="200"/>
  <img src="https://i.imgur.com/0Evl5lv.png" alt="more-trackings" width="200"/>
  <img src="https://i.imgur.com/Of56Tps.png" alt="statistics screens" width="200"/>
  <img src="https://i.imgur.com/0x2EISN.png" alt="details-screen" width="200"/>
</p>


