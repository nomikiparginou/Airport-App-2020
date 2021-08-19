DROP TABLE airline CASCADE CONSTRAINTS;

DROP TABLE booking CASCADE CONSTRAINTS;

DROP TABLE bookingagency CASCADE CONSTRAINTS;

DROP TABLE country CASCADE CONSTRAINTS;

DROP TABLE emailaddress CASCADE CONSTRAINTS;

DROP TABLE faxnumber CASCADE CONSTRAINTS;

DROP TABLE flight CASCADE CONSTRAINTS;

DROP TABLE passenger CASCADE CONSTRAINTS;

DROP TABLE phonenumber CASCADE CONSTRAINTS;

CREATE TABLE airline (
    airlinecode          VARCHAR2(30) NOT NULL,
    country_countryname  VARCHAR2(30) NOT NULL
);

CREATE UNIQUE INDEX airline__idx ON
    airline (
        country_countryname
    ASC );

ALTER TABLE airline ADD CONSTRAINT airline_pk PRIMARY KEY ( airlinecode );

CREATE TABLE booking (
    bookingcode            VARCHAR2(30) NOT NULL,
    bookingdate            DATE NOT NULL,
    seattype               VARCHAR2(30) NOT NULL,
    arrivaldatetime        DATE NOT NULL,
    cost                   NUMBER NOT NULL,
    totalcost              NUMBER NOT NULL,
    seatstatus             VARCHAR2(30) NOT NULL,
    deposit                NUMBER NOT NULL,
    balance                NUMBER NOT NULL,
    passenger_passengerid  VARCHAR2(30) NOT NULL,
    flight_flightcode      VARCHAR2(30) NOT NULL
);

ALTER TABLE booking ADD CONSTRAINT booking_pk PRIMARY KEY ( bookingcode );

CREATE TABLE bookingagency (
    citycode             VARCHAR2(30) NOT NULL,
    cityname             VARCHAR2(30) NOT NULL,
    country_countryname  VARCHAR2(30) NOT NULL
);

ALTER TABLE bookingagency ADD CONSTRAINT bookingagency_pk PRIMARY KEY ( citycode );

CREATE TABLE country (
    countryname          VARCHAR2(30) NOT NULL,
    currency             VARCHAR2(30) NOT NULL,
    exchangerate         NUMBER NOT NULL,
    airline_airlinecode  VARCHAR2(30) NOT NULL,
    tax                  NUMBER NOT NULL
);

CREATE UNIQUE INDEX country__idx ON
    country (
        airline_airlinecode
    ASC );

ALTER TABLE country ADD CONSTRAINT country_pk PRIMARY KEY ( countryname );

CREATE TABLE emailaddress (
    emailaddress           VARCHAR2(30) NOT NULL,
    passenger_passengerid  VARCHAR2(30)
);

ALTER TABLE emailaddress ADD CONSTRAINT emailaddress_pk PRIMARY KEY ( emailaddress );

CREATE TABLE faxnumber (
    faxid                  VARCHAR2(30) NOT NULL,
    faxnum                 VARCHAR2(30),
    passenger_passengerid  VARCHAR2(30)
);

ALTER TABLE faxnumber ADD CONSTRAINT faxnumber_pk PRIMARY KEY ( faxid );

CREATE TABLE flight (
    flightcode           VARCHAR2(30) NOT NULL,
    availability         CHAR(1) NOT NULL,
    classtype            CHAR(1) NOT NULL,
    smokerseat           CHAR(1) NOT NULL,
    depdatetime          DATE NOT NULL,
    flightdur            INTEGER NOT NULL,
    businessseat         INTEGER NOT NULL,
    resbusiness          INTEGER NOT NULL,
    economyseat          INTEGER NOT NULL,
    reseconomy           INTEGER NOT NULL,
    airline_airlinecode  VARCHAR2(30) NOT NULL,
    depcity              VARCHAR2(30) NOT NULL,
    arrcity              VARCHAR2(30) NOT NULL,
    ticketprice          NUMBER NOT NULL
);

ALTER TABLE flight ADD CONSTRAINT flight_pk PRIMARY KEY ( flightcode );

CREATE TABLE passenger (
    passengerid  VARCHAR2(30) NOT NULL,
    name         VARCHAR2(30) NOT NULL,
    surname      VARCHAR2(30) NOT NULL,
    countryname  VARCHAR2(30) NOT NULL,
    address      VARCHAR2(60) NOT NULL
);

ALTER TABLE passenger ADD CONSTRAINT passenger_pk PRIMARY KEY ( passengerid );

CREATE TABLE phonenumber (
    numid                  VARCHAR2(30) NOT NULL,
    phonenum               VARCHAR2(30),
    passenger_passengerid  VARCHAR2(30)
);

ALTER TABLE phonenumber ADD CONSTRAINT phonenumber_pk PRIMARY KEY ( numid );

ALTER TABLE airline
    ADD CONSTRAINT airline_country_fk FOREIGN KEY ( country_countryname )
        REFERENCES country ( countryname );

ALTER TABLE booking
    ADD CONSTRAINT booking_flight_fk FOREIGN KEY ( flight_flightcode )
        REFERENCES flight ( flightcode );

ALTER TABLE booking
    ADD CONSTRAINT booking_passenger_fk FOREIGN KEY ( passenger_passengerid )
        REFERENCES passenger ( passengerid );

ALTER TABLE bookingagency
    ADD CONSTRAINT bookingagency_country_fk FOREIGN KEY ( country_countryname )
        REFERENCES country ( countryname );

ALTER TABLE country
    ADD CONSTRAINT country_airline_fk FOREIGN KEY ( airline_airlinecode )
        REFERENCES airline ( airlinecode );

ALTER TABLE emailaddress
    ADD CONSTRAINT emailaddress_passenger_fk FOREIGN KEY ( passenger_passengerid )
        REFERENCES passenger ( passengerid );

ALTER TABLE faxnumber
    ADD CONSTRAINT faxnumber_passenger_fk FOREIGN KEY ( passenger_passengerid )
        REFERENCES passenger ( passengerid );

ALTER TABLE flight
    ADD CONSTRAINT flight_airline_fk FOREIGN KEY ( airline_airlinecode )
        REFERENCES airline ( airlinecode );

ALTER TABLE phonenumber
    ADD CONSTRAINT phonenumber_passenger_fk FOREIGN KEY ( passenger_passengerid )
        REFERENCES passenger ( passengerid );