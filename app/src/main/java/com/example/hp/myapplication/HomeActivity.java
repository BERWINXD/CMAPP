package com.example.hp.myapplication;

import static com.example.hp.myapplication.R.id;
import static com.example.hp.myapplication.R.layout;
import static com.example.hp.myapplication.R.string;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hp.myapplication.Common.Common;
import com.example.hp.myapplication.Interface.ItemClickListener;
import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.Model.Order;
import com.example.hp.myapplication.Model.User;
import com.example.hp.myapplication.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullName;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);
        Toolbar toolbar = findViewById(id.toolbar);
        toolbar.setTitle("Menu");

        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        FloatingActionButton fab = findViewById(id.fab);
        fab.setOnClickListener(view -> {
            Intent cart = new Intent(HomeActivity.this, ShowCart.class);
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


        recycler_menu = (RecyclerView) findViewById(id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();


    }

    private void loadMenu() {
        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(category, Category.class)
                        .build();

        FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter =
                new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Category model) {
                        holder.txtMenuName.setText(model.getName());
                        Glide.with(HomeActivity.this).load(model.getImage()).into(holder.imageView);
                        Category clickitem = model;
                        holder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Toast.makeText(HomeActivity.this, ""+clickitem.getName(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list, parent, false);
                        return new MenuViewHolder(view);
                    }
                };



        recycler_menu.setAdapter(adapter);



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
                startActivity(new Intent(HomeActivity.this, ShowCart.class));
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

