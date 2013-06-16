#!/bin/sh

cd $(dirname "$0")

virtualenv=~/virtualenv/manyquiz/bin/activate
test -f $virtualenv && . $virtualenv

python manage.py $* --setting=local_settings.computers
