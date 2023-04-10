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
public class Fiche {
    int id;
    private String date;
    private int tel;
    private String etatClinique;
    private String genre;
    private String typeAssurance;

    public Fiche() {
    }

    public Fiche(int id, String date, int tel, String etatClinique, String genre, String typeAssurance) {
        this.id = id;
        this.date = date;
        this.tel = tel;
        this.etatClinique = etatClinique;
        this.genre = genre;
        this.typeAssurance = typeAssurance;
    }

    public Fiche(int tel, String date, String genre, String etatClinique, String typeAssurance) {
        this.date = date;
        this.tel = tel;
        this.etatClinique = etatClinique;
        this.genre = genre;
        this.typeAssurance = typeAssurance;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getEtatClinique() {
        return etatClinique;
    }

    public void setEtatClinique(String etatClinique) {
        this.etatClinique = etatClinique;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTypeAssurance() {
        return typeAssurance;
    }

    public void setTypeAssurance(String typeAssurance) {
        this.typeAssurance = typeAssurance;
    }

    @Override
    public String toString() {
        return "Fiche{" + "id=" + id + ", date=" + date + ", tel=" + tel + ", etatClinique=" + etatClinique + ", genre=" + genre + ", typeAssurance=" + typeAssurance + '}';
    }
    
    
}
