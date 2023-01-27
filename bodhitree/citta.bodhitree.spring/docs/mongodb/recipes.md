
# MongoDB Recipes

### start mongodb server

```
mongod --auth --port 27017 --dbpath ./DB/mongo

```

### connect to mongodb

```
mongo appDatabase -u dbAdmin

```

### create root user

```
use admin

db.createUser({
  user: "root",
  pwd: "BodhiTree",
  roles: [ "root" ]
})

```

### create db admin users

> see https://docs.mongodb.com/manual/reference/built-in-roles/#dbOwner

```
use appDatabase

db.createUser({
  user: "dbOwner",
  pwd: "BodhiTree",
  roles: [ "dbOwner" ]
})

db.createUser({
  user: "dbAdmin",
  pwd: "BodhiTree",
  roles: [ "dbAdmin" ]
})

db.createUser({
  user: "engineer",
  pwd: "BodhiTree",
  roles: [ "dbAdmin", "readWrite" ]
})

```

### create database

