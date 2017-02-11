package com.android.test.application;

import android.app.Application;

import com.android.test.database.CategoryHelper;
import com.backendless.Backendless;
import com.facebook.stetho.Stetho;

/**
 * Created by Manish on 9/2/17.
 */

public class MyApplication extends Application {


    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created. Implementations should be as quick as
     * possible (for example using lazy initialization of state) since the time spent in this
     * function directly impacts the performance of starting the first activity, service, or
     * receiver in a process. If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        CategoryHelper categoryHelper = new CategoryHelper(this);
        String appVersion = "v1";
        Backendless.initApp(this, "3534C93F-A336-1452-FF85-C1E302895100", "5A13616E-D5FD-0F2D-FFC8-EB71B9F9B400", appVersion);
    }
}
