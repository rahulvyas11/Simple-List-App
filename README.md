# Simple-List-App
# Fetch Android Take-Home Project

## Overview
This project my submission for Fetch take-home assignment. The application fetches a list of items from a remote server, groups them by list IDs, and displays them in a RecyclerView. Users can expand and collapse groups to view the items within.

## How to Run:
### Clone Repository
To clone the repository, follow these steps:

1. Run the following command:

`git clone https://github.com/rahulvyas11/Simple-List-App.git`

2. Open Android Studio.
3. Click on File -> Open and navigate to the cloned directory.
4. Select the project and click OK.

If while Building you see this message: Could not determine the dependencies of task ':app:compileDebugJavaWithJavac'.
SDK location not found. Please right click on app-> select New -> Resource Bundle -> Name it local and paste the following code adjusting for your username:
`sdk.dir=/Users/username/Android/sdk` (For Mac)
`sdk.dir = C://Users//USERNAME//AppData//Local//Android//sdk` 
And ensure you sync and build the project
To run the app on an Android device or emulator:

1. Open Android Studio: Launch Android Studio and open the project you have cloned.
2. Connect an Android Device: If you have a physical Android device, connect it to your computer via USB and enable USB debugging in the device's developer options.
3. Set Up an Emulator: If you don't have a physical device, you can use an Android emulator. In Android Studio, go to Tools -> AVD Manager and create a new virtual device if you haven't already. Start the emulator by clicking the "Play" button next to your virtual device.
4. Build the Project: Click on the Build menu and select Make Project to ensure everything is set up correctly.
5. Run the App: Click on the green "Run" button in the toolbar.
Select Your Device: Choose your connected device or running emulator from the list and click OK.
The app should now build and deploy to your selected device or emulator.

## Package Structure
## App
### app/src/main/java/com/fetch/androidtakehome
#### Adapter: Contains RecyclerView adapters for displaying items and groups.
1. ListIDAdapter.java
2. ItemNameAdapter.java

#### Model: Contains the data model classes.
1. Item.java

#### Network: Contains classes for network communication using Retrofit.
1. APIClient.java
2. APIService.java
   
#### Repository: Contains the repository classes for data management.
1. ItemRepository.java
2. ItemRepositoryInterface.java
   
#### View: Contains the activity and UI-related classes.
1. MainActivity.java
   
#### ViewModel: Contains the ViewModel classes for managing UI-related data.
1. ItemViewModel.java

#### res/layout: XML files for Views

## Instrumented Tests
### app/src/test/java/com/fetch/androidtakehome/androidtest/UITests 
#### Contains UI test classes using Espresso.
1. ListCardUITest.java
2. NameRowUITest.java

## Unit Tests
### app/src/test/java/com/fetch/androidtakehome/test
#### Contains Unit test classes using JUnit.
Unit Tests for Each Package


