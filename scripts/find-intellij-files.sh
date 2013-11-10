#!/bin/sh

cd $(dirname "$0")/..

find . \( -name .idea -o -name \*.iml -o -name gradle -o -name gradlew -o -name gradlew.bat \) | grep -v -e ^./tmp/ -e ^./.git/
