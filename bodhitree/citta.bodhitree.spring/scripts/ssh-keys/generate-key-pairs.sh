#!/bin/bash

# see https://gist.github.com/destan/b708d11bd4f403506d6d5bb5fe6a82c5#file-generatekeypair-sh

keysPath=$1

if [ -z $keysPath ]; then
  keysPath="tree/src/main/resources/ssh-keys/dev"
fi

printf "Keys will be generated under: $keysPath\n\n"

openssl genrsa -out $keysPath/private_key.pem 4096
openssl rsa -pubout -in $keysPath/private_key.pem -out $keysPath/public_key.pem

# convert private key to pkcs8 format in order to import it from Java
openssl pkcs8 -topk8 -in $keysPath/private_key.pem -inform pem -out $keysPath/private_key_pkcs8.pem -outform pem -nocrypt

