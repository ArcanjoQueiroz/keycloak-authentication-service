#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

cd ${SCRIPT_PATH}/examples/clients/oauth2-go-client-example && make build && make docker
