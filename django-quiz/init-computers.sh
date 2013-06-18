#!/bin/sh

cd $(dirname "$0")

virtualenv=~/virtualenv/manyquiz/bin/activate
if test -f $virtualenv; then
    . $virtualenv
    pip install -r requirements.txt
fi

./manage-computers.sh syncdb --noinput
./manage-computers.sh importq export/computers.txt
