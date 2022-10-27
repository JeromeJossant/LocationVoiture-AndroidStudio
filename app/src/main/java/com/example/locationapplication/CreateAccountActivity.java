package com.example.locationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, confirmPasswordEditText;
    Button createAccountBtn;
    ProgressBar progressBar;
    TextView loginBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        createAccountBtn = findViewById(R.id.create_account_btn);
        progressBar = findViewById(R.id.progress_bar);
        loginBtnTextView = findViewById(R.id.login_text_view_btn);

        createAccountBtn.setOnClickListener(v-> createAccount());
        loginBtnTextView.setOnClickListener(v->finish());
    }
     void createAccount() {
         String email = emailEditText.getText().toString();
         String password = passwordEditText.getText().toString();
         String confirmPassword = confirmPasswordEditText.getText().toString();

         boolean isValidateData = validateData(email, password, confirmPassword);
     }

     boolean validateData(String email, String password, String confirmPassword){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Le mail n'est pas valide");
            return false;
        }

        if (password.length()<6){
            passwordEditText.setError("Le mot de passe est trop court");
            return false;
        }

        if (!password.equals(confirmPassword)){
            confirmPasswordEditText.setError("Vous avez entré deux mots de passe différents");
        }
        return true;
     }
}