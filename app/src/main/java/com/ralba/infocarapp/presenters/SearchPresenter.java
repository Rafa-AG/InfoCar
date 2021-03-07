package com.ralba.infocarapp.presenters;

import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.SearchInterface;
import com.ralba.infocarapp.models.CarModel;
import com.ralba.infocarapp.views.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements SearchInterface.Presenter{

    private SearchInterface.View view;
    private CarModel model;

    public SearchPresenter(SearchInterface.View view){
        this.view=view;
        this.model=new CarModel();
    }

    @Override
    public void onClickSearch() {
        view.searchCar();
    }

    @Override
    public void onClickHelp() {
        view.startHelpActivity();
    }

    @Override
    public ArrayList<String> getMotorTypes() {
        return model.getAllMotorTypes();
    }

}
