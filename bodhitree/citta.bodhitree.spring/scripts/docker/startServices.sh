#!/bin/sh

set +x

ret=-1
dockerPrune=false
build=false
rebuild=false
services=""
APP_ROOT=${APP_ROOT:-./}

usage="
Usage:

  env:
    APP_ROOT=./

  [...env] startServices.sh
    -h | --help
    -p | --prune-docker
    -b | --build
    -r | --rebuild
    -s | --services=\"mysql, tree, gateway, ... \"\n
"

while [ "$1" != "" ]; do
    PARAM=`echo $1 | awk -F= '{print $1}'`
    VALUE=`echo $1 | awk -F= '{print $2}'`
    case $PARAM in
        -h | --help)
            printf "$usage"
            exit
            ;;
        -r | --rebuild)
            rebuild=true
            ;;
        -b | --build)
            build=true
            ;;
        -p | --prune-docker)
            dockerPrune=true
            ;;
        -s | --services)
            services=$VALUE
            ;;
        *)
            echo "ERROR: unknown parameter \"$PARAM\""
            printf $usage
            exit 1
            ;;
    esac
    shift
done

if [[ -z $services ]]; then
  printf "$usage"
  exit -1
fi

if [ $dockerPrune = true ]; then
  set -x
  docker system prune --volumes
  set +x
fi

if [ $rebuild = true ]; then
  set -x
  ./gradlew clean
  set +x
fi

if [ $build = true ] || [ $rebuild = true ]; then
  set -x
  ./gradlew docker

  if [ $? -ne 0 ]; then
    exit
  fi

  set +x
fi

set -x
mkdir -p ${APP_ROOT}var
scripts/docker/services.sh "$services"
docker-compose -f ${APP_ROOT}var/docker-compose.yml up
set +x

