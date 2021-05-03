#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

# SPI

cd ${SCRIPT_PATH}/spi/ejb-jpa-keycloak-spi && mvn clean install && \
cd ${SCRIPT_PATH}/spi/standalone-keycloak-spi && mvn clean install && \
cd ${SCRIPT_PATH}/clients/oauth2-spring-client-example && mvn clean install dockerfile:build && \
cd ${SCRIPT_PATH}/clients/oauth2-java-standalone-client-example && mvn clean install dockerfile:build  && \
cd ${SCRIPT_PATH}/clients/oauth2-node-client-example && npm install && npm run docker  && \
cd ${SCRIPT_PATH}/clients/oauth2-python-client-example && make docker && \
cd ${SCRIPT_PATH}/clients/oauth2-go-client-example && make build && make docker  && \
cd ${SCRIPT_PATH}/clients/oauth2-shell-client-example && make docker && \
cd ${SCRIPT_PATH}/spring-boot-2-keycloak-example && mvn clean install dockerfile:build

