#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

cd ${SCRIPT_PATH}/examples/spi/ejb-jpa-keycloak-spi && mvn clean install && \
cd ${SCRIPT_PATH}/examples/spi/standalone-keycloak-spi && mvn clean install && \
cd ${SCRIPT_PATH}/examples/clients/oauth2-spring-client-example && mvn clean install dockerfile:build && \
cd ${SCRIPT_PATH}/examples/clients/oauth2-java-standalone-client-example && mvn clean install dockerfile:build  && \
docker build -t authentication-service:latest ${SCRIPT_PATH}
