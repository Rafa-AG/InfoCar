package com.ralba.infocarapp.views;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.FormInterface;
import com.ralba.infocarapp.models.CarEntity;
import com.ralba.infocarapp.presenters.FormPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;

    private EditText brandET;
    private TextInputLayout brandTIL;

    private EditText modelET;
    private TextInputLayout modelTIL;

    private EditText cvET;
    private TextInputLayout cvTIL;

    private EditText descriptionET;
    private TextInputLayout descriptionTIL;

    private EditText launchDateET;
    private TextInputLayout launchDateTIL;

    private CarEntity car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Spinner spinner = (Spinner) findViewById(R.id.motor_type);
        String[] type = {getResources().getString(R.string.motor_type), getResources().getString(R.string.gasoline), getResources().getString(R.string.gasoil), getResources().getString(R.string.electric), getResources().getString(R.string.hybrid)};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    Toast.makeText(spinner.getContext(), "Has seleccionado "+spinner.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(spinner.getContext(), "Nada seleccionado", Toast.LENGTH_LONG).cancel();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.onClickSaveCar();
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickCancel();
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

        cvET=findViewById(R.id.car_cvET);
        cvTIL=findViewById(R.id.car_cvTIL);

        cvET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!car.setCV(cvET.getText().toString())){
                        cvTIL.setError(presenter.getError(MyApplication.getContext().getResources().getString(R.string.CarCV)));
                    }else{
                        cvTIL.setError(null);
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
                if(!hasFocus){
                    if(!car.setLaunchDate(launchDateET.getText().toString())){
                        launchDateTIL.setError(presenter.getError(MyApplication.getContext().getResources().getString(R.string.CarLaunchDate)));
                    }else{
                        launchDateTIL.setError(null);
                    }
                }
            }
        });

    }

    @Override
    public void closeFormActivity() {
        finish();
    }

}