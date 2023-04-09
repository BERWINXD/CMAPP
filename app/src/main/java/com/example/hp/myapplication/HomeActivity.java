package com.example.hp.myapplication;

import static com.example.hp.myapplication.R.id;
import static com.example.hp.myapplication.R.layout;
import static com.example.hp.myapplication.R.string;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hp.myapplication.Common.Common;
import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.Model.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullName;

    FirebaseAuth mAuth;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    private List<Category> categories;

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);
        Toolbar toolbar = findViewById(id.toolbar);
        toolbar.setTitle("Menu");

        setSupportActionBar(toolbar);

        category = FirebaseDatabase.getInstance().getReference("Category");
        categories = new ArrayList<>();

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

        recycler_menu = findViewById(id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();
    }

    private void loadMenu() {

        category.get().addOnCompleteListener(taskCompletion -> {
            if (taskCompletion.isSuccessful()) {
                Map<String, Object> message = (Map<String, Object>) taskCompletion.getResult().getValue();
                if (message != null) {
                    for (Object o : message.values()) {
                        String s = String.valueOf(o);
                        String[] params = s.substring(1, s.length() - 1).split(",");
                        Category category = new Category(
                                params[1].trim().split("=")[1],
                                params[0].substring(6)
                        );
                        categories.add(category);
                        Log.d(TAG, params[0].substring(6) + " : " + params[1] + " : " + params.length);
                    }
                    recycler_menu.setAdapter(new CategoryAdapter(categories));
                }
            }
        });
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

    public void navigateIntent(String itemNo) {
        Intent intent = new Intent(HomeActivity.this, FoodList.class);
        intent.putExtra("item", itemNo);
        startActivity(intent);
    }

}

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Category> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;
        private final CardView cardView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(id.menu_name);
            imageView = view.findViewById(id.menu_image);
            cardView = view.findViewById(id.categorywrapper);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public CardView getCardView() {
            return cardView;
        }
    }

    public CategoryAdapter(List<Category> localDataSet) {
        this.localDataSet = localDataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(localDataSet.get(position).getName());
        Glide.with(viewHolder.imageView.getContext()).load(localDataSet.get(position).getImage()).into(viewHolder.getImageView());
        viewHolder.getCardView().setOnClickListener(listener -> {
            Intent intent = new Intent(listener.getContext(), FoodList.class);
            intent.putExtra("item", localDataSet.get(position).getName());
            listener.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
