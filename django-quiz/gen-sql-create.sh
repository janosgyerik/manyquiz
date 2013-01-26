#!/bin/sh

cd $(dirname "$0")

./manage.py sql main | sed \
    -e 's/PRIMARY KEY/& AUTOINCREMENT/' \
    -e 's/NOT NULL/NULL/' \
    -e '/^BEGIN/d' \
    -e '/^COMMIT/d' \
    -e 's/"id"/"_id"/'

# eof
