package com.ralba.infocarapp.interfaces;

import android.app.Activity;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ralba.infocarapp.models.CarEntity;

import java.util.ArrayList;

public interface FormInterface {

    public interface View{
        void closeFormActivity();
        void selectImageFromGallery();
        void showRequestPermission();
        void showError();
        void cleanImage();
        void saveCar();
        void deleteCar();
        void startHelpActivity();
    }

    public interface Presenter{
        void onClickSaveCar(CarEntity car);
        void onClickDelete(String id);
        String getError(String errorCode);
        void onClickImage();
        void permissionGranted();
        void permissionDenied();
        void onClickClean();
        void onClickHelp();
        ArrayList<String> getMotorTypes();
        CarEntity getCarById(String id);
    }

}
