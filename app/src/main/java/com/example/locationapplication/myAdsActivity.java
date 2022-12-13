package com.example.locationapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.locationapplication.models.LocationVoiture;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Query;

public class myAdsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LocationVoitureAdapter locationVoitureAdapter;
    String userId;
    ImageButton backBtn;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        recyclerView = findViewById(R.id.recyler_view);
        backBtn = findViewById(R.id.back_btn);
        setupRecyclerView();

        backBtn.setOnClickListener(v -> {
            finish();
        });

    }

    void setupRecyclerView(){
        userId = currentUser.getUid();
        Query query = Utility.getCollectionReferenceForLocationVoiture().whereEqualTo("userId", userId).orderBy("timestamp", Query.Direction.DESCENDING);
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