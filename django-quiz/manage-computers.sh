#!/bin/sh

cd $(dirname "$0")

virtualenv=~/virtualenv/manyquiz/bin/activate
test -f $virtualenv && . ~/virtualenv/manyquiz/bin/activate

python manage.py $* --setting=local_settings.computers
