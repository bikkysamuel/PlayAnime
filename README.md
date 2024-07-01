# PlayAnime
PlayAnime is an Android app created using Kotlin, Jetpack Compose, MVVM, Dagger-Hilt, Room, Retrofit and Coil libraries.

App allows users to watch and download anime videos. The app fetches HTML data from an existing website and parses it to display content on the user's device. Users can watch videos directly within the app or download them using a browser or any download manager app of their choice.

## Development
- Jetpack Compose and MVVM Architecture Pattern
- Dagger-Hilt for dependency injection
- Room database for local data storage for the saved anime
- DataStore for local app settings value.
- Network calls using Retrofit

## Features
- Watch anime videos directly within the app.
- Download anime videos for offline viewing using external download manager apps.
- Add anime to favorites list for easy access.
- Utilizes Room database to store local data.
- DataStore used for storing settings data such as theme and font style.

## Setup

1. Download or clone the repository.
2. Open the project in Android Studio.
3. Build and run the APK on your Android device or emulator.

## Download
- [Debug version](https://github.com/bikkysamuel/PlayAnime/releases/download/debug/PlayAnime-debug.apk) is currently available for download.
- Future release will have different signature and needs to be freshly installed. i.e., uninstall debug version and install the new release version.

## Note
PlayAnime does not host or own any of the videos or content obtained from the website data. It functions as an aggregator, fetching data from existing websites for user convenience.

## Disclaimer
PlayAnime is provided as-is and the developers make no guarantees regarding its functionality or the availability of content. Use at your own discretion.
