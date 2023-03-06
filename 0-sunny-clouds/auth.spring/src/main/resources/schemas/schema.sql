CREATE TABLE users (
	id bigserial NOT NULL,
	username varchar(100) NOT NULL,
	password varchar(100) DEFAULT NULL,
	authorities varchar(255) DEFAULT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
