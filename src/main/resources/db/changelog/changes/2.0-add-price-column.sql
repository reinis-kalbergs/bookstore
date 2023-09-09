--liquibase formatted sql

--changeset Reinis:2
ALTER TABLE books
    ADD price DECIMAL NOT NULL DEFAULT 0.0;