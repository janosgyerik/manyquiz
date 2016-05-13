Setup
-----

Common Django setup (only run once, not per project):

    ./setup.sh

Create initial database:

    ./manage-computers.sh syncdb --noinput

Import quiz data from text:

    ./manage-computers.sh importq export/computers.txt

Run Django site on localhost:8000

    ./manage-computers.sh runserver

Visit http://localhost:8000/admin and login with admin/admin


Import questions from a file
----------------------------

The easiest way to import many questions (with answers) in bulk
is to create them in a text file like `sample.txt` and use the
`importq` command like this:

    ./manage-computers.sh importq /path/to/file.txt


Update quiz data in the app based on data in Django
---------------------------------------------------

The steps assume that you are in the project root dir.

Generate SQL scripts in assets:

    ./computers-full/scripts/gen-sql-create.sh

Update the project in Eclipse so it notices the changed file in the assets directory.

Increment `DATABASE_VERSION` in `QuizSQLiteOpenHelper`.

Rebuild and redeploy.


Update quiz data in the project and commit it
---------------------------------------------

Export data in plain text format:

    ./manage-computers.sh exportq > export/computers.txt

Commit and push.


Import quiz data from the project
---------------------------------

Create backup of Django data:

    ./manage-computers.sh exportq > backup/computers.txt

Compare the backup data and the project data in a diff
viewer tool like kdiff3 or gvimdiff, and if necessary merge
the content into a single file

Import data from the project

    ./manage-computers.sh importq export/computers.txt


View stats of quiz data
-----------------------

View a breakdown of # of questions per level:

    ./manage-computers.sh stats --per-level

View a breakdown of # of questions per category:

    ./manage-computers.sh stats --per-category


Misc memo
---------

Initial fixtures file was created with:

    ./manage-computers.sh dumpdata auth.user sites --indent=4 > quiz/fixtures/initial_data.json 

