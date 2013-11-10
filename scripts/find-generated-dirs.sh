#!/bin/sh

cd $(dirname "$0")/..

find . \( -name bin -o -name gen -o -name build \)
