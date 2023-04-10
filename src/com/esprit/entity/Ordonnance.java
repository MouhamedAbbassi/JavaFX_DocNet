/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author asus
 */
public class Ordonnance {
    private int id;
    private String nomMedecin;
    private String nomPatient;
    private String date;
    private String commentaire;
    private List<OrdonnanceMedicament> medicaments;
    private List<User> doctors;
    private int idOrdonnance;

    public Ordonnance() {
        medicaments = new ArrayList<>();
        doctors = new ArrayList<>();
    }

    public Ordonnance(String nomMedecin, String nomPatient, String commentaire) {
        this.nomMedecin = nomMedecin;
        this.nomPatient = nomPatient;
        this.commentaire = commentaire;
    }

    public Ordonnance(String nomMedecin, String nomPatient, String date, String commentaire) {
        this.nomMedecin = nomMedecin;
        this.nomPatient = nomPatient;
        this.date = date;
        this.commentaire = commentaire;
    }

    public Ordonnance(int id, String nomMedecin, String nomPatient, String date, String commentaire) {
        this.id = id;
        this.nomMedecin = nomMedecin;
        this.nomPatient = nomPatient;
        this.date = date;
        this.commentaire = commentaire;
    }

    public Ordonnance(int id, String nomMedecin, String nomPatient, String commentaire) {
        this.id = id;
        this.nomMedecin = nomMedecin;
        this.nomPatient = nomPatient;
        this.commentaire = commentaire;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomMedecin() {
        return nomMedecin;
    }

    public void setNomMedecin(String nomMedecin) {
        this.nomMedecin = nomMedecin;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Ordonnance{" + "id=" + id + ", nomMedecin=" + nomMedecin + ", nomPatient=" + nomPatient + ", date=" + date + ", commentaire=" + commentaire + '}';
    }
    
    public List<OrdonnanceMedicament> getMedicaments() {
        return medicaments;
    }

    public void addMedicament(OrdonnanceMedicament medicament) {
        this.medicaments.add(medicament);
    }

    public void setMedicaments(List<OrdonnanceMedicament> medicaments) {
        this.medicaments = medicaments;
    }

    public int getIdOrdonnance() {
        return idOrdonnance;
    }

    public void setIdOrdonnance(int idOrdonnance) {
        this.idOrdonnance = idOrdonnance;
    }
    
     public List<User> getDoctors() {
        return doctors;
    }

    public void addDoctors(User doctor) {
        this.doctors.add(doctor);
    }

    public void setDoctors(List<User> doctors) {
        this.doctors = doctors;
    }
    
}
