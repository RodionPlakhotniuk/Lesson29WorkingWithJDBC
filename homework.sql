CREATE DATABASE IF NOT EXISTS company;
USE company;
CREATE TABLE employees(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255),
age INT,
position VARCHAR(255),
salary DECIMAL(10,2)
);