package com.example.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.locationapplication.models.LocationVoiture;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

public class LocationVoitureCreateActivity extends AppCompatActivity {


    EditText marqueEditText, modeleEditText, versionEditText, placeEditText, carburantEditText, boiteVitesseEditText, prixJournalierEditText, villeEditText, statutEditText;
    ImageButton saveLocationBtn, backBtn;
    Vibrator vibrator;
    String marque, modele, place, carburant, boiteVitesse, ville, statut, docId;
    Float prixJournalier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_create);

        marqueEditText = findViewById(R.id.location_marque_text);
        modeleEditText = findViewById(R.id.location_modele_text);
        versionEditText = findViewById(R.id.location_version_text);
        placeEditText = findViewById(R.id.location_place_text);
        carburantEditText = findViewById(R.id.location_carburant_text);
        boiteVitesseEditText = findViewById(R.id.location_boiteV_text);
        prixJournalierEditText = findViewById(R.id.location_prixJ_text);
        villeEditText = findViewById(R.id.location_ville_text);
        statutEditText = findViewById(R.id.location_statut_text);
        saveLocationBtn = findViewById(R.id.save_location_btn);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        backBtn = findViewById(R.id.back_btn);


        //recevoir les données
        marque = getIntent().getStringExtra("marque");
        modele = getIntent().getStringExtra("modele");
        place = getIntent().getStringExtra("place");
        carburant = getIntent().getStringExtra("carburant");
        boiteVitesse = getIntent().getStringExtra("boiteVitesse");
        ville = getIntent().getStringExtra("ville");
        statut = getIntent().getStringExtra("statut");
        prixJournalier = getIntent().getFloatExtra("prixJournalier", 0);
        docId = getIntent().getStringExtra("docId");

        marqueEditText.setText(marque);
        modeleEditText.setText(modele);
        placeEditText.setText(place);
        carburantEditText.setText(carburant);
        boiteVitesseEditText.setText(boiteVitesse);
        villeEditText.setText(ville);
        statutEditText.setText(statut);
        prixJournalierEditText.setText(String.valueOf(prixJournalier));


        saveLocationBtn.setOnClickListener( (v) -> saveLocation());
        backBtn.setOnClickListener( (v) -> finish());
    }

    void saveLocation() {
        String locationMarque = marqueEditText.getText().toString();
        String locationModele = modeleEditText.getText().toString();
        String locationVersion = versionEditText.getText().toString();
        String locationPlace = placeEditText.getText().toString();
        String locationCarburant = carburantEditText.getText().toString();
        String locationBoiteVitesse = boiteVitesseEditText.getText().toString();
        Float locationPrixJournalier = Float.valueOf(prixJournalierEditText.getText().toString());
        String locationVille = villeEditText.getText().toString();
        String locationStatut = statutEditText.getText().toString();

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

    boolean validateData(String locationMarque, String locationModele, String locationVersion, String locationPlace, String locationCarburant, String locationBoiteVitesse, Float locationPrixJournalier, String locationVille, String locationStatut) {

        if (locationMarque == null || locationMarque.isEmpty()) {
            marqueEditText.setError("La marque a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        if (locationModele == null || locationModele.isEmpty()) {
            modeleEditText.setError("Le modèle a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        if (locationVersion == null || locationVersion.isEmpty()) {
            versionEditText.setError("La version a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        if (locationPlace == null || locationPlace.isEmpty()) {
            placeEditText.setError("Le nombre de place a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        if (locationCarburant == null || locationCarburant.isEmpty()) {
            carburantEditText.setError("Le carburant a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        if (locationBoiteVitesse == null || locationBoiteVitesse.isEmpty()) {
            boiteVitesseEditText.setError("La boite de vitesse a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        if (locationPrixJournalier == null || locationPrixJournalier == 0) {
            prixJournalierEditText.setError("Le prix journalier a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        if (locationVille == null || locationVille.isEmpty()) {
            villeEditText.setError("La ville a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        if (locationStatut == null || locationStatut.isEmpty()) {
            statutEditText.setError("Le statut a besoin d'être rentré");
            vibrator.vibrate(75);
            return false;
        }
        return true;
    }

    void saveLocationVoitureToFirebase(LocationVoiture locationVoiture){

        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForLocationVoiture().document();

        documentReference.set(locationVoiture).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()){
                     Toast.makeText(LocationVoitureCreateActivity.this, "Votre voiture a été ajouté.", Toast.LENGTH_SHORT).show();
                     finish();
                 } else {
                     Toast.makeText(LocationVoitureCreateActivity.this, "Erreur, votre voiture n'a pas été ajouté !", Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }
}
