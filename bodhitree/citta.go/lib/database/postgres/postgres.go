package postgres_db

import (
	"log"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

type InitStateType int

const (
	INIT_IDLE InitStateType = iota
	INIT_IN_PROCESS
	INIT_DONE
)

var initState InitStateType = INIT_IDLE
var DB *gorm.DB

type OnDBConnected func(DB *gorm.DB)

func Init(dsn string, onConnected OnDBConnected) *gorm.DB {
	if initState != INIT_IDLE {
		return DB
	}

	initState = INIT_IN_PROCESS

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Warn),
	})
	if err != nil {
		log.Fatal("Failed to connect to database. \n", err)
	}

	log.Println("Connected to database")
	db.Logger = logger.Default.LogMode(logger.Warn)

	DB = db
	if onConnected != nil {
		onConnected(DB)
	}

	initState = INIT_DONE

	return DB
}
