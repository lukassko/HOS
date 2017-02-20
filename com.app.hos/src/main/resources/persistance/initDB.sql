CREATE DATABASE IF NOT EXISTS webstore;
GRANT ALL PRIVILEGES ON webstore.* TO pc@localhost IDENTIFIED BY 'pc';

USE webstore;

CREATE TABLE IF NOT EXISTS category (
	id INT(4)  UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20)
) engine=InnoDB;
