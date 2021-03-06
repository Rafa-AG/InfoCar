package com.ralba.infocarapp.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface SearchInterface {

    public interface View{
        void closeSearchActivity();
        void searchCar();
        void startHelpActivity();
    }

    public interface Presenter{
        void onClickSearch();
        void onClickHelp();
        ArrayList<String> getMotorTypes();
    }

}
