#!/usr/bin/env python3.7
from oauthlib.oauth2 import BackendApplicationClient
from requests_oauthlib import OAuth2Session
import os
import json

os.environ['OAUTHLIB_INSECURE_TRANSPORT'] = '1'

client_id='service-client-id'
client_secret='3f8fa682-041e-4f41-a263-025b813fb219'
access_token_uri='http://localhost:9999/auth/realms/master/protocol/openid-connect/token'
scopes=['profile']

class OAuth2Client:
    def __init__(self, client_id='', client_secret='', access_token_uri='', scopes=[]):
        self.client_id = client_id
        self.client_secret = client_secret
        self.access_token_uri = access_token_uri
        self.scopes = scopes
        self.client = BackendApplicationClient(client_id=client_id)
        self.oauth = OAuth2Session(client=self.client)

    def fetch_token(self):
        self.token = self.oauth.fetch_token(token_url=self.access_token_uri,
            client_id=self.client_id,
            client_secret=self.client_secret,
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

    response = session.get('http://localhost:9091/consume')
    print_response(response)

    response = session.get('http://localhost:9091/hi')
    print_response(response)


