#!/bin/sh

cd $(dirname "$0")/..

git ls-files --others -i --exclude-standard | grep -v ^tmp/
