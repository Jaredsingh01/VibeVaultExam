# VibeVault 🎶

VibeVault is a simple Android application that allows users to create and manage a personalized playlist of songs. It supports adding song details, viewing a detailed playlist, and calculating average ratings based on user input.

## Features

-  **Add Songs**: Users can input song titles, artist names, ratings (1–5), and personal comments.
-  **View Playlist**: Displays all entered songs in a structured format.
-  **Average Rating**: Calculates and displays the average rating of all added songs.
-  **Exit Functionality**: Allows users to exit back to the main screen.

## Screens

**Logo**

![Screenshot 2025-06-19 132001](https://github.com/user-attachments/assets/061b52f0-a20c-497e-8ace-04871cbb3081)


1. **MainActivity** – Entry point of the app.
 
![Screenshot 2025-06-19 132939](https://github.com/user-attachments/assets/31292244-c982-4a1d-b2df-1f656188c0e8)





2. **MainActivity2** – Interface for adding new songs to the playlist.
   
![Screenshot 2025-06-19 132728](https://github.com/user-attachments/assets/3ffdee2c-882f-4703-bba6-64be6e7da92f)




3. **DetailedView** – Displays the full list of songs with their details and shows the average rating.
   
![Screenshot 2025-06-19 132754](https://github.com/user-attachments/assets/f7ce0f12-526e-45a9-b41f-c1641b588509)

## GitHub Actions
name: Generated APK AAB (Upload - Create Artifact To Github Action)

env:
  # The name of the main module repository
  main_project_module: app

  # The name of the Play Store
  playstore_name: IIECat

on:
#Where release is writen , must match your branch name , I would suggest cloning your branch  at the end called release so it only builds the final code called release
  push:
    branches:
      - 'release'

  # Allows you to run this workflow manually from the Actions tab
  workflow_dispatch:

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3

      # Set Current Date As Env Variable
      - name: Set current date as env variable
        run: echo "date_today=$(date +'%Y-%m-%d')" >> $GITHUB_ENV

      # Set Repository Name As Env Variable
      - name: Set repository name as env variable
        run: echo "repository_name=$(echo '${{ github.repository }}' | awk -F '/' '{print $2}')" >> $GITHUB_ENV

      - name: Set Up JDK
        uses: actions/setup-java@v3
        with:
          distribution: 'zulu' # See 'Supported distributions' for available options
          java-version: '17'
          cache: 'gradle'

      - name: Change wrapper permissions
        run: chmod +x ./gradlew

      # Run Tests Build
      - name: Run gradle tests
        run: ./gradlew test

      # Run Build Project
      - name: Build gradle project
        run: ./gradlew build

      # Create APK Debug
      - name: Build apk debug project (APK) - ${{ env.main_project_module }} module
        run: ./gradlew assembleDebug

      # Create APK Release
      - name: Build apk release project (APK) - ${{ env.main_project_module }} module
        run: ./gradlew assemble

      # Create Bundle AAB Release
      # Noted for main module build [main_project_module]:bundleRelease
      - name: Build app bundle release (AAB) - ${{ env.main_project_module }} module
        run: ./gradlew ${{ env.main_project_module }}:bundleRelease

      # Upload Artifact Build
      # Noted For Output [main_project_module]/build/outputs/apk/debug/
      - name: Upload APK Debug - ${{ env.repository_name }}
        uses: actions/upload-artifact@v3
        with:
          name: ${{ env.date_today }} - ${{ env.playstore_name }} - ${{ env.repository_name }} - APK(s) debug generated
          path: ${{ env.main_project_module }}/build/outputs/apk/debug/

      # Noted For Output [main_project_module]/build/outputs/apk/release/
      - name: Upload APK Release - ${{ env.repository_name }}
        uses: actions/upload-artifact@v3
        with:
          name: ${{ env.date_today }} - ${{ env.playstore_name }} - ${{ env.repository_name }} - APK(s) release generated
          path: ${{ env.main_project_module }}/build/outputs/apk/release/

      # Noted For Output [main_project_module]/build/outputs/bundle/release/
      - name: Upload AAB (App Bundle) Release - ${{ env.repository_name }}
        uses: actions/upload-artifact@v3
        with:
          name: ${{ env.date_today }} - ${{ env.playstore_name }} - ${{ env.repository_name }} - App bundle(s) AAB release generated
          path: ${{ env.main_project_module }}/build/outputs/bundle/release/



## Calculate Average Rating
Calculates and displays the average rating of all songs using a loop-based algorithm. If no ratings are present, a warning message is shown.

## Smooth Navigation
Button to return from the DetailedView screen to the main song entry screen.

Exit button to close the song entry activity.

 Edge-to-Edge Layout
Implements modern Android design with edge-to-edge layout support and proper insets for system bars.

 In-Memory Storage (Temporary)
All data is stored temporarily using private ArrayList variables. Data is lost when the app is closed.


# Usage
1. Launch the App
After building and installing the app on your Android device or emulator:

The app opens to the main screen and sets up an edge-to-edge layout.

Navigation to the song input screen happens automatically.

## 2. Add a New Song
Enter the following details:

🎵 Song Title

🎤 Artist Name

⭐ Rating (only numbers 1–5 allowed)

💬 Comment

Tap the Add Song button.

A success message (Toast) will confirm the addition.

Input fields will automatically clear for the next entry.

⚠️ If any field is left blank or the rating is invalid, an error message will be shown.

## 3. View Playlist
Tap the View Playlist button.

You’ll be taken to a detailed view screen.

Tap Display Songs to see all added songs, including:

Title

Artist

Rating

Comment

## 4. Calculate Average Rating
In the detailed view screen, tap Calculate Average.

The app will compute the average of all ratings using a loop.

The result is shown below the playlist.

⚠️ If there are no ratings, a warning will be displayed and the average won't be shown.

## 5. Navigation & Exit
Tap Return in the Detailed View to go back to the song input screen.

Tap Exit in the song input screen to close the activity.




