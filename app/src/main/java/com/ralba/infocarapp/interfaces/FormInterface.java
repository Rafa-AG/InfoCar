package com.ralba.infocarapp.interfaces;

public interface FormInterface {

    public interface View{
        void closeFormActivity();
    }

    public interface Presenter{
        void onClickSaveCar();
        void onClickCancel();
    }

}
