CREATE SEQUENCE address_id_seq as BIGINT START WITH 1 INCREMENT BY 1;
CREATE TABLE address (
	code BIGINT GENERATED BY DEFAULT as SEQUENCE address_id_seq PRIMARY KEY,
	streetName varchar(255),
	number INT,
	complement varchar(255),
	neighborhood varchar(255),
	city varchar(255),
	state varchar(2),
	code_person BIGINT NOT NULL,
	FOREIGN KEY (code_person) REFERENCES person(code)
);