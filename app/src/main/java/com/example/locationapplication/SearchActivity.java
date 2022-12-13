package com.example.locationapplication;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SearchActivity extends AppCompatActivity {
    ImageButton imageButton, menuBtn;

    BottomNavigationView bottomNavigationView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageButton = findViewById(R.id.Button_recherche);
        menuBtn = findViewById(R.id.menu_btn);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        menuBtn.setOnClickListener((v) ->showMenu());

        bottomNavigationView.setSelectedItemId(R.id.search);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), ListLocationActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(), ContactActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(SearchActivity.this, menuBtn);
        popupMenu.getMenu().add("Profil");
        popupMenu.getMenu().add("Mes annonces");
        popupMenu.getMenu().add("Se déconcecter");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle() == "Se déconcecter") {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(SearchActivity.this, LoginActivity.class));
                    finish();
                } if (menuItem.getTitle() == "Profil") {
                    startActivity(new Intent(SearchActivity.this, ProfilActivity.class));
                }
                if (menuItem.getTitle() == "Mes annonces") {
                    startActivity(new Intent(SearchActivity.this, myAdsActivity.class));
                }
                return false;
            }
        });
    }
}