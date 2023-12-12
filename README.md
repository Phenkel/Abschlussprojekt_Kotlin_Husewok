# Husewok - ReadMe

## Description
Husewok is a Kotlin app project developed for the final examination in the Android module of my Mobile App Developer training at the Syntax Institute. The app is designed as a Housework Randomizer with gamification elements. After completing a housework task, the user is required to play a game. Losing the game results in receiving a disliked housework task, while winning rewards the user with a preferred task. The app allows users to edit, add, and delete housework tasks.

## Features
- Housework Randomizer: The app provides a random selection of housework tasks for the user to complete.
- Gamification: After completing a housework task, the user is prompted to play a game. Winning the game rewards the user with a preferred task, while losing results in receiving a disliked task.
- Task Management: Users can edit, add, and delete housework tasks according to their preferences.
- Firebase Integration: The app utilizes Firebase as the backend for data storage and management.

## Future Plans
For future updates of the app, I plan to implement the following features:
- Friend System: Users will be able to connect with friends and share their housework progress.
- Image Editing: Users will have the ability to edit and customize images associated with their housework tasks.

## Libraries Used
The following third-party libraries were used in the development of the app:

- com.wajahatkarim:flippable:1.0.6
- com.github.omkar-tenkale:ExplodingComposable:1.0.1
- com.github.JaberAhamed:CardScratch:1.0
- com.github.esatgozcu:Compose-Rolling-Number:1.0.5
- com.exyte:animated-navigation-bar:1.0.0
- com.github.popovanton0:heart-switch:1.4.0
- com.github.SimformSolutionsPvtLtd:SSJetpackComposeSwipeableView:1.0.1
- com.github.Spikeysanju:MotionToast:1.4

In addition, the following libraries were used for JSON parsing and network communication:

- com.squareup.moshi:moshi-kotlin:1.15.0
- com.squareup.retrofit2:retrofit:2.9.0
- com.squareup.retrofit2:converter-moshi:2.9.0

## APIs Used
The app utilizes the following APIs:

- Official Joke API: This API provides a collection of jokes that can be used in the app to add humor and entertainment.
- Bored API: This API offers a variety of activities to help users find something interesting to do when they are bored.

## Technologies Used
The app is developed using the following technologies:

- Kotlin: Kotlin is the primary programming language used for developing the app. It is a modern, concise, and expressive language that runs on the Java Virtual Machine (JVM).
- Jetpack Compose: Jetpack Compose is a modern UI toolkit for building native Android apps. It allows for declarative UI development, making it easier to create dynamic and interactive user interfaces.
- MVVM (Model-View-ViewModel) Architecture: The app follows the MVVM architectural pattern, which separates the presentation logic from the business logic and data handling. This helps in maintaining a clean and modular codebase.
- Repository Pattern: The app utilizes the repository pattern to abstract the data sources and provide a clean interface for data access. This helps in decoupling the data layer from the rest of the app and improves testability and maintainability.

## Installation
To install and run the Husewok app, follow these steps:

1. Clone the repository from the provided GitHub link.
2. Open the project in Android Studio.
3. Build and run the app on an Android device or emulator.

Make sure you have the necessary dependencies and Android SDK versions installed to successfully build and run the app.

## License

This project is licensed under the Apache License, Version 2.0. For more information about the license, please refer to the [Apache License 2.0](http://www.apache.org/licenses/) website.
