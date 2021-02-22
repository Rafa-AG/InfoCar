package com.ralba.infocarapp;

import com.ralba.infocarapp.models.CarEntity;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class CarTest {

    private CarEntity car;

    @Before
    public void setUp(){
        this.car = new CarEntity();
    }

    @Test
    public void carId(){
        this.car.setId("1A");
        assertEquals("1A", this.car.getId());
        this.car.setId("2B");
        assertEquals("2B", this.car.getId());
    }

    @Test
    public void carBrand(){
        assertEquals(true, this.car.setBrand("Car Brand"));
        assertEquals(false, this.car.setBrand("Car Test"));
        assertEquals("Car Brand", this.car.getBrand());
    }

    @Test
    public void carModel(){
        assertEquals(true, this.car.setModel("Car Model"));
        assertEquals(false, this.car.setModel("Car Test"));
        assertEquals("Car Model", this.car.getModel());
    }

    @Test
    public void carHP(){
        assertEquals(true, this.car.setHP("Car HP"));
        assertEquals(false, this.car.setHP("Car Test"));
        assertEquals("Car HP", this.car.getHP());
    }

    @Test
    public void carDescription(){
        assertEquals(true, this.car.setDescription("Car Description"));
        assertEquals(false, this.car.setDescription("Car Test"));
        assertEquals("Car Description", this.car.getDescription());
    }

    @Test
    public void carMotorType(){
        this.car.setMotorType("Gasoline");
        assertEquals("Gasoline", this.car.getMotorType());
        this.car.setMotorType("Hybrid");
        assertEquals("Hybrid", this.car.getMotorType());
    }

    @Test
    public void carLaunchDate(){
        assertEquals(true, this.car.setLaunchDate("05/12/2001"));
        assertEquals(true, this.car.setLaunchDate("15/05/2005"));
        assertEquals(false, this.car.setLaunchDate("21/13/2021"));
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("12/02/2015", date.format(this.car.getLaunchDate()));
    }

    @Test
    public void carReprogrammable(){
        this.car.setReprogrammable(true);
        assertEquals(true, this.car.isReprogrammable());
        this.car.setReprogrammable(false);
        assertEquals(false, this.car.isReprogrammable());
    }

    @Test
    public void carImage(){
        this.car.setImage("image123");
        assertEquals("image123", this.car.getImage());
        this.car.setImage("image456");
        assertEquals("image456", this.car.getImage());
    }

}
