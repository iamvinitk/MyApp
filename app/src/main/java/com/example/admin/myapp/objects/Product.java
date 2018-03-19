package com.example.admin.myapp.objects;


public class Product {
    private String mProductName = null;
    private String mProductPrice = null;
    private String mImageUrl = null;
    private String mProductId = null;


    public Product(String ProductName, String ProductId, String ProductPrice, String ImageUrl) {
        mProductName = ProductName;
        mProductId = ProductId;
        mProductPrice = ProductPrice;
        mImageUrl = ImageUrl;
    }

    public String getmProductName() {
        return mProductName;
    }

    public String getmProductPrice() {
        return mProductPrice;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmProductId() {
        return mProductId;
    }
}



