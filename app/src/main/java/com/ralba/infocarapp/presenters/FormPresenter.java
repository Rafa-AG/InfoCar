package com.ralba.infocarapp.presenters;

import com.ralba.infocarapp.interfaces.FormInterface;

public class FormPresenter implements FormInterface.Presenter {

    private FormInterface.View view;

    public FormPresenter(FormInterface.View view){
        this.view=view;
    }

    @Override
    public void onClickSaveCar() {
        view.closeFormActivity();
    }

    @Override
    public void onClickCancel() {
        view.closeFormActivity();
    }

}
