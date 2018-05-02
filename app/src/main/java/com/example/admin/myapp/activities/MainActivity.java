package com.example.admin.myapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapp.R;
import com.example.admin.myapp.fragments.ProductFragment;

import static com.example.admin.myapp.utils.Constants.IS_LOGGED_OUT;
import static com.example.admin.myapp.utils.Constants.PREFS_NAME;
import static com.example.admin.myapp.utils.Constants.TOKEN;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean(IS_LOGGED_OUT, true)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View header = navigationView.getHeaderView(0);
            TextView userTextView = header.findViewById(R.id.user_name_nav);
            userTextView.setText(prefs.getString("Android", "Android"));
            displaySelectedScreen(999);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public void displaySelectedScreen(int itemId) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.print_order:
                startActivity(new Intent(this, PrintOrderActivity.class));
                break;
            case R.id.my_cart:
                startActivity(new Intent(this, CartListActivity.class));
                break;
            case R.id.my_wishlist:
                startActivity(new Intent(this, WishlistActivity.class));
                break;
            case R.id.nav_share:
                Toast.makeText(getApplicationContext(), "Share Application", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(getApplicationContext(), "Send Application", Toast.LENGTH_SHORT).show();
                break;
            case 999:
                ProductFragment productFragment = new ProductFragment();
                ft.replace(R.id.content_frame, productFragment);
                ft.commit();
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


}
