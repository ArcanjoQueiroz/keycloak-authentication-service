#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

mvn clean install -f ${SCRIPT_PATH}/spi/ejb-jpa-keycloak-spi/pom.xml && \
    docker build -t authentication-service:latest ${SCRIPT_PATH}
