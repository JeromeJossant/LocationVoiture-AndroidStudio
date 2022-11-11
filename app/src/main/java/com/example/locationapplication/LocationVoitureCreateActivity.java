package com.example.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.locationapplication.models.LocationVoiture;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class LocationVoitureCreateActivity extends AppCompatActivity {


    EditText marqueEditText, modeleEditText, versionEditText, placeEditText, carburantEditText, boiteVitesseEditText, prixHoraireEditText, prixJournalierEditText, villeEditText, statutEditText;
    ImageButton saveLocationBtn;
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
        prixHoraireEditText = findViewById(R.id.location_prixH_text);
        prixJournalierEditText = findViewById(R.id.location_prixJ_text);
        villeEditText = findViewById(R.id.location_ville_text);
        statutEditText = findViewById(R.id.location_statut_text);
        saveLocationBtn = findViewById(R.id.save_location_btn);


        saveLocationBtn.setOnClickListener( (v) -> saveLocation());
    }

    void saveLocation() {
        String locationMarque = marqueEditText.getText().toString();
        String locationModele = modeleEditText.getText().toString();
        String locationVersion = versionEditText.getText().toString();
        String locationPlace = placeEditText.getText().toString();
        String locationCarburant = carburantEditText.getText().toString();
        String locationBoiteVitesse = boiteVitesseEditText.getText().toString();
        Float locationPrixHoraire = Float.valueOf(prixHoraireEditText.getText().toString());
        Float locationPrixJournalier = Float.valueOf(prixJournalierEditText.getText().toString());
        String locationVille = villeEditText.getText().toString();
        String locationStatut = statutEditText.getText().toString();

        if (locationMarque == null || locationMarque.isEmpty()) {
            marqueEditText.setError("La marque a besoin d'être rentré");
            return ;
        }
        if (locationModele == null || locationModele.isEmpty()) {
            modeleEditText.setError("Le modèle a besoin d'être rentré");
            return ;
        }
        if (locationVersion == null || locationVersion.isEmpty()) {
            versionEditText.setError("La version a besoin d'être rentré");
            return;
        }
        if (locationPlace == null || locationPlace.isEmpty()) {
            placeEditText.setError("Le nombre de place a besoin d'être rentré");
            return;
        }
        if (locationCarburant == null || locationCarburant.isEmpty()) {
            carburantEditText.setError("Le carburant a besoin d'être rentré");
            return;
        }
        if (locationBoiteVitesse == null || locationBoiteVitesse.isEmpty()) {
            boiteVitesseEditText.setError("La boite de vitesse a besoin d'être rentré");
            return;
        }
        if (locationPrixHoraire == null || locationPrixHoraire.isInfinite()) {
            prixHoraireEditText.setError("Le prix horaire a besoin d'être rentré");
            return;
        }
        if (locationPrixJournalier == null || locationPrixJournalier.isInfinite()) {
            prixJournalierEditText.setError("Le prix journalier a besoin d'être rentré");
            return;
        }
        if (locationVille == null || locationVille.isEmpty()) {
            villeEditText.setError("La ville a besoin d'être rentré");
            return;
        }
        if (locationStatut == null || locationStatut.isEmpty()) {
            statutEditText.setError("Le statut a besoin d'être rentré");
            return;
        }

        LocationVoiture locationVoiture = new LocationVoiture();
        locationVoiture.setMarque(locationMarque);
        locationVoiture.setModele(locationModele);
        locationVoiture.setVersion(locationVersion);
        locationVoiture.setPlace(locationPlace);
        locationVoiture.setCarburant(locationCarburant);
        locationVoiture.setBoiteVitesse(locationBoiteVitesse);
        locationVoiture.setPrixHoraire(locationPrixHoraire);
        locationVoiture.setPrixJournalier(locationPrixJournalier);
        locationVoiture.setVille(locationVille);
        locationVoiture.setStatut(locationStatut);
        locationVoiture.setTimestamp(Timestamp.now());
        saveLocationVoitureToFirebase(locationVoiture);
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
