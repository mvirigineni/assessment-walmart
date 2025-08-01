# Countries Android App

A simple Android application that fetches and displays a list of countries using modern Android development practices.

## Features

- Fetches countries data from JSON API
- Displays countries in a RecyclerView
- Shows country name, region, code, and capital
- Handles loading, error, and empty states
- Supports screen rotation and state restoration

## Architecture

- MVVM architecture pattern
- Repository pattern for data management
- ViewBinding for UI interactions
- Retrofit for network calls
- Coroutines for asynchronous operations

## Technical Stack

- Kotlin programming language
- XML layouts for UI
- Retrofit for API communication
- OkHttp for HTTP client
- RecyclerView for list display
- ViewModel and LiveData for state management

## Project Structure

- data: Country data model
- network: Retrofit API interface and client
- repository: Data repository with error handling
- ui: UIState sealed class for state management
- viewmodel: CountriesViewModel for business logic
- adapter: RecyclerView adapter for country list
- MainActivity: Main activity with ViewBinding

## Error Handling

- Network connectivity issues
- Server errors (5xx)
- Timeout scenarios
- Empty responses
- Malformed JSON data

## Testing

- Unit tests for ViewModel
- MockK for dependency mocking
- Coroutine testing support

## Requirements

- Android API level 24+
- Internet permission
- Kotlin 2.0+
- Android Gradle Plugin 8.11+ s