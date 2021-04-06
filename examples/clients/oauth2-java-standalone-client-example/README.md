# OAuth2 Java Standalone Client Example

## Run Dependencies

From *examples* directory:

```sh
docker-compose up
cd spring-boot-2-keycloak-example && APP_OAUTH2_CLIENT_ID=service-client-id APP_OAUTH2_CLIENT_SECRET=40a03705-9ea7-476c-be7b-a9a52dde3955 mvn clean install spring-boot:run
cd clients/oauth2-spring-client-example && APP_OAUTH2_CLIENT_ID=service-client-id APP_OAUTH2_CLIENT_SECRET=40a03705-9ea7-476c-be7b-a9a52dde3955 mvn clean install spring-boot:run
```

## Build and running

```sh
cd clients/oauth2-java-standalone-client-example 
mvn clean install
java -jar -Dclient_id=service-client-id -Dclient_secret=40a03705-9ea7-476c-be7b-a9a52dde3955 target/oauth2-java-standalone-client-example-1.0.0-SNAPSHOT-jar-with-dependencies.jar 
```