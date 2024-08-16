create table users(
id int AUTO_INCREMENT primary key,
username varchar(50),
password varchar(100),
role varchar(50)
);

create table pet(
id int AUTO_INCREMENT primary key,
name varchar(50),
id_code long,
type varchar(50),
fur_color varchar(50),
country varchar(50),
user_id INT,
FOREIGN KEY (user_id) REFERENCES users(id)
);