#!/bin/sh

cd $(dirname "$0")

virtualenv=~/virtualenv/manyquiz/bin/activate
. $virtualenv

proj=$1; shift

if test $# = 0; then
    python manage.py help
else
    python manage.py $* --setting=local_settings.$proj
fi
