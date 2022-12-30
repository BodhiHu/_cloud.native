package controllers

import (
	"github.com/gofiber/fiber/v2"

	db "citta_go/database"
	"citta_go/models"
)

func Echo(c *fiber.Ctx) error {
	return c.SendString("bodhicitta")
}

func NewUser(c *fiber.Ctx) error {
	user := new(models.User)
	if err := c.BodyParser(user); err != nil {
		return c.Status(400).JSON(err.Error())
	}

	db.DB.Create(user)

	return c.Status(200).JSON(user)
}

func GetUser(c *fiber.Ctx) error {

	var userId = c.Params("id")
	var user models.User
	res := db.DB.First(&user, userId)
	if res.Error != nil {
		return c.SendStatus(404)
	}

	return c.Status(200).JSON(user)
}
