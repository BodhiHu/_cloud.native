package main

import (
	"log"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/cors"
	"github.com/gofiber/fiber/v2/middleware/recover"
	"gorm.io/gorm"

	db "citta_go/lib/database/postgres"
	"citta_go/models"
	"citta_go/repos"
	"citta_go/routes"
)

func main() {
	app := fiber.New()
	app.Use(cors.New())
	app.Use(recover.New())

	db.Init(
		"host=localhost user=bodhi password='bodhicitta' dbname=bodhitree port=54321 sslmode=disable TimeZone=Asia/Shanghai",
		func(DB *gorm.DB) {
			log.Println("Running DB migrations")
			db.DB.AutoMigrate(&models.User{}, &models.Article{})
		},
	)
	repos.Init(db.DB)
	routes.Init(app)

	log.Fatal(app.Listen(":3000"))
}
