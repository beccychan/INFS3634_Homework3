package com.beccychan.cats;

public class CheckoutItem {
    private Integer itemAmount;
    private Integer itemId;
    private String itemImage;
    private String itemName;
    private Double itemPrice;
    private String itemDescription;


    public CheckoutItem(Integer amount, Integer id, String image, String name, Double price, String description) {
        itemAmount = amount;
        itemId = id;
        itemImage = image;
        itemName = name;
        itemPrice = price;
        itemDescription = description;

    }

    public Integer getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Integer itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
