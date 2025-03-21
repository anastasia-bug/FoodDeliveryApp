package com.example.javaexam4_5;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MenuItem implements Parcelable {
    private int id;
    private String name;
    private double price;
    private String description;
    private String weight;
    private int calories;
    private String imagePath;
    private int quantity;

    public MenuItem(int id, String name, double price, String description, String weight, int calories, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.weight = weight;
        this.calories = calories;
        this.imagePath = imagePath;
        this.quantity = 0;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getWeight() { return weight; }
    public int getCalories() { return calories; }
    public String getImagePath() { return imagePath; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getDrawableIdByName() {
        try {
            return R.drawable.class.getField(imagePath).getInt(null);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(weight);
        parcel.writeInt(calories);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeString(imagePath);
        parcel.writeInt(quantity);
    }

    protected MenuItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        weight = in.readString();
        calories = in.readInt();
        price = in.readDouble();
        description = in.readString();
        imagePath = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };
}
