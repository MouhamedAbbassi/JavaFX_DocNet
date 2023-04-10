package com.example.PiDev;


import javafx.beans.property.*;

public class EventModel {
    private IntegerProperty id;
    private StringProperty nom;
    private IntegerProperty  capacite;
    private StringProperty local ;
    private StringProperty date ;
    private DoubleProperty prix ;

    private StringProperty description;

    private  StringProperty categorie;

    public EventModel() {
        this.id = new SimpleIntegerProperty( );
        this.nom = new SimpleStringProperty() ;
        this.capacite = new SimpleIntegerProperty();
        this.local = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.prix = new SimpleDoubleProperty();
        this.description = new SimpleStringProperty();
        this.categorie = new SimpleStringProperty();
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public int getCapacite() {
        return capacite.get();
    }

    public IntegerProperty capaciteProperty() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite.set(capacite);
    }

    public String getLocal() {
        return local.get();
    }

    public StringProperty localProperty() {
        return local;
    }

    public void setLocal(String local) {
        this.local.set(local);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public double getPrix() {
        return prix.get();
    }

    public DoubleProperty prixProperty() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix.set(prix);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getCategorie() {
        return categorie.get();
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", capacite=" + capacite +
                ", local='" + local + '\'' +
                ", date='" + date + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}