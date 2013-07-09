#!/bin/sh

cd $(dirname "$0")

db=../../django-quiz/databases/moto.db
sql=../assets/sql_create.sql

../../django-quiz/gen-sql-create.sh > $sql

sqlite3 $db '.dump quiz_level' | grep INSERT >> $sql
sqlite3 $db '.dump quiz_question' | grep INSERT >> $sql
sqlite3 $db '.dump quiz_answer' | grep INSERT >> $sql

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
