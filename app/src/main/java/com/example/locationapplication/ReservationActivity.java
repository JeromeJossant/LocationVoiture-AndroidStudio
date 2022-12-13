package com.example.locationapplication;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    ImageButton backBtn;
    TextView dateDebutTextView, dateFinTextView, coutTextView;
    float prixRecupere = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        dateDebutTextView = findViewById(R.id.date_debut_text_view);
        dateFinTextView = findViewById(R.id.date_fin_text_view);
        coutTextView = findViewById(R.id.cout_text_view);
        
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener((v) -> {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  //Format de date souhaité
            try {
                Date dateAvant = format.parse(dateDebutTextView.getText().toString());
                Date dateFin = format.parse(dateFinTextView.getText().toString());
                long differenceDate = dateFin.getTime()-dateAvant.getTime();
                float nbJours = (differenceDate/(1000 * 60 * 60 * 24));
                String cout = String.valueOf(nbJours * prixRecupere);
                coutTextView.setText(cout);
            }catch(Exception e){
                e.printStackTrace();
            }

        });

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

        //La date de fin doit etre supérieure à la det de début.

        int NbJours;

        //Récuperer le prixJournalier de la bdd

        /*Multiplier le nombre de jour * le prix journalier
          Puis l'afficher ds le TextView Prix'*/

        //Au click, enregistrer ces 2 dates, cette multiplication ds la bdd t aller sur la page profil.
    }

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

    }
