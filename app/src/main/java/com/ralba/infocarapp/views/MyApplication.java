package com.ralba.infocarapp.views;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate(){
        super.onCreate();
        sContext=getApplicationContext();

        Realm.init(sContext);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(config);

    }

    public static Context getContext(){
        return sContext;
    }

}
