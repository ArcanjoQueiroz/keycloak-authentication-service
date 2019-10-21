#!/bin/bash

REALM=test
CLIENT_ID=my-client-id
CLIENT_SECRET=2474ec65-3b82-4af3-a63b-aa782d429241
USERNAME=alexandre
PASSWORD=foo

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

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9090/hi
