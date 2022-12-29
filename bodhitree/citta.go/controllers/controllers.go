package controllers

import (
	"context"

	"github.com/goccy/go-json"
	"github.com/gofiber/fiber/v2"

	"citta_go/models"
	"citta_go/statestore"
)

func Echo(c *fiber.Ctx) error {
	return c.SendString("bodhicitta")
}

func NewUser(c *fiber.Ctx) error {
	user := new(models.User)
	if err := c.BodyParser(user); err != nil {
		return c.Status(400).JSON(err.Error())
	}

	userJSON, err := json.Marshal(user)
	if err != nil {
		return c.Status(500).JSON(err.Error())
	}

	statestore.GetStore().SaveState(context.TODO(), user.ID, []byte(userJSON))

	return c.Status(200).JSON(user)
}
