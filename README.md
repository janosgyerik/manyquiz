ManyQuiz
========
A collection of modules to build many quiz applications
with minimum duplication of code and layout elements.


Building and running the Computer Quiz
--------------------------------------
1. Import the following projects in Eclipse:

        * library/ -- common quiz logic and layouts

        * computers/ -- logic and layouts for Computer Quiz

        * computers-lite/ -- the Computer Quiz LITE application

2. Run the Computer Quiz LITE project as Android application


Project layout
--------------
+ library/

    An Android Library project that contains most of the
    functionality and layout elements of the quiz.

+ computers/

    An Android Library project that contains common
    logic in the Computer Quiz.
    Pretty much empty for now.

+ computers-lite/

    An Android Application project that corresponds to the
    Computer Quiz LITE application on Google Play.
    It depends on the "library" and "computers" projects.
    Pretty much empty for now.

+ django-quiz/

    A Django project to make it easier to edit the database
    of questions.


Adding more questions
---------------------
The questions in the Android app are stored in a SQLite database.

The `django-quiz` project is to make editing the database a little
bit easier, but it's still quite complicated.

You can edit the questions and update in the application by
following these steps:

0. Follow the First-time setup steps in `django-quiz/README.txt`

1. Edit the questions in the file `django-quiz/export/computers.txt`

2. Import the questions into Django:

        ./django-quiz/reimport-computers.sh

3. Generate the SQL statements for the `computers-lite` project:

        ./computers-lite/scripts/gen-sql-create.sh

4. Refresh the project in Eclipse or Android Studio

5. Increment QuizSQLiteOpenHelper.DATABASE_VERSION

6. Rebuild and run the project

7. Revert the QuizSQLiteOpenHelper.DATABASE_VERSION change


Creating a new quiz clone
-------------------------
TODO
