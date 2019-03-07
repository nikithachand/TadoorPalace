package com.example.tadoorpalace.Model;

public class Cart
{
    private String pid, DishName, Price, Date, Time, Quantity, Discount;

    public Cart() {
    }

    public Cart(String pid, String dishName, String price, String date, String time, String quantity, String discount) {
        this.pid = pid;
        DishName = dishName;
        Price = price;
        Date = date;
        Time = time;
        Quantity = quantity;
        Discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}

