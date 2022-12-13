package com.example.locationapplication.models;

import java.util.Date;

public class Reservation {
    private String id;
    private Date dateDebut;
    private Date dateFin;
    private Float cout;
    private String idLocationVoiture;
    private String idUser;

public Reservation() {
    }

    public Reservation(String id, Date dateDebut, Date dateFin, Float cout, String idLocationVoiture, String idUser) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.cout = cout;
        this.idLocationVoiture = idLocationVoiture;
        this.idUser = idUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Float getCout() {
        return cout;
    }

    public void setCout(Float cout) {
        this.cout = cout;
    }

    public String getIdLocationVoiture() {
        return idLocationVoiture;
    }

    public void setIdLocationVoiture(String idLocationVoiture) {
        this.idLocationVoiture = idLocationVoiture;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

}
