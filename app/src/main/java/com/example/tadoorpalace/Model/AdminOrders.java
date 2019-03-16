package com.example.tadoorpalace.Model;

public class AdminOrders
{
    private String TotalAmount, Name, Phone, Address, Date, Time, State;


    public AdminOrders()
    {
    }

    public AdminOrders(String totalAmount, String name, String phone, String address, String date, String time, String state)
    {
        TotalAmount = totalAmount;
        Name = name;
        Phone = phone;
        Address = address;
        Date = date;
        Time = time;
        State = state;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
