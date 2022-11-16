package com.example.locationapplication.models;

public class User {
    public String email;
    public String nom;
    public String prenom;
    public String uid;


    public User() {
    }



    public User(String email, String nom, String prenom) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User(String email, String password) {
    }

    public User(String email, String nom, String prenom, String uid) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }




    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

}