package database

import (
	"log"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"

	"citta_go/models"
)

var DB *gorm.DB

func ConnectDB() {
	dsn := "host=localhost user=bodhi password='bodhicitta' dbname=bodhitree port=54321 sslmode=disable TimeZone=Asia/Shanghai"

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	})
	if err != nil {
		log.Fatal("Failed to connect to database. \n", err)
	}

	log.Println("Connected to database")
	db.Logger = logger.Default.LogMode(logger.Info)

	log.Println("Running DB migrations")
	db.AutoMigrate(&models.User{})

	DB = db
}
