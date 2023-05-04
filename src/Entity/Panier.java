/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Firas
 */
public class Panier {
    
     private int id ;
 private int id_medica ;
 private int id_user ;
 private int prix ;
 private String nom;
  private int qte ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_medica() {
        return id_medica;
    }

    public void setId_medica(int id_medica) {
        this.id_medica = id_medica;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Panier() {
    }

    public Panier(int id_medica, int id_user, int prix, int qte) {
        this.id_medica = id_medica;
        this.id_user = id_user;
        this.prix = prix;
        this.qte = qte;
    }

    public Panier(int prix , String nom ,int qte) {
        this.prix = prix;
        this.nom = nom;
        this.qte = qte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
      
    
    
}
