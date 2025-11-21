package com.example.springhellowold;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Product {
    private Long productId;
    private String productName;
    private float productCost;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate productExpireDate;

    public Product() {
    }

    public Product(Long productId, String productName, float productCost, LocalDate productExpireDate) {
        this.productId = productId;
        this.productName = productName;
        this.productCost = productCost;
        this.productExpireDate = productExpireDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductCost() {
        return productCost;
    }

    public void setProductCost(float productCost) {
        this.productCost = productCost;
    }

    public LocalDate getProductExpireDate() {
        return productExpireDate;
    }

    public void setProductExpireDate(LocalDate productExpireDate) {
        this.productExpireDate = productExpireDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productCost=" + productCost +
                ", productExpireDate=" + productExpireDate +
                '}';
    }
}

