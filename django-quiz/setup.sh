#!/bin/bash

cd $(dirname "$0")

virtualenv=~/virtualenv/manyquiz
if ! test -d $virtualenv; then
    virtualenv --distribute $virtualenv
    . $virtualenv/bin/activate
    pip install -r requirements.txt
fi
