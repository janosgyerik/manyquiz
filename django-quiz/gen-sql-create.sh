#!/bin/sh

cd $(dirname "$0")

# note: the schema is the same for all quiz
./manage-computers.sh sql quiz | sed \
    -e 's/PRIMARY KEY/& AUTOINCREMENT/' \
    -e 's/NOT NULL/NULL/' \
    -e '/^BEGIN/d' \
    -e '/^COMMIT/d' \
    -e 's/"id"/"_id"/'

# eof
