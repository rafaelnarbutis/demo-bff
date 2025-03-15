package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"orderservice/infra"
	"orderservice/models"
)

func main() {
	router := gin.Default()

	infra.InitBusConfig()
	infra.InitRepositoryConfig()

	router.POST("/orders", post_orders)
	router.GET("/orders/:id", get_order)

	router.Run(":8080")
}

func post_orders(c *gin.Context) {
	var orderRequest models.OrderRequest

	if err := c.BindJSON(&orderRequest); err != nil {

		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}

	c.JSON(200, gin.H{})

	go infra.SendMessage(orderRequest)
	go infra.SaveOrder(orderRequest)
}

func get_order(c *gin.Context) {
	id := c.Param("id")

	order, err := infra.GetOrder(id)
	if err != nil {
		c.JSON(http.StatusNoContent, gin.H{"error": err.Error()})
	}
	c.JSON(200, order)
}
