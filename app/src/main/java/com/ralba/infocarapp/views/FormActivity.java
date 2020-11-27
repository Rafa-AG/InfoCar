package com.ralba.infocarapp.views;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;
import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.FormInterface;
import com.ralba.infocarapp.models.CarEntity;
import com.ralba.infocarapp.presenters.FormPresenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;

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

    private Context myContext;
    private ArrayAdapter<String> adapter;

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
                    Toast.makeText(spinner.getContext(), "Has seleccionado "+spinner.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }else if(position==1){
                    addMotorType(spinner);
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

    public void addMotorType(Spinner spinner){
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

        alertDialog.setView(viewAlertDialog);

        final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

        alertDialog.setCancelable(false).setPositiveButton(getResources().getString(R.string.add),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                adapter.add(dialogInput.getText().toString());
                                spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        }).create().show();
    }
}