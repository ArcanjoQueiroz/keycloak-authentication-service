/* create user */
CREATE USER keycloak IDENTIFIED BY keycloak;

/* grant */
GRANT CREATE TABLE TO keycloak;
GRANT CREATE SEQUENCE TO keycloak;
GRANT CREATE SESSION TO keycloak;

/* create tablespace */
CREATE TABLESPACE keycloak_tbs DATAFILE 'keycloak.dbf' SIZE 500m;

/* set tablespace */
alter user keycloak default tablespace keycloak_tbs;
ALTER USER keycloak QUOTA 500M ON keycloak_tbs;