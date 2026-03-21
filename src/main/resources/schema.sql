CREATE TABLE IF NOT EXISTS products(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100),
    stock INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    rating DECIMAL(3,2),
    product_condition ENUM("nou","bon_estat","acceptable", "mal_estat"),
    status BOOLEAN,
    data_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);