package com.ralba.infocarapp.interfaces;

public interface ListInterface {

    public interface View{
        void startFormActivity();
        void startAboutActivity();
    }

    public interface Presenter{
        void onClickAddCar();
        void onClickFloatingButton();
    }

}
