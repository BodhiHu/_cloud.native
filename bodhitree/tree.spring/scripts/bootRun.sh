#!/bin/sh

set -x

target=$1
shift
restArgs=$@

# export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_201.jdk/Contents/Home
./gradlew $target:bootRun $restArgs
