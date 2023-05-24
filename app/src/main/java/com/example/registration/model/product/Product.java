package com.example.registration.model.product;

import android.os.Parcel;
import android.os.Parcelable;


public class Product implements Parcelable {

        private int id;
        private String name;
        private String description;
        private int quantity;

        public Product(){}
        public Product(String name, String description, int quantity) {
            this.name = name;
            this.description = description;
            this.quantity = quantity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        quantity = in.readInt();
    }
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }
