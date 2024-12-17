-- CREATE TABLE IF NOT EXISTS Post(
--     id INT NOT NULL,
--     user_id INT NOT NULL,
--     title VARCHAR(250) NOT NULL,
--     body TEXT NOT NULL,
--     version INT,
--     PRIMARY KEY (id)
-- );

CREATE TABLE post (
      id INT PRIMARY KEY,
      user_id INT,
      title VARCHAR(255) NOT NULL,
      body TEXT NOT NULL,
      version INT NOT NULL DEFAULT 0
);
