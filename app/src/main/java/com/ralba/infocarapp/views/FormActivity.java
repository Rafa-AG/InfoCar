package com.ralba.infocarapp.views;

import android.app.DatePickerDialog;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

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

        Button saveButton = findViewById(R.id.saveButton);
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

        ImageView buttonLaunchDate=findViewById(R.id.image_search_calendar);
        buttonLaunchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDate=new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        launchDateET.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                    }
                }, year, month, day);
                launchDate.show();
            }
        });

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

}