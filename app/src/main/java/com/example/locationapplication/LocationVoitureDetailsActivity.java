package com.example.locationapplication;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locationapplication.models.LocationVoiture;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocationVoitureDetailsActivity extends AppCompatActivity {

    TextView pageTitreTextView, marqueTextView, modeleTextView,versionTextView, placeTextView, boiteVitesseTextView , carburantTexView, prixHoraireTextView, prixJournalierTextView, villeTextView, statutTextView, dateTimestampTextView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String marque, modele, version, place, boiteVitesse, carburant, ville, statut, docId;
    Float prixHoraire, prixJournalier;
    Timestamp dateTimestamp;



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voiturelocation_details);
        marqueTextView = findViewById(R.id.marque_details_text_view);
        pageTitreTextView = findViewById(R.id.page_title);
        prixHoraireTextView = findViewById(R.id.prixHoraire_details_text_view);
        prixJournalierTextView = findViewById(R.id.prixJournalier_details_text_view);
        dateTimestampTextView = findViewById(R.id.locationVoiture_timestamp_details_text_view);
        modeleTextView = findViewById(R.id.model_details_text_view);
        versionTextView = findViewById(R.id.version_details_text_view);
        placeTextView = findViewById(R.id.place_details_text_view);
        carburantTexView = findViewById(R.id.carburant_details_text_view);
        boiteVitesseTextView = findViewById(R.id.boiteVitesse_details_text_view);
        villeTextView = findViewById(R.id.ville_details_text_view);
        statutTextView = findViewById(R.id.statut_details_text_view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            docId = getIntent().getStringExtra("docId");
            Toast.makeText(this, "User is logged in", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vous n'avez pas accès", Toast.LENGTH_SHORT).show();
        }

        firebaseFirestore.collection("locationVoiture").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()){
                        marque = documentSnapshot.getString("marque");
                        modele = documentSnapshot.getString("modele");
                        version = documentSnapshot.getString("version");
                        place = documentSnapshot.getString("place");
                        boiteVitesse = documentSnapshot.getString("boiteVitesse");
                        carburant = documentSnapshot.getString("carburant");
                        prixHoraire = documentSnapshot.get("prixHoraire", Float.class);
                        prixJournalier = documentSnapshot.get("prixJournalier", Float.class);
                        ville = documentSnapshot.getString("ville");
                        statut = documentSnapshot.getString("statut");
                        dateTimestamp = documentSnapshot.getTimestamp("timestamp");

                        Toast.makeText(LocationVoitureDetailsActivity.this, "Data is OKAY2", Toast.LENGTH_SHORT).show();
                        marqueTextView.setText(marque);
                        modeleTextView.setText(modele);
                        versionTextView.setText(version);
                        placeTextView.setText(place);
                        boiteVitesseTextView.setText(boiteVitesse);
                        carburantTexView.setText(carburant);
                        prixHoraireTextView.setText(prixHoraire.toString());
                        prixJournalierTextView.setText(prixJournalier.toString());
                        villeTextView.setText(ville);
                        statutTextView.setText(statut);
                        dateTimestampTextView.setText(Utility.timestampToString(dateTimestamp));
                        Toast.makeText(LocationVoitureDetailsActivity.this, "Data is okay3333", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LocationVoitureDetailsActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LocationVoitureDetailsActivity.this, "Erreur = "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
