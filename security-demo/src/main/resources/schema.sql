CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255),
    password    VARCHAR(255),
    authorities VARCHAR(255)
);
