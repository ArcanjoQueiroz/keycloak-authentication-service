#!/bin/bash
if [ -z "${REALM}" ]; then
    REALM=master
fi
if [ -z "${CLIENT_ID}" ]; then
    CLIENT_ID=service-client-id
fi
if [ -z "${CLIENT_SECRET}" ]; then
    CLIENT_SECRET=3f8fa682-041e-4f41-a263-025b813fb219
fi
if [ -z "${USERNAME}" ]; then
    USERNAME=alexandre
fi
if [ -z "${PASSWORD}" ]; then
    PASSWORD=foo
fi

# Direct Access Grants Enabled: On
ACCESS_TOKEN=$(curl -s -X POST http://localhost:9999/auth/realms/$REALM/protocol/openid-connect/token \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -H "cache-control: no-cache" \
    -d "client_id=$CLIENT_ID" \
    -d "client_secret=$CLIENT_SECRET" \
    -d "username=$USERNAME" \
    -d "password=$PASSWORD" \
    -d "grant_type=password" | jq '.access_token' | tr -d '"')

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9090/hi
