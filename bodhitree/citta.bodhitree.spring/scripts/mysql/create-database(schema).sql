# Create database (schema) and access user
CREATE DATABASE BodhiTree;
USE BodhiTree;
CREATE USER 'bodhi'@'%' IDENTIFIED BY 'BodhiTree123';
GRANT ALL PRIVILEGES ON * . * TO 'bodhi'@'%';
FLUSH PRIVILEGES;

# show all users
# SELECT * FROM mysql.user;

# DANGER!!!

# DROP USER 'bodhi';
