package models

type OrderRequest struct {
	ClientId string   `json:"client_id"`
	Amount   string   `json:"amount"`
	ItemsId  []string `json:"items_id"`
}
