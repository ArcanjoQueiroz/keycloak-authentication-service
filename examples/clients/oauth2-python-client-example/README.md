# OAuth2 Python Client Example

## Run Dependencies

From *examples* directory:

```sh
docker-compose up
cd spring-boot-2-keycloak-example && APP_OAUTH2_CLIENT_ID=service-client-id APP_OAUTH2_CLIENT_SECRET=40a03705-9ea7-476c-be7b-a9a52dde3955 mvn clean install spring-boot:run
cd clients/oauth2-spring-client-example && APP_OAUTH2_CLIENT_ID=service-client-id APP_OAUTH2_CLIENT_SECRET=40a03705-9ea7-476c-be7b-a9a52dde3955 mvn clean install spring-boot:run
```

## Build and running

```sh
cd clients/oauth2-python-client-example 
make virtualenv
source .venv/bin/activate
make install
CLIENT_ID=service-client-id CLIENT_SECRET=40a03705-9ea7-476c-be7b-a9a52dde3955 make run
```