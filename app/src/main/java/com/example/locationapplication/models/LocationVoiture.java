package com.example.locationapplication.models;

import com.google.firebase.Timestamp;

public class LocationVoiture {

    String marque;
    String modele;
    String version;
    String place;
    String carburant;
    String boiteVitesse;
    Float prixHoraire;
    Float prixJournalier;
    String ville;
    String statut;

    Timestamp timestamp;

    public LocationVoiture() {
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public String getBoiteVitesse() {
        return boiteVitesse;
    }

    public void setBoiteVitesse(String boiteVitesse) {
        this.boiteVitesse = boiteVitesse;
    }

    public Float getPrixHoraire() {
        return prixHoraire;
    }

    public void setPrixHoraire(Float prixHoraire) {
        this.prixHoraire = prixHoraire;
    }

    public Float getPrixJournalier() {
        return prixJournalier;
    }

    public void setPrixJournalier(Float prixJournalier) {
        this.prixJournalier = prixJournalier;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String floatToStringPrixH(Float prixHoraire) {
        return String.valueOf(prixHoraire);
    }

    public String floatToStringPrixJ(Float prixJournalier) {
        return String.valueOf(prixJournalier);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String toString(){
        return "Marque: " + marque + " Modele: " + modele + " Version: " + version + " Place: " + place + " Carburant: " + carburant + " BoiteVitesse: " + boiteVitesse + " PrixHoraire: " + prixHoraire + " PrixJournalier: " + prixJournalier + " Ville: " + ville + " Statut: " + statut;}
}

