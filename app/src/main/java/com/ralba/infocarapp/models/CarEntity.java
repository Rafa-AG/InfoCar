package com.ralba.infocarapp.models;

public class CarEntity {

    private String brand;

    public CarEntity() {}

    public String getBrand() {
        return brand;
    }

    public boolean setBrand(String brand) {
        if(brand.startsWith("a")){
            this.brand = brand;
            return true;
        }else{
            return false;
        }
    }

}
