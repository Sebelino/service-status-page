CREATE TABLE IF NOT EXISTS dev.services
(
    name       VARCHAR(256) NOT NULL,
    url        VARCHAR(256) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
