package com.example.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.locationapplication.models.LocationVoiture;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class ListLocationActivity extends AppCompatActivity {

    FloatingActionButton addLocationBtn;
    ImageButton menuBtn;
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    LocationVoitureAdapter locationVoitureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addLocationBtn = findViewById(R.id.add_location_btn);
        menuBtn = findViewById(R.id.menu_btn);
        recyclerView = findViewById(R.id.recyler_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setSelectedItemId(R.id.home);


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(), ContactActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        addLocationBtn.setOnClickListener((v) -> startActivity( new Intent(ListLocationActivity.this, LocationVoitureCreateActivity.class)) );
        menuBtn.setOnClickListener((v) ->showMenu() );
        setupRecyclerView();

    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(ListLocationActivity.this, menuBtn);
        popupMenu.getMenu().add("Profil");
        popupMenu.getMenu().add("Mes annonces");
        popupMenu.getMenu().add("Se déconcecter");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle() == "Se déconcecter") {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(ListLocationActivity.this, LoginActivity.class));
                    finish();
                } if (menuItem.getTitle() == "Profil") {
                    startActivity(new Intent(ListLocationActivity.this, ProfilActivity.class));
                }
                if (menuItem.getTitle() == "Mes annonces") {
                    startActivity(new Intent(ListLocationActivity.this, myAdsActivity.class));
                }
                return false;
            }
        });
    }

    void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForLocationVoiture().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<LocationVoiture> options = new FirestoreRecyclerOptions.Builder<LocationVoiture>()
                .setQuery(query, LocationVoiture.class)
                .build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationVoitureAdapter = new LocationVoitureAdapter(options, this);
        recyclerView.setAdapter(locationVoitureAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationVoitureAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationVoitureAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationVoitureAdapter.notifyDataSetChanged();
    }
}