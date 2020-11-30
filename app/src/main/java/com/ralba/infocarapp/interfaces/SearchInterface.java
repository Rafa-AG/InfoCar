package com.ralba.infocarapp.interfaces;

public interface SearchInterface {

    public interface View{
        void closeSearchActivity();
    }

    public interface Presenter{
        void onClickSearch();
    }

}
