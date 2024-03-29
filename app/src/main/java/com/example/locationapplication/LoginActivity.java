package com.example.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginBtn;
    ProgressBar progressBar;
    Vibrator vibrator;
    TextView createAccountBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginBtn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar);
        createAccountBtnTextView = findViewById(R.id.create_account_text_view_btn);

        loginBtn.setOnClickListener((v) -> loginUser());
        createAccountBtnTextView.setOnClickListener((v) -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));

    }

    void loginUser(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        boolean isValidateData = validateData(email, password);
        if (!isValidateData){
            return;
        }

        loginAccountInFirebase(email, password);
    }

    void loginAccountInFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()) {
                    //connexion autorisée

                    startActivity(new Intent(LoginActivity.this, ListLocationActivity.class));
                    finish();
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                    } else {
                        Toast.makeText(LoginActivity.this, "Votre mail n'a pas été vérifié. Merci de regarder dans vos mails.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()){
            emailEditText.setError("Le mail n'est pas valide");
            return false;
        }

        if (password.length()<6){
            passwordEditText.setError("Le mot de passe est trop court");
            return false;
        }
        return true;
    }
}