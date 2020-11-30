package com.ralba.infocarapp.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.google.android.material.textfield.TextInputLayout;
import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.SearchInterface;
import com.ralba.infocarapp.presenters.SearchPresenter;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity implements SearchInterface.View {

    private SearchInterface.Presenter presenter;

    private Context myContext;

    private EditText launchDateET;
    private TextInputLayout launchDateTIL;

    private Calendar calendar;
    private DatePickerDialog launchDate;
    private int year, month, day;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        myContext=this;

        presenter=new SearchPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.search_vehicle);
            toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        launchDateET=findViewById(R.id.search_launch_dateET);
        launchDateTIL=findViewById(R.id.search_launch_dateTIL);

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

        launchDateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                launchDateTIL.setHelperText(MyApplication.getContext().getResources().getString(R.string.help_date));
                launchDateTIL.setHelperTextColor(MyApplication.getContext().getResources().getColorStateList(R.color.ic_launcher_background));
                if(!hasFocus){
                    launchDateTIL.setHelperText(null);
                }
            }
        });

        ArrayList<String> items=new ArrayList<>();
        items.add(getResources().getString(R.string.motor_type));
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

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickSearch();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public void closeSearchActivity() {
        finish();
    }
}