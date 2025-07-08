CREATE TABLE IF NOT EXISTS Users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(64) UNIQUE NOT NULL,
    firstname VARCHAR(64),
    lastname VARCHAR(32),
    birth_date DATE,
    email varchar(64) UNIQUE,
    password varchar(64) NOT NULL,
    personal_info VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS Posts(
    id SERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    description VARCHAR(512),
    category VARCHAR(64),
    post_date TIMESTAMP,
    user_id BIGINT REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS Comments(
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES Users(id),
    post_id BIGINT REFERENCES Posts(id),
    post_date TIMESTAMP,
    message varchar(512) /* розмір не оновлений */
);

CREATE TABLE IF NOT EXISTS Reactions(
    user_id BIGINT REFERENCES Users(id),
    post_id BIGINT REFERENCES Posts(id),
    type VARCHAR(32) NOT NULL,
    PRIMARY KEY (user_id, post_id)
);

drop table Comments;
drop table Reactions;
drop table Posts;
drop table Users;