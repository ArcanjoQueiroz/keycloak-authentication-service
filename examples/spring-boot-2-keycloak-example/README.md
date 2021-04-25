# Spring Boot 2 Keycloak Example

This is an example of Spring Boot 2 and Keycloak integration. In this example you can see the complete Spring Boot 2 configuration and examples including Swagger 2 integration and Authorization using Grant Types.

## Building Keycloak (the Authentication Service)

In the root directory, run the build.sh script:

```sh
$ ./build.sh
```

## Run the infrastructure

In the *examples/docker-compose* directory type:

```sh
$ docker-compose up oracle
```

In order to start the *Oracle XE* Database for this example. Start the *Keycloak* typing:

```sh
$ docker-compose up keycloak
```

Remember that you have to install **docker** and **docker-compose** in your operating system.

## Configure Keycloak

Access the **Keycloak Administration Console** through this [link](http://localhost:9999/auth) with **admin** as username and **admin** as password.

### Create a Realm

The master Realm is the Keycloak default management realm and you **cannot** use this realm for your application. Create a new Realm called *test* clicking on **Add Realm** button.

### Creating a Client ID

In order to create a secured service you have to create a **Client ID**. Select Configure > Clients and click on Create button. 
Fill the following fields:

```
Client ID name: my-client-id
Client Protocol: openid-connect
Access Type: confidential
```

And click on Save button.

Now, with the Client ID created, configure the options:

```
Authorization Enabled: On 
Implicit Flow Enabled: On
Valid Redirect URIs: *
```

Spring Boot OAuth2 needs the JWT Access Token contains the field **authorities** with the Granted Authorities of an user. 
Because of that, it will be necessary to configure this behaviour through **Protocol Mappers**.

In the tab **Mappers**, click on Create button and do the following:

```
Name: authorities
Mapper Type: User Client Role
Client ID: my-client-id
Token claim Name: authorities
Claim JSON Type: String
Multivalued: On
```

### Configuring User Attributes

Select your *Client Id in Clients > [client id]*, then select Mappers tab. In order to create a new *Protocol* Mapper, click on *Create* button.
Type the protocol *Name* and select *User Attribute* in *Mapper Type* field. Now you need to type the user attribute name in User Attribute field and the Token Claim Name along with the *Claim JSON Type field*. See:

```
Name: companyId
Mapper Type: User Attribute
User Attribute: companyId
Token Claim Name: companyId
Claim JSON Type: long
Add to ID token: OFF
Add to access token: ON
Add to userinfo: ON
Multivalued: OFF
Aggregate attribute values: OFF
```

Then click on *Save* button.


### User federation

In order to read the users through the SPI you have to configure the user storage provider through Configure > User Federation. 
Select the **ejb-jpa-user-storage-factory** and click on Save button.

## Run the application

In the *examples/spring-boot-2-keycloak-example* type the following command:

```sh
$ mvn clean install spring-boot:run
```

If you don't have Apache Maven installed replace the mvn command by *mvnw*:

```sh
./mvnw clean install spring-boot:run
```

You can configure *client-id* and *client-secret* this way:

```sh
mvn clean install spring-boot:run -Dapp.oauth2.client-id=service-client-id -Dapp.oauth2.client-secret=3f8fa682-041e-4f41-a263-025b813fb219
```

or:

```sh
APP_OAUTH2_CLIENT_ID=service-client-id APP_OAUTH2_CLIENT_SECRET=3f8fa682-041e-4f41-a263-025b813fb219 mvn clean install spring-boot:run
```

## Importing realm file

You can import all realm configurations through *Manage > Import* option inside Keycloak Admin Console. There is a file in *examples/config/realm-test-export.json* with an example of *Partial Import*.