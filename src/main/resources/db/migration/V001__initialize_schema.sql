DROP EXTENSION if exists "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE address
(
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    country VARCHAR(100),
    city VARCHAR(50),
    street VARCHAR(255)
);

CREATE TABLE images
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    image BYTEA
);

CREATE TABLE supplier
(
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(255),
    address_id BIGINT,
    phone_number VARCHAR(50),
    CONSTRAINT fk_supplier_address_id FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE client
(
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    client_name VARCHAR(20),
    client_surname VARCHAR(50),
    birthday DATE,
    registration_date DATE,
    address_id BIGINT,
    CONSTRAINT fk_client_address_id FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE product
(
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(255),
    category VARCHAR(255),
    price NUMERIC,
    available_stock BIGINT,
    last_update_date DATE,
    supplier_id BIGINT,
    image_id UUID,
    CONSTRAINT fk_product_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id),
    CONSTRAINT fk_product_image_id FOREIGN KEY (image_id) REFERENCES images(id)
);


