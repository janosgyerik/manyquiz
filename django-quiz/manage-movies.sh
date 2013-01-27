#!/bin/sh

cd $(dirname "$0")

python manage.py $* --setting=local_settings.movies
