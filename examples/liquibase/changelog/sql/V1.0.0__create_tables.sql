--liquibase formatted sql
--changeset arcanjoaq:1.0.0
--comment Creating table legacy tables

CREATE TABLE USERS (
ID          INTEGER       NOT NULL PRIMARY KEY,
USERNAME    VARCHAR(30)   NOT NULL UNIQUE,
FIRST_NAME  VARCHAR(80)   NOT NULL,
LAST_NAME   VARCHAR(80),
EMAIL       VARCHAR(60),
PASSWORD    VARCHAR(30),
COMPANY_ID  INTEGER		  NOT NULL,
BLOCKED     VARCHAR(1)    DEFAULT 'N');

CREATE TABLE GROUPS (
ID          INTEGER       NOT NULL PRIMARY KEY,
NAME        VARCHAR(30)   NOT NULL UNIQUE,
GROUP_ID    VARCHAR(48));

CREATE TABLE USERS_GROUPS (
USER_ID     INTEGER       NOT NULL,
GROUP_ID    INTEGER       NOT NULL,
CONSTRAINT PK_USER_GROUP            PRIMARY KEY (USER_ID, GROUP_ID),
CONSTRAINT FK_USER_GROUP_USER_ID    FOREIGN KEY (USER_ID) REFERENCES USERS (ID),
CONSTRAINT FK_USER_GROUP_GROUP_ID   FOREIGN KEY (GROUP_ID) REFERENCES GROUPS (ID));

CREATE SEQUENCE USERS_SEQ MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;

CREATE SEQUENCE GROUPS_SEQ MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;

INSERT INTO USERS (ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, COMPANY_ID) VALUES (USERS_SEQ.NEXTVAL, 'alexandre', 'Alexandre', 'Queiroz', 'alexandre.queiroz@github.com', 'foo', 1);
INSERT INTO USERS (ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, COMPANY_ID) VALUES (USERS_SEQ.NEXTVAL, 'fernanda', 'Fernanda', 'Souza', 'fernanda.souza@github.com', 'xpto', 1);

INSERT INTO GROUPS (ID, NAME, GROUP_ID) VALUES (GROUPS_SEQ.NEXTVAL, 'Admin', 'admin');
INSERT INTO GROUPS (ID, NAME, GROUP_ID) VALUES (GROUPS_SEQ.NEXTVAL, 'Developers', 'developers');
INSERT INTO GROUPS (ID, NAME, GROUP_ID) VALUES (GROUPS_SEQ.NEXTVAL, 'People', 'people');

INSERT INTO USERS_GROUPS (USER_ID, GROUP_ID) VALUES ((SELECT ID FROM USERS WHERE USERNAME = 'alexandre'), (SELECT ID FROM GROUPS WHERE NAME = 'Admin'));
INSERT INTO USERS_GROUPS (USER_ID, GROUP_ID) VALUES ((SELECT ID FROM USERS WHERE USERNAME = 'alexandre'), (SELECT ID FROM GROUPS WHERE NAME = 'Developers'));
INSERT INTO USERS_GROUPS (USER_ID, GROUP_ID) VALUES ((SELECT ID FROM USERS WHERE USERNAME = 'fernanda'), (SELECT ID FROM GROUPS WHERE NAME = 'People'));
