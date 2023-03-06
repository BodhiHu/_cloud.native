package routes

import (
	"github.com/gofiber/fiber/v2"

	repos "citta_go/repos"
)

func Init(app *fiber.App) {
	app.Get("/", repos.Echo)
	app.Post("/user", repos.Users.Create)
	app.Get("/user/:id", repos.Users.GetById)
}
