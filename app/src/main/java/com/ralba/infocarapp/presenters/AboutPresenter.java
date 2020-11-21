package com.ralba.infocarapp.presenters;

import com.ralba.infocarapp.interfaces.AboutInterface;

public class AboutPresenter implements AboutInterface.Presenter {

    private AboutInterface.View view;

    public AboutPresenter(AboutInterface.View view){
        this.view=view;
    }

}
