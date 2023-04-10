/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.entity;

/**
 *
 * @author asus
 */
public class OrdonnanceMedicament {
    private int id;
    private int duration;
    private int dosage;
    private int idMedicament;

    public OrdonnanceMedicament() {
    }

    public OrdonnanceMedicament(int duration, int dosage) {
        this.duration = duration;
        this.dosage = dosage;
    }
    public OrdonnanceMedicament(int duration, int dosage , int idMedicament) {
        this.idMedicament = idMedicament;
        this.duration = duration;
        this.dosage = dosage;
    }

    public OrdonnanceMedicament(int id, int duration, int dosage, int idMedicament) {
        this.id = id;
        this.duration = duration;
        this.dosage = dosage;
        this.idMedicament = idMedicament;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }
    
    
    @Override
    public String toString() {
        return "OrdonnanceMedicament{" + "id=" + id + ", duration=" + duration + ", dosage=" + dosage + '}';
    }
    
    
    
}
