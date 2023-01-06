package main

import (
	"log"
	"runtime"
	"sync"
	"time"

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

	app := fiber.New(fiber.Config{
		Prefork: !true,
	})
	app.Use(cors.New())
	app.Use(recover.New())

	routes.Init(app)

	go someRoutine()

	log.Fatal(app.Listen(":3000"))
}

func someRoutine() {
	time.Sleep(time.Second)
	log.Print("--------------- someRoutine ---------------------------")

	wg := new(sync.WaitGroup)
	N := runtime.NumCPU()
	if N > 5 {
		N = 5
	}
	C := make(chan int, N)

	for i := 0; i < N; i++ {
		wg.Add(1)
		go func(i int) {
			seconds := i + 1
			time.Sleep(time.Duration(seconds) * time.Second)
			log.Print("time delay routine done: ", seconds, " seconds")
			C <- 1
			wg.Done()
		}(i)
	}

	wg.Wait()
	waitedSeconds := 0
	for i := 0; i < N; i++ {
		waitedSeconds += <-C
	}
	log.Print("WaitGroup done, waited ", waitedSeconds, " seconds")

	log.Print("--------------- someRoutine ---------------------------")
}
