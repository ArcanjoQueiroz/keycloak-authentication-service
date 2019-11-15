#!/bin/bash

REALM=test
CLIENT_ID=service-client-id
CLIENT_SECRET=63d07f4e-b461-42db-81fa-0589d3c52fb1
SCOPES=profile

ACCESS_TOKEN=$(curl -s -X POST http://localhost:9999/auth/realms/$REALM/protocol/openid-connect/token \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -H "cache-control: no-cache" \
    -d "client_id=$CLIENT_ID" \
    -d "client_secret=$CLIENT_SECRET" \
    -d "grant_type=client_credentials" | jq '.access_token' | tr -d '"')

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9999/auth/realms/$REALM/protocol/openid-connect/userinfo | jq .

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9091/hi

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9091/consume

