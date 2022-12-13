package com.example.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.locationapplication.models.Contact;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

public class ContactActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    EditText editNom, editPrenom, editEmail, editMessage;
    Button button;
    ImageButton menuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        button = (Button) findViewById(R.id.button_envoyer);
        editNom = findViewById(R.id.edit_text_nom);
        editPrenom = findViewById(R.id.edit_text_prenom);
        editEmail = findViewById(R.id.edit_text_email);
        editMessage = findViewById(R.id.edit_text_message);
        menuBtn = findViewById(R.id.menu_btn);

        bottomNavigationView.setSelectedItemId(R.id.contact);

        menuBtn.setOnClickListener((v) ->showMenu());

        button.setOnClickListener((v) -> envoimessage());

    /*    button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), confirmation.class );
                startActivity(intent);
                finish();
            }
        });*/
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), ListLocationActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.contact:
                        return true;
                }
                return false;
            }
        });
    }

    private void envoimessage() {
        String userNom = editNom.getText().toString();
        String userPrenom = editPrenom.getText().toString();
        String userEmail = editEmail.getText().toString();
        String userMessage = editMessage.getText().toString();


        boolean isValidateData = validateData(userNom, userPrenom, userEmail, userMessage);
        if (!isValidateData){
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Contact contact = new Contact();
        contact.setNom(userNom);
        contact.setPrenom(userPrenom);
        contact.setEmail(userEmail);
        contact.setMessage(userMessage);
        contact.setUserId(currentUser.getUid());
        saveContactToFirebase(contact);


    }



    boolean validateData(String userNom, String userPrenom, String userEmail, String userMessage){
        if (userNom == null || userNom.isEmpty()) {
            editNom.setError("Le nom a besoin d'être rentré");
            return false;
        }
        if (userPrenom == null || userPrenom.isEmpty()) {
            editPrenom.setError("Le prenom  a besoin d'être rentré");
            return false;
        }
        if (userEmail == null || userEmail.isEmpty()) {
            editEmail.setError(" Email a besoin d'être rentré");
            return false;
        }
        if (userMessage == null || userMessage.isEmpty()) {
            editMessage.setError("Le message a besoin d'être rentré");
            return false;
        }
        return true;
    }


    void saveContactToFirebase(Contact contact){

        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForContact().document();

        documentReference.set(contact).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ContactActivity.this, "Votre message a été envoyé avec success.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ContactActivity.this, "Erreur, votre message n'a pas été envoyé !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(ContactActivity.this, menuBtn);
        popupMenu.getMenu().add("Profil");
        popupMenu.getMenu().add("Mes annonces");
        popupMenu.getMenu().add("Se déconcecter");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle() == "Se déconcecter") {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(ContactActivity.this, LoginActivity.class));
                    finish();
                } if (menuItem.getTitle() == "Profil") {
                    startActivity(new Intent(ContactActivity.this, ProfilActivity.class));
                }
                if (menuItem.getTitle() == "Mes annonces") {
                    startActivity(new Intent(ContactActivity.this, myAdsActivity.class));
                }
                return false;
            }
        });
    }
}