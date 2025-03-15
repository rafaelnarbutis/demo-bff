package infra

import (
	"encoding/json"
	amqp "github.com/rabbitmq/amqp091-go"
	"log"
	"orderservice/models"
	"os"
)

var channel *amqp.Channel
var queue *amqp.Queue

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

func InitBusConfig() {

	host := os.Getenv("RABBIT_MQ_HOST")

	if host == "" {
		host = "localhost"
	}

	conn, err := amqp.Dial("amqp://guest:guest@" + host + ":5672/")

	failOnError(err, "Failed to connect to RabbitMQ")

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")

	q, err := ch.QueueDeclare(
		"order-queue", // name
		false,         // durable
		false,         // delete when unused
		false,         // exclusive
		false,         // no-wait
		nil,           // arguments
	)
	failOnError(err, "Failed to declare a queue")

	queue = &q
	channel = ch
}

func SendMessage(request models.OrderRequest) {
	orderJson, err := json.Marshal(&request)

	failOnError(err, "Failed to marshal order")

	errCh := channel.Publish(
		"",
		"order-queue",
		false,
		false,
		amqp.Publishing{
			ContentType: "application/json",
			Body:        orderJson,
		})

	if errCh != nil {
		return
	}

	failOnError(err, "Failed to publish a message")
	log.Printf("sending message: %s", orderJson)
}
