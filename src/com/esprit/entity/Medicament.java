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
public class Medicament {
    private int id;
    private String nom;
    private String type;
    private String nb_dose;
    private float prix;
    private int stock;

    public Medicament() {
    }

    public Medicament(int id, String nom, String type, String nb_dose, float prix, int stock) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.nb_dose = nb_dose;
        this.prix = prix;
        this.stock = stock;
    }

    public Medicament(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNb_dose() {
        return nb_dose;
    }

    public void setNb_dose(String nb_dose) {
        this.nb_dose = nb_dose;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Medicament{" + "id=" + id + ", nom=" + nom + ", type=" + type + ", nb_dose=" + nb_dose + ", prix=" + prix + ", stock=" + stock + '}';
    }
    
}
