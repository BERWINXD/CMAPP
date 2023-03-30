package com.example.hp.myapplication;

import static com.example.hp.myapplication.R.id;
import static com.example.hp.myapplication.R.layout;
import static com.example.hp.myapplication.R.string;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hp.myapplication.Common.Common;
import com.example.hp.myapplication.Model.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullName;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);
        Toolbar toolbar = findViewById(id.toolbar);
        toolbar.setTitle("Menu");

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(id.fab);
        fab.setOnClickListener(view -> {
            Intent cart = new Intent(HomeActivity.this, Cart.class);
            startActivity(cart);
        });

        DrawerLayout drawer = findViewById(id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(id.txtFullName);
        if (Common.currentUser != null)
            txtFullName.setText(Common.currentUser.getName() == null ? "Tester" : Common.currentUser.getName());
        else txtFullName.setText("Tester");


        findViewById(id.item1).setOnClickListener(li -> navigateIntent("NON-VEG"));
        findViewById(id.item2).setOnClickListener(li -> navigateIntent("VEG"));
        findViewById(id.item3).setOnClickListener(li -> navigateIntent("Beverage"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case id.nav_cart:
                startActivity(new Intent(HomeActivity.this, Cart.class));
                break;
            case id.nav_orders:
                startActivity(new Intent(HomeActivity.this, Order.class));
                break;
            case id.nav_signout:
                FirebaseAuth.getInstance().signOut();

                break;
            default:
                throw new IllegalStateException("Unexpected value");
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateIntent(String itemNo) {
        Intent intent = new Intent(HomeActivity.this, FoodList.class);
        intent.putExtra("item", itemNo);
        startActivity(intent);
    }

}
