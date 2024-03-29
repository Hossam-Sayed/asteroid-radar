<div align=center>
<picture> <img width="200" src=https://github.com/Hossam-Sayed/asteroid-radar/blob/main/app/src/main/res/drawable-v24/ic_asteroid.png /> </picture>
</div>

# Asteroid Radar <img width="36" src=https://github.com/Hossam-Sayed/asteroid-radar/assets/83096913/744c650b-9614-462f-a579-7562314e5019 />
The Asteroid Radar app keeps track of the asteroids coming near our beloved Earth 🌍

## Description

Asteroid Radar is an innovative Android application, meticulously crafted in Kotlin, that empowers users to monitor near-Earth asteroids with precision. Utilizing the robust Retrofit library, it establishes seamless communication with NASA’s API to retrieve real-time asteroid data. The app’s backbone is the Room database, which stores the API’s data and features a dynamic ‘Image of the Day’—a daily-refreshed visual treat from the cosmos.

Engineered with the modern MVVM architecture and repository pattern, Asteroid Radar ensures optimal performance through a single-activity-multiple-fragments approach. It harnesses Kotlin coroutines for efficient asynchronous operations, while LiveData and MutableLiveData orchestrate a responsive user experience by observing and updating UI components in real time.

Data binding and binding adapters are integrated for fluid interaction between the app’s data and visual elements, complemented by a high-performance RecyclerView implementation. Furthermore, the CoroutineWorker is adeptly employed to manage background events and schedule overnight tasks—such as updating the database with fresh data—ensuring the app remains up-to-date, even as users slumber.

As a testament to its quality, Asteroid Radar has been reviewed by Google and successfully passed the assessment in Udacity’s Advanced Android Kotlin Nanodegree—a program designed and taught by industry leaders.

Key Features:

- Real-time asteroid tracking
- Daily updated cosmic imagery
- Seamless NASA API integration
- Persistent data storage
- Advanced MVVM with repository pattern
- Single-activity architecture for enhanced performance
- Asynchronous processing with Kotlin coroutines
- Data binding for streamlined UI interactions
- Background task scheduling with CoroutineWorker

Asteroid Radar is not just an app; it’s a celestial gateway, bringing the universe to your fingertips while setting a new standard for Android development with its cutting-edge features and performance.
