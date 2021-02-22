package com.ralba.infocarapp.interfaces;

import androidx.recyclerview.widget.RecyclerView;

import com.ralba.infocarapp.models.CarEntity;

import java.util.ArrayList;
import java.util.Date;

public interface ListInterface {

    public interface View{
        void startFormActivity();
        void startAboutActivity();
        void startSearchActivity();
        void startFormActivity(String id);
        void onSwipeRemove(RecyclerView.ViewHolder viewHolder);
    }

    public interface Presenter{
        void onClickAddCar();
        void onClickAboutUs();
        void onClickSearch();
        void onClickRecyclerViewItem(String id);
        void removeCar(RecyclerView.ViewHolder viewHolder, String id);
        ArrayList<CarEntity> getAllItemsSummarize();
        ArrayList<CarEntity> getAllByFilter(String brand, String motor, Date date);
        ArrayList<String> getMotorTypes();
    }

}
