package main

import (
	"log"
	"runtime"
	"sync"

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
	db.Init(
		"host=localhost user=bodhi password='bodhicitta' dbname=bodhitree port=54321 sslmode=disable TimeZone=Asia/Shanghai",
		func(DB *gorm.DB) {
			log.Println("Running DB migrations")
			db.DB.AutoMigrate(&models.User{}, &models.Article{})
		},
	)
	repos.Init(db.DB)

	wg := new(sync.WaitGroup)
	N := runtime.NumCPU()
	if N > 1 {
		N = 1
	}
	C := make(chan int, N)

	for i := 0; i < N; i++ {
		wg.Add(1)
		go func(i int) {
			app := fiber.New(fiber.Config{
				Prefork: !true,
			})
			app.Use(cors.New())
			app.Use(recover.New())

			routes.Init(app)

			C <- i
			log.Print("Listening in goroutine ", <-C)

			log.Fatal(app.Listen(":3000"))

			C <- -1
			wg.Done()
		}(i)
	}

	wg.Wait()
	for e := range C {
		log.Print("fiber routine exited with code ", e)
	}
}
