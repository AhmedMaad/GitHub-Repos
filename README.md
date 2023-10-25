# GitHub Repos App

GitHub Repos is an Android application that has a list of public GitHub repositories along with:
+ Repository Name
+ Owner Name
+ Owner Avatar Image
+ Creation Date

The application works in both online and offline modes where the data is cached when the application fetches the data from the server for the first time.

Also, the application preserves the last viewed item in the shown list in case the user exited the application and the process was killed by the system.

## Used Technologies:
+ The application is entirely written in **Kotlin**.
+ Fetching data from the internet using **Retrofit** library.
+ Saving data to the database using **Room** library.
+ Avoiding callbacks using **Coroutines**.
+ Displaying data using a **Recycler View**.
+ Loading images using **Glide** library.
+ Using **MVVM** as an architectural pattern.'
+ Data placeholders are displayed using **Shimmer** library.