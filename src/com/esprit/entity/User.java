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
public class User {
    private int id;
    private String nom;
    private String prenom;
    private String password;
    private String email;
    private String image;

    public User() {
    }

    public User(String username, String lastname, String image) {
        this.nom = username;
        this.prenom = lastname;
        this.image = image;
    }
    
    
    
    public User(int id, String username, String password) {
        this.id = id;
        this.nom = username;
        this.password = password;
    }

    public User(int id, String username, String lastname, String email) {
        this.id = id;
        this.nom = username;
        this.prenom = lastname;
        this.email = email;
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

    public void setNom(String username) {
        this.nom = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String lastname) {
        this.prenom = lastname;
    }
    
}
