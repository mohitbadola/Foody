package com.jason.foody.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@Entity(name = "cart")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userId;
    private UUID itemId;
    @NotBlank(message = "Item name can not be empty.")
    private String itemName;
    @Positive(message = "Quantity should be positive.")
    private int quantity;
    @Positive(message = "Price must be positive.")
    private Double price;

    public CartItem() {
    }

    public CartItem(Double price, int quantity, String itemName, UUID itemId, UUID userId) {
        this.price = price;
        this.quantity = quantity;
        this.itemName = itemName;
        this.itemId = itemId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
