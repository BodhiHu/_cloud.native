#!/bin/bash

services=$1

MYSQL_SERVICE="#mysql: disabled"
GATEWAY_SERVICE="#gateway: disabled"
ADMIN_SERVICE="#admin: disabled"
AUTH_SERVICE="#auth: disabled"
TREE_SERVICE="#tree: disabled"
SPROUT_SERVICE="#sprout: disabled"

if [[ $services =~ 'mysql' ]]; then
read -d '' MYSQL_SERVICE << EOF
  mysql:
    image: mysql:8
    container_name: 'bodhitree.mysql'
    restart: "no"
    environment:
      MYSQL_DATABASE: 'BodhiTree'
      MYSQL_USER: 'bodhi'
      MYSQL_PASSWORD: 'BodhiTree123'
      MYSQL_ROOT_PASSWORD: 'BodhiTreeDB_RootPWD'
    #network_mode: "host"
    network_mode: "bridge"
    ports:
      - 3306:3306
      - 33060:33060
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "10"
EOF
fi

if [[ $services =~ 'gateway' ]]; then
read -d '' GATEWAY_SERVICE << EOF
  gateway:
    image: bodhitree/gateway
    container_name: 'bodhitree.gateway'
    restart: "no"
    depends_on:
      - auth
      - tree
    network_mode: "host"
    ports:
      - 8600:8600
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "10"
EOF
fi

if [[ $services =~ 'admin' ]]; then
read -d '' ADMIN_SERVICE << EOF
  admin:
    image: bodhitree/admin
    container_name: 'bodhitree.admin'
    restart: "no"
    depends_on:
      - tree
    network_mode: "host"
    ports:
      - 9110:9110
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "10"
EOF
fi

if [[ $services =~ 'auth' ]]; then
read -d '' AUTH_SERVICE << EOF
  auth:
    image: bodhitree/auth
    container_name: 'bodhitree.auth'
    restart: "no"
    depends_on:
      - mysql
    network_mode: "host"
    ports:
      - 8601:8601
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "10"
EOF
fi

if [[ $services =~ 'tree' ]]; then
read -d '' TREE_SERVICE << EOF
  tree:
    image: bodhitree/tree
    container_name: 'bodhitree.tree'
    restart: "no"
    depends_on:
      - mysql
    network_mode: "host"
    ports:
      - 8602:8602
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "10"
EOF
fi

if [[ $services =~ 'sprout' ]]; then
read -d '' SPROUT_SERVICE << EOF
  sprout:
    image: bodhitree/sprout:0.0.1
    container_name: 'bodhitree.sprout'
    build:
      context: ../../../Sprout
      dockerfile: scripts/docker/Dockerfile
    restart: "no"
    network_mode: "host"
    depends_on:
      - tree
      #- auth
      #- gateway
    ports:
      - 8666:8666
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "10"
EOF
fi

DOCKER_CMOPOSE_SERVICES=`printf "
# BodhiTree docker-compose

version: '3.3'
services:
  $MYSQL_SERVICE
  $GATEWAY_SERVICE
  $ADMIN_SERVICE
  $AUTH_SERVICE
  $TREE_SERVICE
  $SPROUT_SERVICE

"`

printf "\n$DOCKER_CMOPOSE_SERVICES\n\n" > "${APP_ROOT}var/docker-compose.yml"

cat ${APP_ROOT}var/docker-compose.yml

