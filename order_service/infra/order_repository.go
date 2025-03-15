package infra

import (
	"context"
	"log"
	"orderservice/models"

	"go.mongodb.org/mongo-driver/v2/bson"
	"go.mongodb.org/mongo-driver/v2/mongo"
	"go.mongodb.org/mongo-driver/v2/mongo/options"
)

var client *mongo.Client

func InitRepositoryConfig() {
	credential := options.Credential{
		Username:      "root",
		Password:      "root",
		AuthMechanism: "SCRAM-SHA-1",
	}
	client_context, _ := mongo.Connect(options.Client().ApplyURI("mongodb://localhost:27017").SetAuth(credential))
	client = client_context
}

func SaveOrder(order models.OrderRequest) {
	_, err := client.Database("order_db").Collection("orders").InsertOne(context.Background(), order)
	if err != nil {
		log.Fatalf("Error to save a order: %s", err)
	}
}

func GetOrder(id string) (models.OrderRequest, error) {
	var order models.OrderRequest

	objectId, _ := bson.ObjectIDFromHex(id)

	filter := bson.D{{"_id", objectId}}
	err := client.Database("order_db").Collection("orders").FindOne(context.Background(), filter).Decode(&order)

	if err != nil {
		log.Printf("Error to get a order: %s", err)
		return order, err
	}

	return order, nil
}
