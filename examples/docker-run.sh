#!/bin/bash

export OAUTH2_EXTERNAL_URL='http://localhost:9999/auth'
export OAUTH2_BASE_URL='http://keycloak:8080/auth'
export CLIENT_ID='test'
export CLIENT_SECRET='9310a2f5-3625-4833-aa4b-2f38d0840674'
export REALM='test'

set -x

docker run --rm --name java-client \
	-e CLIENT_ID=$CLIENT_ID \
	-e CLIENT_SECRET=$CLIENT_SECRET \
	-e SERVICE_URL='http://172.17.0.1:9090/hi' \
	-e ACCESS_TOKEN_URI="http://172.17.0.1:9999/auth/realms/${REALM}/protocol/openid-connect/token" \
	-it arcanjoqueiroz/oauth2-java-standalone-client-example:1.0.0

docker run --rm --name node-client \
	-e CLIENT_ID=$CLIENT_ID \
	-e CLIENT_SECRET=$CLIENT_SECRET \
	-e REALM=$REALM \
	-e AUTH_SERVER_BASE_URL='http://172.17.0.1:9999/auth' \
	-e USERNAME='alexandre' \
	-e PASSWORD='foo' \
	-it arcanjoqueiroz/oauth2-node-client-example:1.0.0

docker run --rm --name go-client \
	-e CLIENT_ID=$CLIENT_ID \
	-e CLIENT_SECRET=$CLIENT_SECRET \
	-e ACCESS_TOKEN_URI="http://172.17.0.1:9999/auth/realms/${REALM}/protocol/openid-connect/token" \
	-e SERVICE_BASE_URL='http://172.17.0.1:9091' \
	-e USERNAME='alexandre' \
	-e PASSWORD='foo' \
	-it arcanjoqueiroz/oauth2-go-client-example:1.0.0	

docker run --rm --name python-client \
	-e CLIENT_ID=$CLIENT_ID \
	-e CLIENT_SECRET=$CLIENT_SECRET \
	-e ACCESS_TOKEN_URI="http://172.17.0.1:9999/auth/realms/${REALM}/protocol/openid-connect/token" \
	-e SERVICE_BASE_URL='http://172.17.0.1:9091' \
	-it arcanjoqueiroz/oauth2-python-client-example:1.0.0		

docker run --rm --name shell-client-test \
	-e CLIENT_ID=$CLIENT_ID \
	-e CLIENT_SECRET=$CLIENT_SECRET \
	-e REALM=$REALM \
	-e AUTH_SERVER_BASE_URL='http://172.17.0.1:9999/auth' \
	-e USERNAME='alexandre' \
	-e PASSWORD='foo' \
	-it arcanjoqueiroz/oauth2-shell-client-example:1.0.0 sh test.sh

docker run --rm --name shell-client-client-password \
	-e CLIENT_ID=$CLIENT_ID \
	-e CLIENT_SECRET=$CLIENT_SECRET \
	-e REALM=$REALM \
	-e AUTH_SERVER_BASE_URL='http://172.17.0.1:9999/auth' \
	-e USERNAME='alexandre' \
	-e PASSWORD='foo' \
	-e SERVICE_BASE_URL='http://172.17.0.1:9091' \
	-it arcanjoqueiroz/oauth2-shell-client-example:1.0.0 sh client_password.sh

docker run --rm --name shell-client-client-credentials \
	-e CLIENT_ID=$CLIENT_ID \
	-e CLIENT_SECRET=$CLIENT_SECRET \
	-e REALM=$REALM \
	-e AUTH_SERVER_BASE_URL='http://172.17.0.1:9999/auth' \
	-e USERNAME='alexandre' \
	-e PASSWORD='foo' \
	-e SERVICE_BASE_URL='http://172.17.0.1:9091' \
	-it arcanjoqueiroz/oauth2-shell-client-example:1.0.0 sh client_credentials.sh

docker run --rm --name shell-client-ab-client-credentials \
	-e CLIENT_ID=$CLIENT_ID \
	-e CLIENT_SECRET=$CLIENT_SECRET \
	-e REALM=$REALM \
	-e AUTH_SERVER_BASE_URL='http://172.17.0.1:9999/auth' \
	-e USERNAME='alexandre' \
	-e PASSWORD='foo' \
	-e SERVICE_BASE_URL='http://172.17.0.1:9091' \
	-it arcanjoqueiroz/oauth2-shell-client-example:1.0.0 sh ab_client_credentials.sh
