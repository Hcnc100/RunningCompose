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

~~This project is not complete, as the map compositing library does not have the *snapshot* functions, to take a picture of the tracking.
the image will be replaced by the app logo in the main screen items~~

### Update
Use a google map native library, this for take a snapshot of tracking and save this in internal memory, if you see with maps compose library but no save a img tracking see a *map-compose-library* branch



## Screenshots
### Splash
<p>
  <img src="https://i.imgur.com/uvAYldT.png" alt="splash" width="200"/>
</p>

### MainScreen (empty)
<p>
  <img src="https://i.imgur.com/96jya3I.png" alt="runs" width="200"/>
  <img src="https://i.imgur.com/nD59rQ2.png" alt="statistics" width="200"/>
  <img src="https://i.imgur.com/ocAYZmM.png" alt="settings" width="200"/>
</p>

### TrackingScreen
<p>
  <img src="https://i.imgur.com/neddzib.png" alt="init tracking" width="200"/>
  <img src="https://i.imgur.com/e4kl0fR.png" alt="saving tracking" width="200"/>
  <img src="https://i.imgur.com/9qW8GQm.png" alt="cancel tracking" width="200"/>
</p>

### No Empty Screen
<p>
  <img src="https://i.imgur.com/r5w8Lr5.png" alt="more-trackings" width="200"/>
  <img src="https://i.imgur.com/y61UmFD.png" alt="statistics screens" width="200"/>
  <img src="https://i.imgur.com/EEHzLq3.png" alt="details-screen" width="200"/>
</p>


