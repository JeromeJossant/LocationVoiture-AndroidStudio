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
        return FirebaseFirestore.getInstance().collection("contact");
    }

    static CollectionReference getCollectionReferenceForReservation() {
        return FirebaseFirestore.getInstance().collection("reservation");
    }

    static String timestampToString(Timestamp timestamp) {
       return new SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate());
    }


}
