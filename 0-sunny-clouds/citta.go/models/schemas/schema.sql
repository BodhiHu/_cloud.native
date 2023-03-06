-- Create tables
CREATE TABLE users (
	id bigserial NOT NULL,
	username varchar(100) NOT NULL,
	password varchar(100) DEFAULT NULL,
	authorities varchar(255) DEFAULT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE articles (
	id bigserial NOT NULL,
	title text NOT NULL,
	media_url text NULL,
	desc text NOT NULL,
	content text NULL,
	CONSTRAINT articles_pkey PRIMARY KEY (id)
);

-- 
ALTER TABLE users DROP COLUMN "name";
