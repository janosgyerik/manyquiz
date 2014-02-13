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
Most often you probably want to build specific projects:

    ./gradlew -p IntentsLab/ assembleDebugTest

Or you could build everything in one go:

    ./gradlew assembleDebugTest


Running tests on the command line
---------------------------------
Most often you probably want to run tests in specific projects:

    ./gradlew -p IntentsLab/ connectedInstrumentTest

Or you could run all tests:

    ./gradlew connectedInstrumentTest
