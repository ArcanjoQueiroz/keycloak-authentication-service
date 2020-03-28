#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

GOLANG_HOME=${HOME}/golang
GOLANG_SRC=${GOLANG_HOME}/src

if [ ! -d ${GOLANG_SRC} ]; then
    mkdir -p ${GOLANG_SRC}
fi
ln -sf ${SCRIPT_PATH} ${GOLANG_SRC}/oauth2-go-client-example
