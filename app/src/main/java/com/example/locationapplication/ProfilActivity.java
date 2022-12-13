package com.example.locationapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilActivity extends AppCompatActivity {

    TextView nomTextView, prenomTextView, emailTextView;
    Button backAnnonceBtn;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser currentUser;
    String nom, prenom, email;
    ImageButton backBtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        nomTextView = findViewById(R.id.nom_profil_text_view);
        prenomTextView = findViewById(R.id.prenom_profil_text_view);
        emailTextView = findViewById(R.id.email_profil_text_view);
        backAnnonceBtn = findViewById(R.id.back_annonce_btn);
        backBtn = findViewById(R.id.back_btn);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        backAnnonceBtn.setOnClickListener(v -> finish());

        backBtn.setOnClickListener((v) -> finish());

        if (firebaseAuth.getCurrentUser() != null) {
            currentUser = firebaseAuth.getCurrentUser();
        } else {
            Toast.makeText(this, "Vous n'avez pas accès", Toast.LENGTH_SHORT).show();
        }

        firebaseFirestore.collection("users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()){
                        nom = documentSnapshot.getString("nom");
                        prenom = documentSnapshot.getString("prenom");
                        email = documentSnapshot.getString("email");

                        nomTextView.setText(nom);
                        prenomTextView.setText(prenom);
                        emailTextView.setText(email);
                    } else {
                        Toast.makeText(ProfilActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfilActivity.this, "Erreur = "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}