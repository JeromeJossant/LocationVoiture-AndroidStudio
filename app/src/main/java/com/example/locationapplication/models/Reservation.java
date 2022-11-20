package com.example.locationapplication.models;

import java.util.Date;

public class Reservation {
    private String id;
    private Date dateDebut;
    private Date dateFin;
    private Float cout;
    private Integer idLocationVoiture;
    private Integer idUser;

public Reservation() {
    }

    public Reservation(String id, Date dateDebut, Date dateFin, Float cout, Integer idLocationVoiture, Integer idUser) {
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

    public Integer getIdLocationVoiture() {
        return idLocationVoiture;
    }

    public void setIdLocationVoiture(Integer idLocationVoiture) {
        this.idLocationVoiture = idLocationVoiture;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

}
