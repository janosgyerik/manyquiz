Setup
-----
1. Create initial database:

        ./manage-computers.sh syncdb --noinput

2. Import quiz data from text:

        ./manage-computers.sh import_questions export/computers.txt

3. Run Django site on localhost:8000

        ./manage-computers.sh runserver

4. Visit http://localhost:8000/admin and login with admin/admin


Import questions from a file
----------------------------
The easiest way to import many questions (with answers) in bulk
is to create them in a text file like `sample.txt` and use the
`import_questions` command like this:

    ./manage-computers.sh import_qustions /path/to/file.txt -n

The `-n` flag is to do a dry-run, check what would be imported
without actually importing anything. If the output looks good,
then re-run the command without the `-n` flag.


Update quiz data in the app based on data in Django
---------------------------------------------------
The steps assume that you are in the project root dir.

1. Generate SQL scripts in assets:

        ./computers-full/scripts/gen-sql-create.sh

2. Update the project in Eclipse so it notices the changed file
   in the assets directory.

3. Increment DATABASE_VERSION in QuizSQLiteOpenHelper

4. Rebuild and redeploy


Update quiz data in the project and commit it
---------------------------------------------
1. Export data in plain text format:

        ./manage-computers.sh export_questions > export/computers.txt

2. Commit and push


Import quiz data from the project
---------------------------------
1. Create backup of Django data

        ./manage-computers.sh export_questions > backup/computers.txt

2. Compare the backup data and the project data in a diff
   viewer tool like kdiff3 or gvimdiff, and if necessary merge
   the content into a single file

3. Import data from the project

        ./manage-computers.sh import_questions export/computers.txt -n


Misc memo
---------
* Initial fixtures file was created with:

        ./manage-computers.sh dumpdata auth.user sites --indent=4 > quiz/fixtures/initial_data.json 

