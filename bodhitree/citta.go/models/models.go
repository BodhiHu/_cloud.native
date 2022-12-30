package models

type User struct {
	ID   uint   `json:"id";  	gorm:"primaryKey;type:int;autoIncrement;"`
	Name string `json:"name"; gorm:""`
}
