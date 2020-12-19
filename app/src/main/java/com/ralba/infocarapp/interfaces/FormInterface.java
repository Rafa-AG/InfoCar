package com.ralba.infocarapp.interfaces;

import android.app.Activity;

import androidx.constraintlayout.widget.ConstraintLayout;

public interface FormInterface {

    public interface View{
        void closeFormActivity();
        void selectImageFromGallery();
        void showRequestPermission();
        void showError();
        void cleanImage();
    }

    public interface Presenter{
        void onClickSaveCar();
        void onClickDelete();
        String getError(String errorCode);
        void onClickImage();
        void permissionGranted();
        void permissionDenied();
        void onClickClean();
    }

}
