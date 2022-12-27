package controllers

import (
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

	statestore.GetStore()

	return c.Status(200).JSON(user)
}

// AllBooks
func AllBooks(c *fiber.Ctx) error {
	books := []models.Book{}
	database.DB.Db.Find(&books)

	return c.Status(200).JSON(books)
}

// Update
func Update(c *fiber.Ctx) error {
	book := []models.Book{}
	title := new(models.Book)
	if err := c.BodyParser(title); err != nil {
		return c.Status(400).JSON(err.Error())
	}

	database.DB.Db.Model(&book).Where("title = ?", title.Title).Update("author", title.Author)

	return c.Status(400).JSON("updated")
}

// Delete
func Delete(c *fiber.Ctx) error {
	book := []models.Book{}
	title := new(models.Book)
	if err := c.BodyParser(title); err != nil {
		return c.Status(400).JSON(err.Error())
	}
	database.DB.Db.Where("title = ?", title.Title).Delete(&book)

	return c.Status(200).JSON("deleted")
}
