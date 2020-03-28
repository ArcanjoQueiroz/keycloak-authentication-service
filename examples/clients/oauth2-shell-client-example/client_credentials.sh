#!/bin/bash

REALM=master
CLIENT_ID=service-client-id
CLIENT_SECRET=3f8fa682-041e-4f41-a263-025b813fb219
SCOPES=profile

ACCESS_TOKEN=$(curl -s -X POST http://localhost:9999/auth/realms/$REALM/protocol/openid-connect/token \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -H "cache-control: no-cache" \
    -d "client_id=$CLIENT_ID" \
    -d "client_secret=$CLIENT_SECRET" \
    -d "grant_type=client_credentials" | jq '.access_token' | tr -d '"')

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9091/hi

curl -s -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:9091/consume

