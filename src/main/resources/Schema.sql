SET search_path TO NOTIFIER;

DROP SCHEMA NOTIFIER CASCADE;
CREATE SCHEMA NOTIFIER;

-- CUSTOMER
CREATE SEQUENCE S_CUSTOMER START WITH 1 INCREMENT BY 1;
CREATE TABLE CUSTOMER(
ID INTEGER DEFAULT NEXTVAL('S_CUSTOMER') PRIMARY KEY,
FIRST_NAME TEXT,
LAST_NAME TEXT,
EMAIL TEXT,
MOBILE TEXT
);

-- BILLING
CREATE SEQUENCE S_BILLING START WITH 1 INCREMENT BY 1;
CREATE TABLE BILLING(
ID INTEGER DEFAULT NEXTVAL ('S_BILLING') PRIMARY KEY ,
AMOUNT NUMERIC ,
CUSTOMER_ID INTEGER REFERENCES CUSTOMER(ID),
DUE_DATE TIMESTAMP ,
NOTIFICATION_STATUS TEXT,
CREATED_DATE TIMESTAMP ,
CHANGED_DATE TIMESTAMP
);

