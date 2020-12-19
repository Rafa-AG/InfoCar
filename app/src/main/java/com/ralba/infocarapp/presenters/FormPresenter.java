package com.ralba.infocarapp.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.FormInterface;
import com.ralba.infocarapp.views.FormActivity;
import com.ralba.infocarapp.views.MyApplication;

public class FormPresenter implements FormInterface.Presenter {

    private FormInterface.View view;

    public FormPresenter(FormInterface.View view){
        this.view=view;
    }

    @Override
    public void onClickSaveCar() {
        view.closeFormActivity();
    }

    @Override
    public void onClickDelete() {
        view.closeFormActivity();
    }

    @Override
    public String getError(String errorCode){
        String errorMSG="";
        switch (errorCode){
            case "CarBrand":
                errorMSG=MyApplication.getContext().getResources().getString(R.string.incorrect_brand);
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

    @Override
    public void onClickImage() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d("FormActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);
        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                view.showRequestPermission();
            }
        } else {
            view.selectImageFromGallery();
        }
    }

    @Override
    public void permissionGranted() {
        view.selectImageFromGallery();
    }

    @Override
    public void permissionDenied() {
        view.showError();
    }

    @Override
    public void onClickClean() {
        view.cleanImage();
    }

}
