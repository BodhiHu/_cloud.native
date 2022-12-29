package routes

import (
	"github.com/gofiber/fiber/v2"

	"citta_go/controllers"
)

func InitRoutes(app *fiber.App) {
	app.Get("/", controllers.Echo)
	app.Post("/user", controllers.NewUser)
}
