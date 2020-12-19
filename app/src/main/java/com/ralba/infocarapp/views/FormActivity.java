package com.ralba.infocarapp.views;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.FormInterface;
import com.ralba.infocarapp.models.CarEntity;
import com.ralba.infocarapp.presenters.FormPresenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;

    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private static final int REQUEST_SELECT_IMAGE = 201;

    private ConstraintLayout constraintLayoutFormActivity;

    private String id;

    private EditText brandET;
    private TextInputLayout brandTIL;

    private EditText modelET;
    private TextInputLayout modelTIL;

    private EditText hpET;
    private TextInputLayout hpTIL;

    private EditText descriptionET;
    private TextInputLayout descriptionTIL;

    private EditText launchDateET;
    private TextInputLayout launchDateTIL;

    private CarEntity car;

    private ImageView formImage;

    private Context myContext;
    private ArrayAdapter<String> adapter;
    private Calendar calendar;
    private DatePickerDialog launchDate;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext=this;
        setContentView(R.layout.activity_form);

        presenter = new FormPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.add_vehicle);
            toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        formImage = findViewById(R.id.form_image);

        constraintLayoutFormActivity=findViewById(R.id.constraintLayoutFormActivity);

        formImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickImage();
            }
        });

        Button cleanButton=findViewById(R.id.clean_button);

        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickClean();
            }
        });

        ArrayList<String> items=new ArrayList<>();
        items.add(getResources().getString(R.string.motor_type));
        items.add(getResources().getString(R.string.add_motor_type));
        items.add(getResources().getString(R.string.gasoline));
        items.add(getResources().getString(R.string.diesel));
        items.add(getResources().getString(R.string.electric));
        items.add(getResources().getString(R.string.hybrid));

        adapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Spinner spinner=(Spinner) findViewById(R.id.search_motor);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>1){
                    Toast.makeText(spinner.getContext(), getResources().getString(R.string.have_selected)+" "+spinner.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }else if(position==1){
                    LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                    View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

                    alertDialog.setView(viewAlertDialog);

                    final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

                    alertDialog.setCancelable(false).setPositiveButton(getResources().getString(R.string.add),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    if(dialogInput.getText().toString().equals("")){
                                        Toast.makeText(myContext, getResources().getString(R.string.nothing_added), Toast.LENGTH_LONG).show();
                                        spinner.setSelection(adapter.getPosition(getResources().getString(R.string.motor_type)));
                                    }else{
                                        adapter.add(dialogInput.getText().toString());
                                        spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    }
                                }
                            }).setNegativeButton(getResources().getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    spinner.setSelection(adapter.getPosition(getResources().getString(R.string.motor_type)));
                                    dialogBox.cancel();
                                }
                            }).create().show();
                }else{
                    Toast.makeText(spinner.getContext(), getResources().getString(R.string.nothing_selected), Toast.LENGTH_LONG).cancel();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.onClickSaveCar();
            }
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCar();
            }
        });

        car=new CarEntity();

        brandET=findViewById(R.id.car_brandET);
        brandTIL=findViewById(R.id.car_brandTIL);

        brandET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!car.setBrand(brandET.getText().toString())){
                        brandTIL.setError(presenter.getError(MyApplication.getContext().getResources().getString(R.string.CarBrand)));
                    }else{
                        brandTIL.setError(null);
                    }
                }
            }
        });

        modelET=findViewById(R.id.car_modelET);
        modelTIL=findViewById(R.id.car_modelTIL);

        modelET.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus){
                    if(!car.setModel(modelET.getText().toString())){
                        modelTIL.setError(presenter.getError(MyApplication.getContext().getResources().getString(R.string.CarModel)));
                    }else{
                        modelTIL.setError(null);
                    }
                }
            }
        });

        hpET=findViewById(R.id.car_hpET);
        hpTIL=findViewById(R.id.car_hpTIL);

        hpET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(hpET.getText().toString().equals("") || !car.setHP(hpET.getText().toString())){
                        hpTIL.setError(presenter.getError(MyApplication.getContext().getResources().getString(R.string.CarHP)));
                    }else{
                        hpTIL.setError(null);
                    }
                }
            }
        });

        descriptionET=findViewById(R.id.car_descriptionET);
        descriptionTIL=findViewById(R.id.car_descriptionTIL);

        descriptionET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!car.setDescription(descriptionET.getText().toString())){
                        descriptionTIL.setError(presenter.getError(MyApplication.getContext().getResources().getString(R.string.CarDescription)));
                    }else{
                        descriptionTIL.setError(null);
                    }
                }
            }
        });

        launchDateET=findViewById(R.id.car_launchdateET);
        launchDateTIL=findViewById(R.id.car_launchdateTIL);

        launchDateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                launchDateTIL.setHelperText(MyApplication.getContext().getResources().getString(R.string.help_date));
                launchDateTIL.setHelperTextColor(MyApplication.getContext().getResources().getColorStateList(R.color.ic_launcher_background));
                if(!hasFocus){
                    if(!car.setLaunchDate(launchDateET.getText().toString())){
                        launchDateTIL.setError(presenter.getError(MyApplication.getContext().getResources().getString(R.string.CarLaunchDate)));
                    }else{
                        launchDateTIL.setError(null);
                        launchDateTIL.setHelperText(null);
                    }
                }
            }
        });

        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);

        ImageView buttonLaunchDate=findViewById(R.id.form_icon_calendar);
        buttonLaunchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDate=new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String date=String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);
                        launchDateET.setText(date);
                    }
                }, year, month, day);
                launchDate.show();
            }
        });

        id=getIntent().getStringExtra("id");

        if(id!=null){
            brandET.setText(id);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.permissionGranted();
                    Snackbar.make(constraintLayoutFormActivity, getResources().getString(R.string.write_permission_accepted), Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    presenter.permissionDenied();
                    Snackbar.make(constraintLayoutFormActivity, getResources().getString(R.string.write_permission_not_accepted), Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public void closeFormActivity() {
        finish();
    }

    @Override
    public void selectImageFromGallery() {
        Snackbar.make(constraintLayoutFormActivity, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG).show();
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),REQUEST_SELECT_IMAGE);
    }

    @Override
    public void showRequestPermission() {
        ActivityCompat.requestPermissions(FormActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
    }

    @Override
    public void showError() {
        Snackbar.make(constraintLayoutFormActivity, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void cleanImage() {
        formImage.setImageBitmap(null);
        formImage.setBackground(getDrawable(R.drawable.ic_lands));
    }

    private void deleteCar(){
        AlertDialog.Builder builder=new AlertDialog.Builder(FormActivity.this);
        builder.setTitle(getResources().getString(R.string.delete_car));
        builder.setMessage(getResources().getString(R.string.ask_deletion));

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onClickDelete();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                        Bitmap imageScaled = Bitmap.createScaledBitmap(bmp, 200, 200, false);

                        formImage.setBackground(null);

                        formImage.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

}