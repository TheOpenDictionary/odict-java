#!/usr/bin/env bash

function schema() {
    flatc -j -o src/main/java/org/odict/java schema.fbs
}
$1
#if [ "$#" -eq "0" ]
#    then
#        dependencies
#        clean
#        build
#    else
#        $1
#fi
