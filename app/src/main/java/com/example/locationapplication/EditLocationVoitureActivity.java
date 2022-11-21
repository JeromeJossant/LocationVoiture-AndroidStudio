package com.example.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditLocationVoitureActivity extends AppCompatActivity {



    TextView pageTitreTextView, marqueTextView, modeleTextView,versionTextView, placeTextView, boiteVitesseTextView , carburantTexView, prixJournalierTextView, villeTextView, statutTextView, dateTimestampTextView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String marque, modele, version, place, boiteVitesse, carburant, ville, statut, docId;
    Float prixHoraire, prixJournalier;
    Timestamp dateTimestamp;
    ImageButton backBtn, editLocationBtn;
    LinearLayout reservationBtn;
    boolean isEditable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

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

        backBtn.setOnClickListener( (v) -> finish());

     /*   marque = getIntent().getStringExtra("marque");
        modele = getIntent().getStringExtra("modele");
        place = getIntent().getStringExtra("place");
        carburant = getIntent().getStringExtra("carburant");
        boiteVitesse = getIntent().getStringExtra("boiteVitesse");
        ville = getIntent().getStringExtra("ville");
        statut = getIntent().getStringExtra("statut");
        prixHoraire = getIntent().getFloatExtra("prixHoraire", 0);
        docId = getIntent().getStringExtra("docId");

        if (docId != null && !docId.isEmpty()) {
            isEditable = true;
            pageTitreTextView.setText("Modification");
        }*/

    }
}