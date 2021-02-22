package com.ralba.infocarapp.models;

import com.ralba.infocarapp.R;
import com.ralba.infocarapp.views.MyApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmResults;

public class CarModel {

    public ArrayList<CarEntity> getAllSummarize(){
        Realm realm=Realm.getDefaultInstance();

        RealmResults<CarEntity> result=realm.where(CarEntity.class).findAll();

        ArrayList<CarEntity> carList=new ArrayList<>();
        carList.addAll(realm.copyFromRealm(result));

        ArrayList<CarEntity> carListSummarize=new ArrayList<>();

        for(CarEntity car : carList){
            CarEntity aux = new CarEntity();
            aux.setId(car.getId());
            aux.setBrand(car.getBrand());
            aux.setModel(car.getModel());
            aux.setImage(car.getImage());
            carListSummarize.add(aux);
        }

        return carListSummarize;
    }

    public boolean insertCar(CarEntity car){
        AtomicBoolean result = new AtomicBoolean(false);

        car.setId(UUID.randomUUID().toString());

        Realm realm=Realm.getDefaultInstance();

        realm.executeTransaction(a->{
            realm.copyToRealm(car);
            result.set(true);
        });

        realm.close();

        return result.get();
    }

    public boolean updateCar(CarEntity car){
        AtomicReference<Boolean> result = new AtomicReference<>(false);

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(a->{
            realm.copyToRealmOrUpdate(car);
            result.set(true);
        });

        realm.close();

        return result.get();
    }

    public CarEntity getCarById(String id){
        CarEntity car = new CarEntity();
        Realm realm=Realm.getDefaultInstance();

        car=realm.where(CarEntity.class).equalTo("id", id).findFirst();

        realm.close();

        return car;
    }

    public boolean deleteCarById(String id){
        AtomicReference<Boolean> result = new AtomicReference<>(false);
        Realm realm=Realm.getDefaultInstance();

        realm.executeTransaction(a->{
            CarEntity car=realm.where(CarEntity.class).equalTo("id", id).findFirst();
            car.deleteFromRealm();
            result.set(true);
        });

        realm.close();

        return result.get();
    }

    public ArrayList<String> getAllMotorTypes(){
        ArrayList<String> motors=new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();

        motors.add(MyApplication.getContext().getString(R.string.motor_type));
        motors.add(MyApplication.getContext().getString(R.string.add_motor_type));

        RealmResults<CarEntity> results = realm.where(CarEntity.class).distinct("motorType").findAll();
        ArrayList<CarEntity> carList=new ArrayList<>();

        carList.addAll(realm.copyFromRealm(results));

        realm.close();

        for(CarEntity c:carList){
            motors.add(c.getMotorType());
        }

        return motors;
    }

    public ArrayList<CarEntity> getAllByFilter(String brand, String motor, Date date){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CarEntity> results;

        if(date==null){
            results=realm.where(CarEntity.class).contains("brand", brand)
                    .contains("motorType", motor).findAll();
        }else{
            results=realm.where(CarEntity.class).contains("brand", brand)
                    .equalTo("date", date.toString())
                    .contains("motorType", motor).findAll();
        }

        ArrayList<CarEntity> carList = new ArrayList<>();
        carList.addAll(realm.copyFromRealm(results));

        realm.close();

        ArrayList<CarEntity> carListSummarize = new ArrayList<>();

        for(CarEntity c: carList){
            CarEntity aux = new CarEntity();
            aux.setBrand(c.getBrand());
            aux.setModel(c.getModel());
            aux.setImage(c.getImage());
            carListSummarize.add(aux);
        }

        return carListSummarize;
    }

}
