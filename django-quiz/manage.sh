#!/bin/sh

cd $(dirname "$0")
. ./virtualenv.sh

proj=$1; shift

if test $# = 0; then
    python manage.py help
else
    python manage.py $* --setting=local_settings.$proj
fi
