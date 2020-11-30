package com.ralba.infocarapp.models;

import com.ralba.infocarapp.R;
import com.ralba.infocarapp.views.MyApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class CarEntity {

    private String brand;
    private String model;
    private String HP;
    private String description;
    private String motorType;
    private Date launchDate;
    private boolean reprogrammable;

    public CarEntity() {}

    public String getBrand() {
        return brand;
    }

    public boolean setBrand(String brand) {
        if(brand.length()>0 && brand.matches("[A-Za-zÑñ]+") && brand.matches("[^0-9]+")){
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
        if(model.length()>0){
            this.model = model;
            return true;
        }else{
            return false;
        }
    }

    public String getHP() {
        return HP;
    }

    public boolean setHP(String HP) {
        if(HP.matches("[0-9]*")){
            this.HP=HP;
            return true;
        }else{
            return false;
        }
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        if(description.length()>0){
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
        String restriction = "^(?:(?:(?:0?[1-9]|1\\d|2[0-8])[/](?:0?[1-9]|1[0-2])|(?:29|30)[/](?:0?[13-9]|1[0-2])|31[/](?:0?[13578]|1[02]))[/](?:0{2,3}[1-9]|0{1,2}[1-9]\\d|0?[1-9]\\d{2}|[1-9]\\d{3})|29[/]0?2[/](?:\\d{1,2}(?:0[48]|[2468][048]|[13579][26])|(?:0?[48]|[13579][26]|[2468][048])00))$";

        if(launchDate.matches(restriction)){
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
