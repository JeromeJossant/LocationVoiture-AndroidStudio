package com.example.locationapplication;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locationapplication.models.Contact;
import com.example.locationapplication.models.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    ImageButton backBtn;
    TextView dateDebutTextView, dateFinTextView, coutTextView, marqueModelTextView, prixJournalierTextView;
    float prixJournalier;
    String docId, marque, modele;
    FirebaseAuth firebaseAuth;
    Button reserverBtn, voirPrixBtn;

    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        dateDebutTextView = findViewById(R.id.date_debut_text_view);
        dateFinTextView = findViewById(R.id.date_fin_text_view);
        marqueModelTextView = findViewById(R.id.marque_model_version_text_view);
        prixJournalierTextView = findViewById(R.id.prix_journalier_text_view);
        coutTextView = findViewById(R.id.cout_text_view);
        reserverBtn = findViewById(R.id.valider_btn);
        voirPrixBtn = findViewById(R.id.voir_prix);
        
        backBtn = findViewById(R.id.back_btn);


        voirPrixBtn.setOnClickListener((v) -> {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  //Format de date souhaité
        try {
            Date dateAvant = format.parse(dateDebutTextView.getText().toString());
            Date dateFin = format.parse(dateFinTextView.getText().toString());
            long differenceDate = dateFin.getTime()-dateAvant.getTime();
            float nbJours = (differenceDate/(1000 * 60 * 60 * 24));
            String cout = String.valueOf(nbJours * prixJournalier);
            Toast.makeText(ReservationActivity.this, cout, Toast.LENGTH_SHORT).show();
            coutTextView.setText(cout);
        }catch(Exception e){
            e.printStackTrace();
        }
        });

        reserverBtn.setOnClickListener((v) -> createReservation());

        backBtn.setOnClickListener((v) -> finish());

        dateDebutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View view) {

                setDate_Debut();
            }
        });

        dateFinTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate_Fin();
            }
        });

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
                        prixJournalier = documentSnapshot.getLong("prixJournalier").floatValue();

                        marqueModelTextView.setText(marque + " " + modele);
                        prixJournalierTextView.setText(String.valueOf(prixJournalier));



                    } else {
                        Toast.makeText(ReservationActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReservationActivity.this, "Erreur = " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

        //La date de fin doit etre supérieure à la det de début.

        int NbJours;

        //Récuperer le prixJournalier de la bdd

        /*Multiplier le nombre de jour * le prix journalier
          Puis l'afficher ds le TextView Prix'*/

        //Au click, enregistrer ces 2 dates, cette multiplication ds la bdd t aller sur la page profil.


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDate_Debut() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                String showDate = date + "/" + (month + 1) + "/" + year;
                dateDebutTextView.setText(showDate);
            }
        }, year, month, date);

        datePickerDialog.show();
}

        private void setDate_Fin(){
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(calendar.MONTH);
            int date = calendar.get(Calendar.DATE);

            DatePickerDialog datePickerDialog= new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int month, int date){
                    String showDate=date +"/"+(month+1)+"/"+year;
                    dateFinTextView.setText(showDate);
                }
            },year,month,date);

            datePickerDialog.show();

        }

    private void createReservation() {

        Float cout = Float.valueOf(coutTextView.getText().toString());

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Reservation reservation = new Reservation();
        reservation.setCout(cout);
        reservation.setIdLocationVoiture(docId);
        reservation.setIdUser(currentUser.getUid());

        saveReservationToFirebase(reservation);

    }


    void saveReservationToFirebase(Reservation reservation){

        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForReservation().document();

        documentReference.set(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ReservationActivity.this, "Votre réservation a été éffecuté avec success.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ReservationActivity.this, "Erreur, votre réservation n'a pas été éffectué !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
