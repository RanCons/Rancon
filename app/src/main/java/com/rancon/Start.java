package com.rancon;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by kaishu on 4/2/18.
 */

public class Start extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (StaticMethods.isOnline(this)) {

        }
    }
}
