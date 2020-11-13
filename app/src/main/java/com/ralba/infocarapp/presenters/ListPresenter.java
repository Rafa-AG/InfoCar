package com.ralba.infocarapp.presenters;

import com.ralba.infocarapp.interfaces.ListInterface;

public class ListPresenter implements ListInterface.Presenter{

    private ListInterface.View view;

    public ListPresenter(ListInterface.View view){
        this.view=view;
    }

    @Override
    public void onClickAddCar() {
        view.starFormActivity();
    }

}