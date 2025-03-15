package order_service

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"orderservice/infra"
	"orderservice/models"
)

func main() {
	router := gin.Default()

	router.POST("/orders", orders)

	router.Run(":8080")
}

func orders(c *gin.Context) {
	var orderRequest models.OrderRequest

	if err := c.BindJSON(&orderRequest); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}

	c.JSON(200, gin.H{})

	go infra.SendMessage(orderRequest)
}
