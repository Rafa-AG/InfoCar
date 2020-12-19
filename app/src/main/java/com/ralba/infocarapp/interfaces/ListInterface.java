package com.ralba.infocarapp.interfaces;

public interface ListInterface {

    public interface View{
        void startFormActivity();
        void startAboutActivity();
        void startSearchActivity();
        void startFormActivity(String id);
    }

    public interface Presenter{
        void onClickAddCar();
        void onClickAboutUs();
        void onClickSearch();
        void onClickRecyclerViewItem(String id);
    }

}
