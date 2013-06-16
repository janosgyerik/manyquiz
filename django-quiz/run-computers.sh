#!/bin/sh

cd $(dirname "$0")

virtualenv=~/virtualenv/manyquiz/bin/activate
test -f $virtualenv && . $virtualenv

./manage-computers.sh runserver
