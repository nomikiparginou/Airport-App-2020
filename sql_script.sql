DROP TABLE IF EXISTS airline;

DROP TABLE IF EXISTS booking;

DROP TABLE IF EXISTS bookingagency;

DROP TABLE IF EXISTS country;

DROP TABLE IF EXISTS emailaddress;

DROP TABLE IF EXISTS faxnumber;

DROP TABLE IF EXISTS flight;

DROP TABLE IF EXISTS passenger;

DROP TABLE IF EXISTS phonenumber;

CREATE TABLE airline (
    airlinecode          VARCHAR(30) NOT NULL,
    country_countryname  VARCHAR(30) NOT NULL
);

CREATE UNIQUE INDEX airline__idx ON
    airline (
        country_countryname
    ASC );

ALTER TABLE airline ADD CONSTRAINT airline_pk PRIMARY KEY ( airlinecode );

CREATE TABLE booking (
    bookingcode            VARCHAR(30) NOT NULL,
    bookingdate            DATETIME NOT NULL,
    seattype               VARCHAR(30) NOT NULL,
    arrivaldatetime        DATETIME NOT NULL,
    cost                   DOUBLE NOT NULL,
    totalcost              DOUBLE NOT NULL,
    seatstatus             VARCHAR(30) NOT NULL,
    deposit                DOUBLE NOT NULL,
    balance                DOUBLE NOT NULL,
    passenger_passengerid  VARCHAR(30) NOT NULL,
    flight_flightcode      VARCHAR(30) NOT NULL
);

ALTER TABLE booking ADD CONSTRAINT booking_pk PRIMARY KEY ( bookingcode );

CREATE TABLE bookingagency (
    citycode             VARCHAR(30) NOT NULL,
    cityname             VARCHAR(30) NOT NULL,
    country_countryname  VARCHAR(30) NOT NULL
);

ALTER TABLE bookingagency ADD CONSTRAINT bookingagency_pk PRIMARY KEY ( citycode );

CREATE TABLE country (
    countryname          VARCHAR(30) NOT NULL,
    currency             VARCHAR(30) NOT NULL,
    exchangerate         DOUBLE NOT NULL,
    airline_airlinecode  VARCHAR(30) NOT NULL,
    tax                  DOUBLE NOT NULL
);

CREATE UNIQUE INDEX country__idx ON
    country (
        airline_airlinecode
    ASC );

ALTER TABLE country ADD CONSTRAINT country_pk PRIMARY KEY ( countryname );

CREATE TABLE emailaddress (
    emailaddress           VARCHAR(30) NOT NULL,
    passenger_passengerid  VARCHAR(30)
);

ALTER TABLE emailaddress ADD CONSTRAINT emailaddress_pk PRIMARY KEY ( emailaddress );

CREATE TABLE faxnumber (
    faxid                  VARCHAR(30) NOT NULL,
    faxnum                 VARCHAR(30),
    passenger_passengerid  VARCHAR(30)
);

ALTER TABLE faxnumber ADD CONSTRAINT faxnumber_pk PRIMARY KEY ( faxid );

CREATE TABLE flight (
    flightcode           VARCHAR(30) NOT NULL,
    availability         CHAR(1) NOT NULL,
    classtype            CHAR(1) NOT NULL,
    smokerseat           CHAR(1) NOT NULL,
    depdatetime          DATETIME NOT NULL,
    flightdur            INTEGER NOT NULL,
    businessseat         INTEGER NOT NULL,
    resbusiness          INTEGER NOT NULL,
    economyseat          INTEGER NOT NULL,
    reseconomy           INTEGER NOT NULL,
    airline_airlinecode  VARCHAR(30) NOT NULL,
    depcity              VARCHAR(30) NOT NULL,
    arrcity              VARCHAR(30) NOT NULL,
    ticketprice          DOUBLE NOT NULL
);

ALTER TABLE flight ADD CONSTRAINT flight_pk PRIMARY KEY ( flightcode );

CREATE TABLE passenger (
    passengerid  VARCHAR(30) NOT NULL,
    name         VARCHAR(30) NOT NULL,
    surname      VARCHAR(30) NOT NULL,
    countryname  VARCHAR(30) NOT NULL,
    address      VARCHAR(60) NOT NULL
);

ALTER TABLE passenger ADD CONSTRAINT passenger_pk PRIMARY KEY ( passengerid );

CREATE TABLE phonenumber (
    numid                  VARCHAR(30) NOT NULL,
    phonenum               VARCHAR(30),
    passenger_passengerid  VARCHAR(30)
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