package com.example.locationapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.locationapplication.models.LocationVoiture;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.FirebaseAppCheckTokenProvider;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addLocationBtn;
    ImageButton menuBtn;
    RecyclerView recyclerView;
    LocationVoitureAdapter locationVoitureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addLocationBtn = findViewById(R.id.add_location_btn);
        menuBtn = findViewById(R.id.menu_btn);
        recyclerView = findViewById(R.id.recyler_view);


        addLocationBtn.setOnClickListener((v) -> startActivity( new Intent(MainActivity.this, LocationVoitureCreateActivity.class)) );
        menuBtn.setOnClickListener((v) ->showMenu() );
        setupRecyclerView();

    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, menuBtn);
        popupMenu.getMenu().add("Profil");
        popupMenu.getMenu().add("Se déconcecter");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle() == "Se déconcecter") {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                } if (menuItem.getTitle() == "Profil") {
                    startActivity(new Intent(MainActivity.this, ProfilActivity.class));
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