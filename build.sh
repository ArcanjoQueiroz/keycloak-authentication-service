#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

${SCRIPT_PATH}/examples/build.sh && \
    docker build -t authentication-service:latest ${SCRIPT_PATH}
