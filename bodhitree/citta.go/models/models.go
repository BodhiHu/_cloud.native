package models

type User struct {
	ID   uint   `json:"id"   gorm:"primaryKey;type:uint;autoIncrement;"`
	Name string `json:"name" gorm:""`
}

type Article struct {
	ID       uint   `json:"id"                  gorm:"primaryKey;type:uint;autoIncrement;"`
	Title    string `json:"title,omitempty"     gorm:"not null;"`
	MediaUrl string `json:"mediaUrl,omitempty"`
	Desc     string `json:"desc"                gorm:"not null;"`
	Content  string `json:"content"`
}
