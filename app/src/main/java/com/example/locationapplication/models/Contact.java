package com.example.locationapplication.models;


public class Contact {
    public String nom;
    public String prenom;
    public String message;
    public String email;
    public String userId;

    public Contact() {
    }

    public Contact(String nom, String prenom, String message, String email, String usid) {
        this.nom = nom;
        this.prenom = prenom;
        this.message = message;
        this.email = email;
        this.userId = usid;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", message='" + message + '\'' +
                ", email='" + email + '\'' +
                ", usid='" + userId + '\'' +
                '}';
    }
}

