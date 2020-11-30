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

}
