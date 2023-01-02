### Set up MySQL

##### 1. download and install mysql;
> https://dev.mysql.com/downloads/mysql/

##### 2. export env:

```
export PATH=$PATH:/usr/local/mysql/bin
``` 

##### 3. create user and database

```
$ # logon to mysql
$ mysql -u root -p
$
$ # create user 'bodhi' with pwd 'BodhiTree'
$ mysql > create user 'bodhi'@'localhost' IDENTIFIED BY 'BodhiTree';
$
$ # create db BodhiTree
$ mysql > create database BodhiTree
$
$ # grant db permissions to bodhi
$ mysql > grant all on BodhiTree.* to 'bodhi'@'localhost';
$
$ mysql > exit

```
