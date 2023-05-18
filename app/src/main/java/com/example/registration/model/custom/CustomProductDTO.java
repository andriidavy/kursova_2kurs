package com.example.registration.model.custom;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomProductDTO implements Parcelable {
    private int productId;
    private String productName;
    private int quantity;

    public CustomProductDTO(int productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
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

    protected CustomProductDTO(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        quantity = in.readInt();
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
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
