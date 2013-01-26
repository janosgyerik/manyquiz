#!/bin/sh

cd $(dirname "$0")

quiz_id=2
db=../../django-quiz/sqlite3.db
sql=../assets/sql_create.sql

../../django-quiz/gen-sql-create.sh > $sql

sqlite3 $db '.dump main_quiz' | grep INSERT >> $sql
sqlite3 $db <<EOF | sed s/table/main_question/ >> $sql
.mode insert
select * from main_question where quiz_id = $quiz_id;
EOF
sqlite3 $db <<EOF | sed s/table/main_answer/ >> $sql
.mode insert
select a.* from main_answer a join main_question q on a.question_id = q.id where quiz_id = $quiz_id;
EOF

cat <<EOF
# How to connect to a sqlite database on Android
# Warning: this works only on Android DEV phones and jailbroken phones
# As a workaround, you can add an activity for debugging purposes which
# copies the database file to /sdcard.
#
$ adb -s emulator-5554 shell
# sqlite3 /data/data/com.example.google.rss.rssexample/databases/rssitems.db
SQLite version 3.3.12
Enter ".help" for instructions
.... enter commands, then quit...
sqlite> .exit 
EOF

# eof
