#NIT3213 Final Assignment

An Android application built using MVVM architecture, Hilt for dependency injection, and Retrofit for API communication.

##Installation
Clone the repository:
Open the project in Android Studio.
Build the project to download all dependencies.
Run the app on an emulator or physical device.

##Usage
###LoginActivity
Allows user authentication.
Handles the following scenarios:
Displays an error if the username or password is empty.
Shows a message for invalid credentials.
Displays a warning when no internet connection is available.
###DashboardActivity
Displays a list of items fetched from a REST API after a successful login.
###DetailsActivity
Shows detailed information for a selected item from the dashboard.

##Project Structure
com.project.myapplication
│
├── di/             # Hilt modules
├── models/         # Data models (e.g., LoginRequest, LoginResponse)
├── network/        # Retrofit API interface
├── repository/     # Data access and logic
├── ui/             # Activities and layouts
├── util/           # Utility classes (e.g., Result sealed class)
└── viewmodel/      # ViewModels for business logic

##Architecture
The app follows the MVVM (Model-View-ViewModel) architecture pattern with Hilt for dependency injection:
###Model: Handles data operations and API interaction.
###View (UI Layer):
Activities and layouts display data.
Observes LiveData from ViewModel.
###ViewModel:
Contains UI logic and exposes data via LiveData.
Communicates with repositories.
###Repository:
Abstracts data sources and communicates with ApiService.
Wraps responses in a sealed Result class.
###Network Layer:
Retrofit interface for making HTTP requests.
###Hilt DI:
Injects dependencies into ViewModels and repositories automatically.

##API Call Flow
LoginActivity → calls loginViewModel.login()
LoginViewModel → calls repository.login()
LoginRepository → performs API call via Retrofit
Result is posted as LiveData<Result<LoginResponse>>

##Dependencies
Retrofit – For network requests
Hilt – For dependency injection
LiveData – For reactive UI updates
ViewModel – For managing UI-related data
RecyclerView – For displaying lists of items

##Summary
This project showcases best practices in Android development including:
Clean MVVM separation
Network communication with error handling
Scalable architecture with DI
User-friendly feedback for login and data operations
