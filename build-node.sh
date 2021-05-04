#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

cd ${SCRIPT_PATH}/examples/clients/oauth2-node-client-example && npm install && npm run fix && npm run docker
