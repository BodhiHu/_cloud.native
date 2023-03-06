package repos

import (
	"github.com/gofiber/fiber/v2"
	"gorm.io/gorm"

	repo "citta_go/lib/repository"
	"citta_go/models"
)

var DB *gorm.DB

func Init(db *gorm.DB) {
	repo.Init(db)
	DB = db
}

func Echo(c *fiber.Ctx) error {
	return c.SendString("bodhicitta")
}

var Users = &repo.Repository{
	Name: "users",
	NewEntity: func() interface{} {
		return new(models.User)
	},
}

var Articles = repo.Repository{
	Name: "articles",
	NewEntity: func() interface{} {
		return new(models.Article)
	},
}
