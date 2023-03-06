package statestore

import (
	"context"

	dapr "github.com/dapr/go-sdk/client"
)

const STATE_STORE_NAME = "bodhitree"

type StateStore struct {
	client    dapr.Client
	storeName string
}

type IStateStore interface {
	SaveState(ctx context.Context, key string, data []byte) error
	GetState(ctx context.Context, key string) (item *dapr.StateItem, err error)
	DeleteState(ctx context.Context, key string) error
}

var stateStore *StateStore = nil

func InitStateStore() (store *StateStore, err error) {
	if stateStore == nil {
		client, err := dapr.NewClient()
		if err != nil {
			return nil, err
		}
		stateStore = &StateStore{client, STATE_STORE_NAME}
	}

	return stateStore, nil
}

func GetStore() (store *StateStore) {
	if stateStore == nil {
		InitStateStore()
	}

	return stateStore
}

func (store *StateStore) SaveState(ctx context.Context, key string, data []byte) error {
	return store.client.SaveState(ctx, store.storeName, key, data, nil)
}
func (store *StateStore) GetState(ctx context.Context, key string) (item *dapr.StateItem, err error) {
	return store.client.GetState(ctx, store.storeName, key, nil)
}
func (store *StateStore) DeleteState(ctx context.Context, key string) error {
	return store.client.DeleteState(ctx, store.storeName, key, nil)
}
