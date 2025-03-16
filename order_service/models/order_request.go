package models

type OrderRequest struct {
	ClientId string   `json:"client_id"`
	Total    string   `json:"total"`
	ItemsId  []string `json:"items_id"`
}

type OrderResponse struct {
	Id       string   `json:"id"`
	ClientId string   `json:"client_id"`
	Total    string   `json:"total"`
	ItemsId  []string `json:"items_id"`
}
