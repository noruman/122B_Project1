CREATE TABLE creditcards (
	id	VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    expiration DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE customers (
	id	INTEGER NOT NULL AUTO_INCREMENT, 
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    cc_id VARCHAR(20) NOT NULL REFERENCES creditcards(id),
    address VARCHAR(200) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE genres (
	id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE genres_in_movies (
	genre_id INTEGER NOT NULL REFERENCES genres(id),
    movie_id INTEGER NOT NULL REFERENCES movies(id)
);

CREATE TABLE movies (
	id	INTEGER NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    year INTEGER NOT NULL,
    director VARCHAR(100) NOT NULL,
    banner_url VARCHAR(200),
    trailer_url VARCHAR(200),
    PRIMARY KEY(id)
);

CREATE TABLE sales (
	id INTEGER NOT NULL AUTO_INCREMENT,
    customer_id INTEGER NOT NULL REFERENCES customers(id),
    movie_id INTEGER NOT NULL REFERENCES movies(id),
    sales_date DATE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE stars (
	id	INTEGER NOT NULL AUTO_INCREMENT,
    first_name 	VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE,
    photo_url VARCHAR(200),
    PRIMARY KEY (id)
);

CREATE TABLE stars_in_movies (
	star_id	INTEGER NOT NULL REFERENCES stars(id),
    movie_id INTEGER NOT NULL REFERENCES movies(id)
)