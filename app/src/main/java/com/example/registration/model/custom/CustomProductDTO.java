package com.example.registration.model.custom;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomProductDTO implements Parcelable {
    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public CustomProductDTO(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    protected CustomProductDTO(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        quantity = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<CustomProductDTO> CREATOR = new Creator<CustomProductDTO>() {
        @Override
        public CustomProductDTO createFromParcel(Parcel in) {
            return new CustomProductDTO(in);
        }

        @Override
        public CustomProductDTO[] newArray(int size) {
            return new CustomProductDTO[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(productName);
        dest.writeInt(quantity);
        dest.writeDouble(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
