package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.Common.Common;
import com.example.hp.myapplication.Model.Order;
import com.example.hp.myapplication.R;
import com.example.hp.myapplication.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;

    RecyclerView recycle_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");

        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent cart = new Intent(Home.this, Cart.class);
            startActivity(cart);
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        txtFullName = headerview.findViewById(R.id.txtFullName);
        if (Common.currentUser != null)
            txtFullName.setText(Common.currentUser.getName() == null ? "Tester" : Common.currentUser.getName());
        else txtFullName.setText("Tester");
        recycle_menu = findViewById(R.id.recycler_menu);
        recycle_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycle_menu.setLayoutManager(layoutManager);
        loadMenu();
    }

    private void loadMenu() {
        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(category, Category.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_list, parent, false);
                return new MenuViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder viewHolder, int position, @NonNull Category model) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.imageView);
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                viewHolder.setItemClickListener((view, position1, isLongClick) -> {
                    Intent foodlist = new Intent(Home.this, FoodList.class);
                    foodlist.putExtra("CategoryId", adapter.getRef(position1).getKey());
                    startActivity(foodlist);
                });
            }
        };
        recycle_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Intent intent = new Intent(Home.this, Menu.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {
            Intent intent2 = new Intent(Home.this, Cart.class);
            startActivity(intent2);
        } else if (id == R.id.nav_orders) {
            Intent intent3 = new Intent(Home.this, Order.class);
            startActivity(intent3);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
