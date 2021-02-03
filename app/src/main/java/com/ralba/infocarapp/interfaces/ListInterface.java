package com.ralba.infocarapp.interfaces;

import com.ralba.infocarapp.models.CarEntity;

import java.util.ArrayList;
import java.util.Date;

public interface ListInterface {

    public interface View{
        void startFormActivity();
        void startAboutActivity();
        void startSearchActivity();
        void startFormActivity(CarEntity car);
    }

    public interface Presenter{
        void onClickAddCar();
        void onClickAboutUs();
        void onClickSearch();
        void onClickRecyclerViewItem(CarEntity car);
        ArrayList<CarEntity> getAllItemsSummarize();
        ArrayList<CarEntity> getAllByFilter(String brand, String motor, Date date);
        ArrayList<String> getMotorTypes();
    }

}
