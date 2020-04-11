#!/bin/bash

REALM=master
CLIENT_ID=service-client-id
CLIENT_SECRET=3f062820-630c-4f61-ad7f-a4137be3fff7
SCOPES=profile

OAUTH2_RESPONSE=$(curl -s -X POST http://localhost:9999/auth/realms/$REALM/protocol/openid-connect/token \
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

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9091/hi

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9091/consume

