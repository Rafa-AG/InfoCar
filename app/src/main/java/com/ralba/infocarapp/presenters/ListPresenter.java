package com.ralba.infocarapp.presenters;

import androidx.recyclerview.widget.RecyclerView;

import com.ralba.infocarapp.interfaces.ListInterface;
import com.ralba.infocarapp.models.CarEntity;
import com.ralba.infocarapp.models.CarModel;

import java.util.ArrayList;
import java.util.Date;

public class ListPresenter implements ListInterface.Presenter{

    private ListInterface.View view;
    private CarModel model;

    public ListPresenter(ListInterface.View view){
        this.view=view;
        this.model=new CarModel();
    }

    @Override
    public void onClickAddCar() {
        view.startFormActivity();
    }

    @Override
    public void onClickAboutUs() {
        view.startAboutActivity();
    }

    @Override
    public void onClickSearch() {
        view.startSearchActivity();
    }

    @Override
    public void onClickRecyclerViewItem(String id) {
        view.startFormActivity(id);
    }

    @Override
    public void removeCar(RecyclerView.ViewHolder viewHolder, String id) {
        model.deleteCarById(id);
        view.onSwipeRemove(viewHolder);
    }

    @Override
    public ArrayList<CarEntity> getAllItemsSummarize(){
        return model.getAllSummarize();
    }

    @Override
    public ArrayList<CarEntity> getAllByFilter(String brand, String motor, Date date) {
        return model.getAllByFilter(brand, motor, date);
    }

    @Override
    public ArrayList<String> getMotorTypes() {
        return model.getAllMotorTypes();
    }

}
