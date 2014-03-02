Coursera Android
================
The original assigments from the Android course on Coursera.
No solutions, no spoilers here.

https://www.coursera.org/course/android

Specifically, the exercises from the session that started in January, 2014 (codename: `android-001`).


Importing into Android Studio
-----------------------------
Use the **File | Import Project...** menu to open the `settings.gradle` file.
You can accept all defaults and simply click **OK** everywhere.


Building on the command line
----------------------------
Most of the time you probably want to build a specific project:

    ./gradlew assembleDebugTest -p IntentsLab/

Or you can build everything in one go:

    ./gradlew assembleDebugTest


Running tests on the command line
---------------------------------
Most of the time you probably want to run the Robotium tests in a specific project:

    ./gradlew connectedInstrumentTest -p IntentsLab/

Or you can run all tests:

    ./gradlew connectedInstrumentTest


Staying up to date
------------------
I will keep adding the new assignments as they get released,
ideally on the same day or the next.
Upgrade your local clone of this repository with:

    git fetch origin
    git merge origin/master
