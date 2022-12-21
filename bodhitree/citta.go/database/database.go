package database

import (
	"log"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"

	models "citta_go/models"
)

type Dbinstance struct {
	Db *gorm.DB
}

var DB Dbinstance

// connectDb
func ConnectDb() {
	dsn := "host=localhost user=postgres password='' dbname=go-db port=5432 sslmode=disable TimeZone=Asia/Shanghai"

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	})

	if err != nil {
		log.Fatal("Failed to connect to database. \n", err)
	}

	log.Println("connected")
	db.Logger = logger.Default.LogMode(logger.Info)
	log.Println("running migrations")
	db.AutoMigrate(&models.Book{})

	DB = Dbinstance{
		Db: db,
	}
}
