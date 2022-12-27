package statestore

import (
	"context"

	dapr "github.com/dapr/go-sdk/client"
)

const STATE_STORE_NAME = "bodhitree"

type StateStore struct {
	client    Client
	storeName string
}

type IStateStore interface {
	SaveState(ctx context.Context, key string, data []byte) error
	GetState(ctx context.Context, key string) (item *StateItem, err error)
	DeleteState(ctx context.Context, key string) error
}

var stateStore *StateStore = nil

func InitStateStore() {
	if stateStore == nil {
		client, err := dapr.NewClient()
		if err == nil {
			stateStore = &StateStore{client, STATE_STORE_NAME}
		}
		return stateStore, err
	}
}

func GetStore() *StateStore {
	return stateStore
}

func (store *StateStore) SaveState(ctx context.Context, key string, data []byte) error {
	return store.client.SaveState(ctx, store.storeName, key, data, nil)
}
func (store *StateStore) GetState(ctx context.Context, key string) (item *StateItem, err error) {
	return store.client.GetState(ctx, storeName, key, nil)
}
func (store *StateStore) DeleteState(ctx context.Context, key string) error {
	return store.client.DeleteState(ctx, key, nil)
}

// func main() {

// 	rand.Seed(time.Now().UnixMicro())

// 	for i := 0; i < 10; i++ {

// 		orderId := rand.Intn(1000-1) + 1

// 		client, err := dapr.NewClient()
// 		if err != nil {
// 			panic(err)
// 		}
// 		defer client.Close()
// 		ctx := context.Background()
// 		err = client.SaveState(ctx, STATE_STORE_NAME, "order_1", []byte(strconv.Itoa(orderId)), nil)
// 		if err != nil {
// 			panic(err)
// 		}
// 		result, err := client.GetState(ctx, STATE_STORE_NAME, "order_1", nil)
// 		if err != nil {
// 			panic(err)
// 		}

// 		log.Println("Result after get:", string(result.Value))
// 		time.Sleep(2 * time.Second)
// 	}
// }
