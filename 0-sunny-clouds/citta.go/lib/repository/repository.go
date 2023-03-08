package repository

import (
	"github.com/gofiber/fiber/v2"
	"gorm.io/gorm"
)

var DB *gorm.DB

func Init(db *gorm.DB) {
	DB = db
}

type NewEntityFunc func() interface{}

type Repository struct {
	Name      string
	NewEntity NewEntityFunc
}

type RepoController interface {
	Create(c *fiber.Ctx) error
	GetById(c *fiber.Ctx) error
	UpdateById(c *fiber.Ctx) error
	DeleteById(c *fiber.Ctx) error
}

func (repo *Repository) Create(c *fiber.Ctx) error {
	entity := repo.NewEntity()
	if err := c.BodyParser(entity); err != nil {
		return c.Status(400).JSON(err.Error())
	}

	DB.Create(entity)

	return c.Status(200).JSON(entity)
}

func (repo *Repository) GetById(c *fiber.Ctx) error {
	var id = c.Params("id")
	entity := repo.NewEntity()
	res := DB.First(&entity, id)
	if res.Error != nil {
		return c.SendStatus(404)
	}

	return c.Status(200).JSON(entity)
}

func (repo *Repository) UpdateById(c *fiber.Ctx) error {
	var id = c.Params("id")
	entity := repo.NewEntity()
	res := DB.First(&entity, id)
	if res.Error != nil {
		return c.SendStatus(404)
	}

	if err := c.BodyParser(entity); err != nil {
		return c.Status(400).JSON(err.Error())
	}

	res2 := DB.Save(entity)
	if res2.Error != nil {
		return c.SendStatus(500)
	}

	return c.Status(200).JSON(entity)
}

func (repo *Repository) DeleteById(c *fiber.Ctx) error {
	var id = c.Params("id")
	entity := repo.NewEntity()
	res := DB.First(&entity, id)
	if res.Error != nil {
		return c.SendStatus(404)
	}

	res2 := DB.Delete(&entity, id)
	if res2.Error != nil {
		return c.SendStatus(500)
	}

	return c.Status(200).JSON(entity)
}
