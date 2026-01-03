package com.nikahtech.muslimnikah;

import android.app.Application;

import com.nikahtech.muslimnikah.keystores.LocalDBManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalDBManager.initialize(this);
    }
}
