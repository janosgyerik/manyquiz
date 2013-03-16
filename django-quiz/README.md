Setup
-----
1. Create initial database:

        ./manage-computers.sh syncdb --noinput

2. Import quiz data from JSON:

        ./manage-computers.sh loaddata json/computers.json

3. Run Django site on localhost:8000

        ./manage-computers.sh runserver

4. Visit http://localhost:8000/admin and login with admin/admin


Update Quiz data
----------------

    ./manage-computers.sh dumpdata quiz --indent=4 > json/computers.json


Misc memo
---------
* Create initial fixtures:

        ./manage-computers.sh dumpdata auth.user sites --indent=4 > quiz/fixtures/initial_data.json 

* Import old quiz data

        ./manage-computers.sh import_oldquiz /path/to/questions.txt

