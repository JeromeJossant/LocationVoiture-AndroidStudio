package com.example.locationapplication;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;

public class Utility {

    static CollectionReference getCollectionReferenceForLocationVoiture() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("locationVoiture");
    }



    static CollectionReference getCollectionReferenceForContact() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("contact");
    }

    static DocumentReference getCollectionReferenceForUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid());
    }


    static String timestampToString(Timestamp timestamp) {
       return new SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate());
    }

    static Query getCollectionReferenceLocationVoitureForUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("locationVoiture").whereEqualTo("userId", currentUser);
    }

}
