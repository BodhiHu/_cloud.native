package main

import (
	"log"

	db "citta_go/database"
	"citta_go/routes"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/cors"
	"github.com/gofiber/fiber/v2/middleware/recover"
)

func main() {
	app := fiber.New()
	app.Use(cors.New())
	app.Use(recover.New())

	db.ConnectDB()
	routes.InitRoutes(app)

	log.Fatal(app.Listen(":3000"))
}
