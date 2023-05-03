/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Firas
 */
public class Medicament {
    
 private int id ;
   private String nom;
   private String type;
   private int nb_dose ;
   private int prix;
   private int stock ;
   private String image;

    public int getId() {
        return id;
    }
  

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public int getNb_dose() {
        return nb_dose;
    }

    public int getPrix() {
        return prix;
    }

    public int getStock() {
        return stock;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNb_dose(int nb_dose) {
        this.nb_dose = nb_dose;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Medicament(String nom, String type, int nb_dose, int prix, int stock, String image) {
       // this.id = id;
        this.nom = nom;
        this.type = type;
        this.nb_dose = nb_dose;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
    }

    public Medicament() {
    }

   public Medicament(int id,String nom, String type, int nb_dose, int prix, int stock,String image) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.nb_dose = nb_dose;
        this.prix = prix;
        this.stock = stock;
        this.image = image; 
    }

    @Override
    public String toString() {
        return "Medicament{" + " nom=" + nom + ", type=" + type + ", nb_dose=" + nb_dose + ", prix=" + prix + ", stock=" + stock + ", image=" + image + '}';
    }

   

 
    
    
      
}
