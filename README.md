# Cat Breeds App

## Overview

Cat Breeds App is a simple Android application that provides a list of cat breeds along with their details. Users can search for specific cat breeds, mark them as favorites, and view the details of each breed.

## Features

- **List of Cat Breeds:** Display a list of cat breeds fetched from a remote API.
- **Search Functionality:** Allows users to search for specific cat breeds.
- **Favorite Functionality:** Users can mark/unmark breeds as favorites.
- **Detail View:** View detailed information about each cat breed.
- **Offline Support:** Data is cached locally using Room database for offline access.
- **Unit Tests:** Includes unit tests for ViewModel and Repository layers using JUnit and Mockito.

## Technologies Used

- **Kotlin:** The programming language used for development.
- **Jetpack Compose:** Modern toolkit for building native UI.
- **MVVM (Model-View-ViewModel):** Architectural pattern used for separation of concerns.
- **Koin:** Dependency injection library for Kotlin.
- **Retrofit:** Type-safe HTTP client for Android and Java to consume the API.
- **Room:** Persistence library for local database management.
- **Kotlin Coroutines:** For asynchronous programming.
- **JUnit:** For writing unit tests.
- **Mockito:** For mocking dependencies in tests.
- **Truth:** Assertion library for unit tests.
