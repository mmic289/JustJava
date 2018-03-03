package com.example.android.justjava;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/14/2018.
 */

public class CoffeeObject {

    private String coffeeSize;
    private ArrayList<String> listOfToppings = new ArrayList<>();
    private double cPrice;
    private int cQuantity;
    private int extraOptions;

    public CoffeeObject(String sizeInput, int allToppings, int finalQuanity, double finalPrice, ArrayList<String> tList) {

        coffeeSize = sizeInput;
        extraOptions = allToppings;
        cQuantity = finalQuanity;
        cPrice = finalPrice;
        listOfToppings = new ArrayList<>(tList);
        return;

    }

    public String getCoffeeSize() {
        return coffeeSize;
    }

    public double getcPrice() {
        return cPrice;
    }

    public int getcQuantity() {
        return cQuantity;
    }

    public int getExtraOptions() {
        return extraOptions;
    }

    public ArrayList<String> getToppingsList() {
        return listOfToppings;
    }
}
