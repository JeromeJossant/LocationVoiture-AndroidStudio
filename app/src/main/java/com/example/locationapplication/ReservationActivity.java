package com.example.locationapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationActivity extends AppCompatActivity {

    ImageButton backBtn;
    TextView dateDebutTextView, dateFinTextView, coutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        dateDebutTextView = findViewById(R.id.date_debut_text_view);
        dateFinTextView = findViewById(R.id.date_fin_text_view);
        coutTextView = findViewById(R.id.cout_text_view);
        
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener((v) -> {
            finish();
        });
    }
}