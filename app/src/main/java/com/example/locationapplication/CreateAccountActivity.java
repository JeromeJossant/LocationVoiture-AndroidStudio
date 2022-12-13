package com.example.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationapplication.models.LocationVoiture;
import com.example.locationapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class CreateAccountActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, confirmPasswordEditText, nomEditText, prenomEditText;
    Button createAccountBtn;
    ProgressBar progressBar;
    Vibrator vibrator;
    TextView loginBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        nomEditText = findViewById(R.id.nom_edit_text);
        prenomEditText = findViewById(R.id.prenom_edit_text);
        createAccountBtn = findViewById(R.id.create_account_btn);
        progressBar = findViewById(R.id.progress_bar);
        loginBtnTextView = findViewById(R.id.login_text_view_btn);

        createAccountBtn.setOnClickListener(v -> createAccount());
        loginBtnTextView.setOnClickListener(v -> finish());

    }

    void createAccount() {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean isValidateData = validateData(email, password, confirmPassword);
        if (!isValidateData) {
            return;
        }

        createAccountInFirebase(email, password, nomEditText.getText().toString(), prenomEditText.getText().toString());
    }

    void createAccountInFirebase(String email, String password, String nom, String prenom) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    User user = new User(email, nom, prenom , firebaseUser.getUid());
                    FirebaseFirestore.getInstance().collection("users").document(firebaseUser.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            changeInProgress(false);
                            Toast.makeText(CreateAccountActivity.this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    changeInProgress(false);
                    Toast.makeText(CreateAccountActivity.this, "Erreur lors de la création du compte", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    void changeInProgress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password, String confirmPassword){


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Le mail n'est pas valide");
            vibrator.vibrate(75);
            return false;
        }

        if (password.length()<6){
            passwordEditText.setError("Le mot de passe est trop court");
            vibrator.vibrate(75);
            return false;
        }

        if (!password.equals(confirmPassword)){
            confirmPasswordEditText.setError("Vous avez entré deux mots de passe différents");
            vibrator.vibrate(75);
            return false;
        }
        return true;
    }

}
