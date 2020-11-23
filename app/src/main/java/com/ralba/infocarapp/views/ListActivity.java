package com.ralba.infocarapp.views;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ralba.infocarapp.R;
import com.ralba.infocarapp.interfaces.ListInterface;
import com.ralba.infocarapp.presenters.ListPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class ListActivity extends AppCompatActivity implements ListInterface.View {

    String TAG="InfoCarApp/ListActivity";
    private ListInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new ListPresenter(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on Floating Button");
                presenter.onClickAddCar();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Log.d(TAG, "Menu Search click");
            presenter.onClickFloatingButton();
            return true;
        }

        if(id==R.id.about_us){
            Log.d(TAG, "Menu About Us click");
            presenter.onClickAboutUs();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startFormActivity() {
        Intent intent=new Intent(getApplicationContext(), FormActivity.class);
        startActivity(intent);
    }

    @Override
    public void startAboutActivity() {
        Intent intent=new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

}