# Cinema Now

Cinema Now is an Android app that shows movies now playing in theaters, gets local theaters in the area, and allows users to save movies to their watchlist. 

**[View on the Google Play Store](https://play.google.com/store/apps/details?id=com.allsparkcreative.cinemanow)**

## Development
- Kotlin based app built with Android Jetpack components that gets local movies now playing in theaters with the [The Movie Database](https://www.themoviedb.org/) API
- Connects model components to corresponding XML views with Data Binding
- Accesses the device’s GPS sensor for accurate [MapQuest](https://www.mapquest.com/) API calls
- Executes coroutines to fetch API data asynchronously 
- Saves persistent data locally with Room (SQLite)
- Uses Intents to pull up the dialer app for calling theaters and Google Mpas for turn-by-turn navigation.
- Applied modern Material user interface components from [Material.io](https://material.io/)

