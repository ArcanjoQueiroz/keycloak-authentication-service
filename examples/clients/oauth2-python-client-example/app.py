#!/usr/bin/env python3.8
# -*- coding: utf-8 -*-
from oauthlib.oauth2 import BackendApplicationClient
from requests_oauthlib import OAuth2Session
import os
import json

os.environ['OAUTHLIB_INSECURE_TRANSPORT'] = '1'

client_id=os.getenv('CLIENT_ID', 'test')
client_secret=os.getenv('CLIENT_SECRET', 'a167e1f1-870d-4926-89d8-738a8d214817')
access_token_uri=os.getenv('ACCESS_TOKEN_URI', 'http://localhost:9999/auth/realms/test/protocol/openid-connect/token')
service_base_url=os.getenv('SERVICE_BASE_URL', 'http://localhost:9091')
scopes=['profile']

class OAuth2Client:
    def __init__(self, client_id='', client_secret='', access_token_uri='', scopes=[], username=None, password=None):
        self.client_id = client_id
        self.client_secret = client_secret
        self.access_token_uri = access_token_uri
        self.scopes = scopes
        self.client = BackendApplicationClient(client_id=client_id)
        self.oauth = OAuth2Session(client=self.client)
        self.username = username
        self.password = password

    def fetch_token(self):
        self.token = self.oauth.fetch_token(token_url=self.access_token_uri,
            client_id=self.client_id,
            client_secret=self.client_secret,
            username=self.username,
            password=self.password,
            scopes=self.scopes)
        return self

    def get_session(self):
        return self.oauth


def print_response(response):
    if response.status_code == 200:
        message = json.loads(response.content)
        print('Message: ' + message['message'])
    else:
        print('Error: ' + response.content)

if __name__ == '__main__':
    oauth2_client = OAuth2Client(
        client_id=client_id,
        client_secret=client_secret,
        access_token_uri=access_token_uri,
        scopes=scopes)
    session = oauth2_client.fetch_token().get_session()
    print(f"Client ID: {session.client_id}")
    print(f"Access Token: {session.access_token}")

    response = session.get(f"{service_base_url}/consume")
    print_response(response)

    response = session.get(f"{service_base_url}/hi")
    print_response(response)


