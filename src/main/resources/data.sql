CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);
INSERT INTO users (name, email) VALUES ('김철수', 'chulsoo.kim@example.com');
INSERT INTO users (name, email) VALUES ('이영희', 'younghee.lee@example.com');
INSERT INTO users (name, email) VALUES ('박지민', 'jimin.park@example.com');

CREATE TABLE IF NOT EXISTS boards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
INSERT INTO boards (title, content, created_at, updated_at, user_id) VALUES ('첫 번째 게시글', '안녕하세요, 첫 번째 게시글입니다.', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);
INSERT INTO boards (title, content, created_at, updated_at, user_id) VALUES ('두 번째 게시글', '안녕하세요, 두 번째 게시글입니다.', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2);
INSERT INTO boards (title, content, created_at, updated_at, user_id) VALUES ('세 번째 게시글', '안녕하세요, 세 번째 게시글입니다.', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 3);
