#!/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")


function build_keycloak() {
	cd ${SCRIPT_PATH}/examples/spi/ejb-jpa-keycloak-spi && \
		mvn clean install && \
	cd ${SCRIPT_PATH}/examples/spi/standalone-keycloak-spi && \
		mvn clean install && \
	cd ${SCRIPT_PATH} && \
		docker build -t authentication-service:latest ${SCRIPT_PATH}	
}

function build_app() {
	cd ${SCRIPT_PATH}/examples/spring-boot-2-keycloak-example && \
	mvn clean install dockerfile:build
}

function build_clients() {
	cd ${SCRIPT_PATH}/examples/clients/oauth2-spring-client-example && \
		mvn clean install dockerfile:build && \
	cd ${SCRIPT_PATH}/examples/clients/oauth2-java-standalone-client-example && \
		mvn clean install dockerfile:build  && \
	cd ${SCRIPT_PATH}/examples/clients/oauth2-node-client-example && \
		npm install && npm run docker  && \
	cd ${SCRIPT_PATH}/examples/clients/oauth2-python-client-example && \
		make docker && \
	cd ${SCRIPT_PATH}/examples/clients/oauth2-go-client-example && \
		make build && make docker  && \
	cd ${SCRIPT_PATH}/examples/clients/oauth2-shell-client-example && \
		make docker
}

build_keycloak
build_app
build_clients
