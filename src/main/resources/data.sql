CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);
INSERT INTO users (name, email) VALUES ('김철수', 'chulsoo.kim@example.com');
INSERT INTO users (name, email) VALUES ('이영희', 'younghee.lee@example.com');
INSERT INTO users (name, email) VALUES ('박지민', 'jimin.park@example.com');
