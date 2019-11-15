#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

mvn clean install -f ${SCRIPT_PATH}/clients/oauth2-spring-client-example/pom.xml && \
    mvn clean install -f ${SCRIPT_PATH}/spring-boot-2-keycloak-example/pom.xml

