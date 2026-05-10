CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       type VARCHAR(50) NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       publish_year INT NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       genre VARCHAR(50) NOT NULL,
                       quantity INT NOT NULL,
                       extra_data VARCHAR(255)
);