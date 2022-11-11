package com.example.locationapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {

    static CollectionReference getCollectionReferenceForLocationVoiture() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("locationVoiture")
                .document(currentUser.getUid()).collection("my_locationVoiture");
    }


}
