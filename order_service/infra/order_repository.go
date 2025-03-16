package infra

import (
	"context"
	"log"
	"orderservice/models"
	"os"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var client *mongo.Client

type orderBson struct {
	Id       primitive.ObjectID `bson:"_id"`
	ClientId string             `bson:"client_id"`
	Amount   string             `bson:"amount"`
	ItemsId  []string           `bson:"items_id"`
}

func InitRepositoryConfig() {
	credential := options.Credential{
		Username:      "root",
		Password:      "root",
		AuthMechanism: "SCRAM-SHA-1",
	}

	host := os.Getenv("MONGODB_HOST")

	if host == "" {
		host = "localhost"
	}

	var uri = "mongodb://" + host + ":27017"

	client_context, _ := mongo.Connect(context.Background(), options.Client().ApplyURI(uri).SetAuth(credential))
	client = client_context
}

func SaveOrder(order models.OrderRequest) {
	_, err := client.Database("order_db").Collection("orders").InsertOne(context.Background(), order)
	if err != nil {
		log.Fatalf("Error to save a order: %s", err)
	}
}

func GetOrder(id string) (models.OrderResponse, error) {
	var orderBson orderBson

	objectId, _ := primitive.ObjectIDFromHex(id)

	filter := bson.D{{"_id", objectId}}
	err := client.Database("order_db").Collection("orders").FindOne(context.Background(), filter).Decode(&orderBson)

	if err != nil {
		log.Printf("Error to get a order: %s", err)
		return models.OrderResponse{}, err
	}

	return models.OrderResponse{
		Id:       orderBson.Id.Hex(),
		ClientId: orderBson.ClientId,
		Amount:   orderBson.Amount,
		ItemsId:  orderBson.ItemsId,
	}, nil
}

func GetOrders() []models.OrderResponse {
	var orders []models.OrderResponse

	cursor, err := client.Database("order_db").Collection("orders").Find(context.Background(), bson.D{})

	if err != nil {
		log.Printf("Error to get orders: %s", err)
		return orders
	}

	for cursor.Next(context.Background()) {
		var orderBson orderBson

		if err := cursor.Decode(&orderBson); err != nil {
			log.Printf("Error decoding order: %s", err)
			continue
		}

		order := models.OrderResponse{
			Id:       orderBson.Id.Hex(),
			ClientId: orderBson.ClientId,
			Amount:   orderBson.Amount,
			ItemsId:  orderBson.ItemsId,
		}

		orders = append(orders, order)
	}

	return orders
}
