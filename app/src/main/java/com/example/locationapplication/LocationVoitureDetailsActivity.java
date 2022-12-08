package com.example.locationapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocationVoitureDetailsActivity extends AppCompatActivity {

    TextView pageTitreTextView, marqueTextView, modeleTextView,versionTextView, placeTextView, boiteVitesseTextView , carburantTexView, prixJournalierTextView, villeTextView, statutTextView, dateTimestampTextView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String marque, modele, version, place, boiteVitesse, carburant, ville, statut, docId, uid;
    Float prixJournalier;
    Timestamp dateTimestamp;
    ImageButton backBtn, editLocationBtn, deleteLocationBtn;
    LinearLayout reservationBtn;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voiturelocation_details);
        marqueTextView = findViewById(R.id.marque_details_text_view);
        pageTitreTextView = findViewById(R.id.page_title);
        prixJournalierTextView = findViewById(R.id.prixJournalier_details_text_view);
        dateTimestampTextView = findViewById(R.id.locationVoiture_timestamp_details_text_view);
        modeleTextView = findViewById(R.id.model_details_text_view);
        versionTextView = findViewById(R.id.version_details_text_view);
        placeTextView = findViewById(R.id.place_details_text_view);
        carburantTexView = findViewById(R.id.carburant_details_text_view);
        boiteVitesseTextView = findViewById(R.id.boiteVitesse_details_text_view);
        villeTextView = findViewById(R.id.ville_details_text_view);
        statutTextView = findViewById(R.id.statut_details_text_view);
        reservationBtn = findViewById(R.id.reserver_btn);
        editLocationBtn = findViewById(R.id.edit_location_btn);
        backBtn = findViewById(R.id.back_btn);
        deleteLocationBtn = findViewById(R.id.delete_location_btn);


        backBtn.setOnClickListener((v) -> {
            startActivity(new Intent(LocationVoitureDetailsActivity.this, ListLocationActivity.class));
        });

        reservationBtn.setOnClickListener(v -> {
            startActivity(new Intent(LocationVoitureDetailsActivity.this, ReservationActivity.class));
            Toast.makeText(LocationVoitureDetailsActivity.this, "Réservation en cours...", Toast.LENGTH_SHORT).show();
        });

        deleteLocationBtn.setOnClickListener(v -> {
            firebaseFirestore.collection("locationVoiture").document(docId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LocationVoitureDetailsActivity.this, "Suppression réussie", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LocationVoitureDetailsActivity.this, ListLocationActivity.class));
                    } else {
                        Toast.makeText(LocationVoitureDetailsActivity.this, "Erreur = " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        editLocationBtn.setOnClickListener(v ->{
            startActivity(new Intent(LocationVoitureDetailsActivity.this, EditLocationVoitureActivity.class));
            Toast.makeText(LocationVoitureDetailsActivity.this, "Modification en cours...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LocationVoitureDetailsActivity.this, EditLocationVoitureActivity.class);
            intent.putExtra("docId", docId);
            intent.putExtra("marque", marque);
            startActivity(intent);
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        deleteLocationBtn.setVisibility(View.GONE);
        editLocationBtn.setVisibility(View.GONE);

        if (firebaseAuth.getCurrentUser() != null) {
            docId = getIntent().getStringExtra("docId");
        } else {
            Toast.makeText(this, "Vous n'avez pas accès", Toast.LENGTH_SHORT).show();
        }

        firebaseFirestore.collection("locationVoiture").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        marque = documentSnapshot.getString("marque");
                        modele = documentSnapshot.getString("modele");
                        version = documentSnapshot.getString("version");
                        place = documentSnapshot.getString("place");
                        boiteVitesse = documentSnapshot.getString("boiteVitesse");
                        carburant = documentSnapshot.getString("carburant");
                        prixJournalier = documentSnapshot.get("prixJournalier", Float.class);
                        ville = documentSnapshot.getString("ville");
                        statut = documentSnapshot.getString("statut");
                        dateTimestamp = documentSnapshot.getTimestamp("timestamp");
                        uid = documentSnapshot.getString("userId");

                        marqueTextView.setText(marque);
                        modeleTextView.setText(modele);
                        versionTextView.setText(version);
                        placeTextView.setText(place);
                        boiteVitesseTextView.setText(boiteVitesse);
                        carburantTexView.setText(carburant);
                        prixJournalierTextView.setText(prixJournalier.toString());
                        villeTextView.setText(ville);
                        statutTextView.setText(statut);
                        dateTimestampTextView.setText(Utility.timestampToString(dateTimestamp));

                        if (currentUser.getUid().equals(uid)) { // Test currentUser et userId de la task
                            deleteLocationBtn.setVisibility(View.VISIBLE);
                            editLocationBtn.setVisibility(View.VISIBLE);
                        } else {
                            deleteLocationBtn.setVisibility(View.GONE);
                            editLocationBtn.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(LocationVoitureDetailsActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LocationVoitureDetailsActivity.this, "Erreur = " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
