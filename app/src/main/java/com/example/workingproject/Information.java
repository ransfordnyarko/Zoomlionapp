package com.example.workingproject;

public class Information {
    private String email;
    private int no_products;
    private double amount;

    public Information() {
    }

    public Information(String email, int no_products, double amount) {
        this.email = email;
        this.no_products = no_products;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNo_products() {
        return no_products;
    }

    public void setNo_products(int no_products) {
        this.no_products = no_products;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
