#!/bin/bash

REALM=master
CLIENT_ID=service-client-id
CLIENT_SECRET=3f8fa682-041e-4f41-a263-025b813fb219
USERNAME=alexandre
PASSWORD=foo

set -x

# Direct Access Grants Enabled: On
ACCESS_TOKEN=$(curl -s -X POST http://localhost:9999/auth/realms/$REALM/protocol/openid-connect/token \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -H "cache-control: no-cache" \
    -d "client_id=$CLIENT_ID" \
    -d "client_secret=$CLIENT_SECRET" \
    -d "username=$USERNAME" \
    -d "password=$PASSWORD" \
    -d "grant_type=password" | jq '.access_token' | tr -d '"')

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9999/auth/realms/$REALM/protocol/openid-connect/userinfo | jq .

