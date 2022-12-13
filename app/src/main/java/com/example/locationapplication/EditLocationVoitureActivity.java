package com.example.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationapplication.models.LocationVoiture;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditLocationVoitureActivity extends AppCompatActivity {


    TextView pageTitreTextView;
    EditText marqueEditText, modeleEditText, versionEditText, placeEditText, boiteVitesseEditText, carburantEditText, prixJournalierEditText, villeEditText, statutEditText;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String marque, modele, version, place, boiteVitesse, carburant, ville, statut, docId, uid;
    Float prixJournalier;
    Timestamp dateTimestamp;
    ImageButton backBtn, saveEditLocationBtn;
    LinearLayout reservationBtn;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        marqueEditText = findViewById(R.id.edit_location_marque);
        pageTitreTextView = findViewById(R.id.page_title);
        prixJournalierEditText = findViewById(R.id.edit_location_prixJournalier);
        modeleEditText = findViewById(R.id.edit_location_modele);
        versionEditText = findViewById(R.id.edit_location_version);
        placeEditText = findViewById(R.id.edit_location_place);
        carburantEditText = findViewById(R.id.edit_location_carburant);
        boiteVitesseEditText = findViewById(R.id.edit_location_boiteVitesse);
        villeEditText = findViewById(R.id.edit_location_ville);
        statutEditText = findViewById(R.id.edit_location_statut);
        reservationBtn = findViewById(R.id.reserver_btn);
        saveEditLocationBtn = findViewById(R.id.save_edit_location_btn);
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener((v) -> finish());

        saveEditLocationBtn.setOnClickListener((v) -> saveEditLocation());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


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

                        marqueEditText.setText(marque);
                        modeleEditText.setText(modele);
                        versionEditText.setText(version);
                        placeEditText.setText(place);
                        boiteVitesseEditText.setText(boiteVitesse);
                        carburantEditText.setText(carburant);
                        prixJournalierEditText.setText(prixJournalier.toString());
                        villeEditText.setText(ville);
                        statutEditText.setText(statut);

                    } else {
                        Toast.makeText(EditLocationVoitureActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditLocationVoitureActivity.this, "Erreur = " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void saveEditLocation() {
        String locationMarque = marqueEditText.getText().toString().trim();
        String locationModele = modeleEditText.getText().toString().trim();
        String locationVersion = versionEditText.getText().toString().trim();
        String locationPlace = placeEditText.getText().toString().trim();
        String locationCarburant = carburantEditText.getText().toString().trim();
        String locationBoiteVitesse = boiteVitesseEditText.getText().toString().trim();
        Float locationPrixJournalier = Float.valueOf(prixJournalierEditText.getText().toString().trim());
        String locationVille = villeEditText.getText().toString().trim();
        String locationStatut = statutEditText.getText().toString().trim();

        boolean isValidateData = validateData(locationMarque, locationModele, locationVersion, locationPlace, locationCarburant, locationBoiteVitesse, locationPrixJournalier, locationVille, locationStatut);
        if (!isValidateData){
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        LocationVoiture locationVoiture = new LocationVoiture();
        locationVoiture.setMarque(locationMarque);
        locationVoiture.setModele(locationModele);
        locationVoiture.setVersion(locationVersion);
        locationVoiture.setPlace(locationPlace);
        locationVoiture.setCarburant(locationCarburant);
        locationVoiture.setBoiteVitesse(locationBoiteVitesse);
        locationVoiture.setPrixJournalier(locationPrixJournalier);
        locationVoiture.setVille(locationVille);
        locationVoiture.setStatut(locationStatut);
        locationVoiture.setTimestamp(Timestamp.now());
        locationVoiture.setUserId(currentUser.getUid());
        saveLocationVoitureToFirebase(locationVoiture);
    }

    void saveLocationVoitureToFirebase (LocationVoiture locationVoiture) {

        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForLocationVoiture().document(docId);

        documentReference.set(locationVoiture).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditLocationVoitureActivity.this, "Votre voiture a été modifié.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditLocationVoitureActivity.this, "Erreur, votre voiture n'a pas été modifié !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    boolean validateData(String locationMarque, String locationModele, String locationVersion, String locationPlace, String locationCarburant, String locationBoiteVitesse, Float locationPrixJournalier, String locationVille, String locationStatut) {

        if (locationMarque == null || locationMarque.isEmpty()) {
            marqueEditText.setError("La marque a besoin d'être rentré");
            return false;
        }
        if (locationModele == null || locationModele.isEmpty()) {
            modeleEditText.setError("Le modèle a besoin d'être rentré");
            return false;
        }
        if (locationVersion == null || locationVersion.isEmpty()) {
            versionEditText.setError("La version a besoin d'être rentré");
            return false;
        }
        if (locationPlace == null || locationPlace.isEmpty()) {
            placeEditText.setError("Le nombre de place a besoin d'être rentré");
            return false;
        }
        if (locationCarburant == null || locationCarburant.isEmpty()) {
            carburantEditText.setError("Le carburant a besoin d'être rentré");
            return false;
        }
        if (locationBoiteVitesse == null || locationBoiteVitesse.isEmpty()) {
            boiteVitesseEditText.setError("La boite de vitesse a besoin d'être rentré");
            return false;
        }
        if (locationPrixJournalier == null || locationPrixJournalier == 0) {
            prixJournalierEditText.setError("Le prix journalier a besoin d'être rentré");
            return false;
        }
        if (locationVille == null || locationVille.isEmpty()) {
            villeEditText.setError("La ville a besoin d'être rentré");
            return false;
        }
        if (locationStatut == null || locationStatut.isEmpty()) {
            statutEditText.setError("Le statut a besoin d'être rentré");
            return false;
        }
        return true;
    }
}