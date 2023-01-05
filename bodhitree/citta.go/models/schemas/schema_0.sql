CREATE TABLE users (
	id bigserial NOT NULL,
	name text NULL,
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
