--liquibase formatted sql

--changeset Reinis:1
CREATE TABLE books
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL UNIQUE,
    author     VARCHAR(255) NOT NULL,
    date_added TIMESTAMP
);