# Authentication Service

[![Build Status](https://travis-ci.com/arcanjoaq/keycloak-authentication-service.svg?branch=master)](https://travis-ci.com/arcanjoaq/keycloak-authentication-service)

OAuth2/OpenID Connect Authentication Service using JBoss Keycloak 12.0.4 + Legacy Authentication SPI using EJB 3 and JPA 2 + Oracle DB Support.

This is an OAuth 2.0/OpenID Connect Server built using **JBoss Keycloak** and **Oracle 11g**. There is an implementation of a **Service Provider Interface (SPI)** to allow the authentication through legacy user database tables.

The *SPI* example was built using **EJB 3**, **JPA (Hibernate)** and **JBoss Logging**. You can access the source code (Maven) inside *spi* directory.

## Building

To compile the examples, run *build.sh* script:

```sh
./build.sh
```

## Running the example

Inside the directory *examples*, run *create-storage.sh* to create the necessary *Docker Storage*:

```sh
./create-storage.sh
```

And:

```sh
docker-compose up # or docker-compose up oracle liquibase keycloak
```

Keycloak runs on port *9999* and the debug port is *8787*. Oracle XE Database uses default ports *1521* and *8080* (Apex).

*Docker* and *Docker-Compose* are mandatory in order to run the examples in your operating system.

## Administration Console

You can access the *Administration Console* through this [link](http://localhost:9999/auth).

## Licensing

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0.html)
