CREATE TABLE food_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    quantity int NOT NULL CHECK (quantity >= 1),
    expiry_date DATE NOT NULL
);