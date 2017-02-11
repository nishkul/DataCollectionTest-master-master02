package com.android.test.ui.screen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.database.EntryHelper;
import com.android.test.model.Data;
import com.android.test.model.Entry;
import com.android.test.ui.screen.fragments.EntryViewFragment;
import com.backendless.Backendless;
import com.backendless.async.callback.BackendlessCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;

    private EntryHelper entryHelper;
    private ArrayList<Entry> arrylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            startActivity(new Intent(MainActivity.this, MapActivity.class));
        } else if (id == R.id.nav_view) {
            fragment = new EntryViewFragment();
            replaceFragment(fragment);
        } else if (id == R.id.nav_manage) {
            sendDataToServer();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendDataToServer() {


        Data data = new Data();
        entryHelper = new EntryHelper(this);
        arrylist = entryHelper.getAllEntryToServerSend();
        if (arrylist.size() > 0) {
            data.setEntryArrayList(arrylist);
            Backendless.Persistence.save(data, new BackendlessCallback<Data>() {
                @Override
                public void handleResponse(Data comment) {
                    for (Entry entry : arrylist) {
                        entryHelper.getEntrySendToServer(String.valueOf(entry.getId()));
                        Log.v("send", "Data send to server ");
                    }
                    Toast.makeText(MainActivity.this, "Data send to server", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(MainActivity.this, "No data found..", Toast.LENGTH_SHORT).show();
        }


    }

    private void replaceFragment(Fragment fragment) {
//        String backStateName = fragment.getClass().getName();
//
//        FragmentManager manager = getSupportFragmentManager();
//        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
//
//        if (!fragmentPopped) { //fragment not in back stack, create it.
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.container_body, fragment);
//            ft.addToBackStack(backStateName);
//            ft.commit();
//        }


        if (getSupportFragmentManager().findFragmentById(R.id.container_body) != null) {
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.container_body)).commit();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_body, fragment)
                .commit();

    }


}
