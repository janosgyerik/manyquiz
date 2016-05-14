#!/bin/bash -e
#
# SCRIPT: build.sh
# AUTHOR: Janos Gyerik <info@titan2x.com>
# DATE:   2013-11-11
# REV:    1.0.D (Valid are A, B, D, T and P)
#               (For Alpha, Beta, Dev, Test and Production)
#
# PLATFORM: Not platform dependent
#
# PURPOSE: Build the project in debug or release mode
#
# set -n   # Uncomment to check your syntax, without execution.
#          # NOTE: Do not forget to put the comment back in or
#          #       the shell script will not execute!
# set -x   # Uncomment to debug this shell script (Korn shell only)
#

usage() {
    test $# = 0 || echo $@
    echo "Usage: $0 [OPTION]... [ARG]..."
    echo
    echo Build the project in debug or release mode
    echo
    echo Options:
    echo "  -d, --debug          build for debug, default = $debug"
    echo "  -r, --release        build for release, default = $release"
    echo
    echo "  -b, --build          custom build, default = $build"
    echo
    echo "  -l, --list           list built apks, default = $list"
    echo
    echo "  -h, --help           Print this help"
    echo
    exit 1
}

args=
#arg=
#flag=off
#param=
debug=off
release=off
build=off
list=off
while [ $# != 0 ]; do
    case $1 in
    -h|--help) usage ;;
    -d|--debug) debug=on ;;
    -r|--release) release=on ;;
    -b|--build) build=on ;;
    -l|--list) list=on ;;
    --) shift; while [ $# != 0 ]; do args="$args \"$1\""; shift; done; break ;;
    -) usage "Unknown option: $1" ;;
    -?*) usage "Unknown option: $1" ;;
    *) args="$args \"$1\"" ;;  # script that takes multiple arguments
#    *) test "$arg" && usage || arg=$1 ;;  # strict with excess arguments
#    *) arg=$1 ;;  # forgiving with excess arguments
    esac
    shift
done

eval "set -- $args"  # save arguments in $@. Use "$@" in for loops, not $@

#test $# = 0 && usage


msg() {
    echo '*' $*
}

randstring() {
    md5sum=$(which md5sum 2>/dev/null || which md5)
    POS=2
    LEN=12
    str=$(echo $1 $$ $(date +%S) | $md5sum | $md5sum)
    echo ${str:$POS:$LEN}
}

list() {
    ls -ltr */build/outputs/apk/*-{release,debug*}.apk 2>/dev/null
}

cd $(dirname "$0")/..

gradle=./gradlew

projectname=$(ls *.iml | head -n 1 | sed -e s/.iml$//)

test $build = on || tasks=clean
test $# -gt 0 || tasks="$tasks test"

if test $release = on; then
    check_or_setup_keys=on
    build=on
    tasks="$tasks assembleRelease"
else
    check_or_setup_keys=off
fi

keys_config=./keys/config.sh
if test $check_or_setup_keys = on; then
    test -f $keys_config || {
        mkdir -p keys
        cat<<EOF >$keys_config
#!/bin/sh

export STORE_FILE=$PWD/keys/$projectname.keystore
export STORE_PASSWORD=$(randstring store)
export KEY_ALIAS=mykey
export KEY_PASSWORD=$(randstring key)

# eof
EOF
    }
    test -f keys/$projectname.keystore || {
        . $keys_config
        keytool -genkey -v -keystore keys/$projectname.keystore -storepass $storepass -keypass $keypass -validity 10000 -keyalg RSA
    }
    . $keys_config
fi

if test $debug = on; then
    build=on
    tasks="$tasks assembleDebug"
fi

if test $build = on; then
    echo $gradle $tasks $*
    $gradle $tasks $*
    echo
    list=on
fi

test $list = on && list

# eof
