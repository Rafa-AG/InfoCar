package com.ralba.infocarapp.presenters;

import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.FormInterface;
import com.ralba.infocarapp.views.MyApplication;

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
    public void onClickDelete() {
        view.closeFormActivity();
    }

    @Override
    public String getError(String errorCode){
        String errorMSG="";
        switch (errorCode){
            case "CarBrand":
                errorMSG=MyApplication.getContext().getResources().getString(R.string.incorrect_brand);
                break;
            case "CarModel":
                errorMSG=MyApplication.getContext().getResources().getString(R.string.incorrect_model);
                break;
            case "CarHP":
                errorMSG=MyApplication.getContext().getResources().getString(R.string.incorrect_hp);
                break;
            case "CarDescription":
                errorMSG=MyApplication.getContext().getResources().getString(R.string.incorrect_description);
                break;
            case "CarLaunchDate":
                errorMSG=MyApplication.getContext().getResources().getString(R.string.incorrect_launch_date);
                break;
        }
        return errorMSG;
    }

}
