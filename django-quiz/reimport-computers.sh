#!/bin/sh

cd $(dirname "$0")

./manage-computers.sh importq export/computers.txt --reset
