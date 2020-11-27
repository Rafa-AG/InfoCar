package com.ralba.infocarapp.presenters;

import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.SearchInterface;
import com.ralba.infocarapp.views.MyApplication;

public class SearchPresenter implements SearchInterface.Presenter{

    private SearchInterface.View view;

    public SearchPresenter(SearchInterface.View view){
        this.view=view;
    }

    @Override
    public void onClickSearch() {
        view.closeSearchActivity();
    }

    @Override
    public String getError(String errorCode){
        String errorMSG="";
        switch (errorCode){
            case "CarBrand":
                errorMSG= MyApplication.getContext().getResources().getString(R.string.incorrect_brand);
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
