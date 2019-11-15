#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

mvn clean install -f ${SCRIPT_PATH}/examples/clients/oauth2-client-example/pom.xml && \
    mvn clean install -f ${SCRIPT_PATH}/examples/spring-boot-2-keycloak-example/pom.xml

mvn clean install -f ${SCRIPT_PATH}/spi/ejb-jpa-keycloak-spi/pom.xml && \
    docker build -t authentication-service:latest ${SCRIPT_PATH}
