package com.ralba.infocarapp.models;

import com.ralba.infocarapp.R;
import com.ralba.infocarapp.views.MyApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarEntity {

    private String brand;
    private String model;
    private String CV;
    private String description;
    private String motorType;
    private Date launchDate;
    private boolean reprogrammable;

    public CarEntity() {}

    public String getBrand() {
        return brand;
    }

    public boolean setBrand(String brand) {
        if(brand.toString().length()>2 && brand.toString().matches("[a-z A-Z]+") && brand.toString().matches("[^0-9]+")){
            this.brand = brand;
            return true;
        }else{
            return false;
        }
    }

    public String getModel() {
        return model;
    }

    public boolean setModel(String model) {
        if(model.toString().length()>0){
            this.model = model;
            return true;
        }else{
            return false;
        }
    }

    public String getCV() {
        return CV;
    }

    public boolean setCV(String CV) {
        if(CV.toString().length()>0 && CV.toString().matches("[^a-zA-Z]+")){
            this.CV=CV;
            return true;
        }else{
            return false;
        }
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        if(description.toString().length()>0){
            this.description=description;
            return true;
        }else{
            return false;
        }
    }

    public String getMotorType() {
        return motorType;
    }

    public void setMotorType(String motorType) {
        this.motorType=motorType;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public boolean setLaunchDate(String launchDate) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        if(launchDate.toString().length()==10 && launchDate.toString().matches("[^a-zA-Z]+")){
            try {
                this.launchDate=date.parse(launchDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean isReprogrammable() {
        return reprogrammable;
    }

    public void setReprogrammable(boolean reprogrammable) {
        this.reprogrammable = reprogrammable;
    }
}
