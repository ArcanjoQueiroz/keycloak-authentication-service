#!/bin/bash
if [ -z "${REALM}" ]; then
    REALM=test
fi
if [ -z "${CLIENT_ID}" ]; then
    CLIENT_ID=test
fi
if [ -z "${CLIENT_SECRET}" ]; then
    CLIENT_SECRET=a167e1f1-870d-4926-89d8-738a8d214817
fi
if [ -z "${USERNAME}" ]; then
    USERNAME=alexandre
fi
if [ -z "${PASSWORD}" ]; then
    PASSWORD=foo
fi
if [ -z "${AUTH_SERVER_BASE_URL}" ]; then
    AUTH_SERVER_BASE_URL=http://localhost:9999/auth
fi

set -x

# Direct Access Grants Enabled: On
function getAccessTokenByClientCredentials() {
    OAUTH2_RESPONSE=$(curl -s -X POST ${AUTH_SERVER_BASE_URL}/realms/${REALM}/protocol/openid-connect/token \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -H "cache-control: no-cache" \
        -d "client_id=$CLIENT_ID" \
        -d "client_secret=$CLIENT_SECRET" \
        -d "grant_type=client_credentials")
    ERROR=$(echo $OAUTH2_RESPONSE | jq '.error' | tr -d '"')
    ERROR_DESCRIPTION=$(echo $OAUTH2_RESPONSE | jq '.error_description' | tr -d '"')
    ACCESS_TOKEN=$(echo $OAUTH2_RESPONSE | jq '.access_token' | tr -d '"')
    if [ "$ERROR" != "null" ]; then
	    echo "$ERROR $ERROR_DESCRIPTION"
	    exit 1
    fi    
}

getAccessTokenByClientCredentials

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9091/hi

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9091/consume

