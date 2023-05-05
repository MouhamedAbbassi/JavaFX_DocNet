package gui;

public class Event {
    private int id;
    private String nom;
    private Integer  capacite;
    private String local ;
    private String date ;
    private Double prix ;

    private String description;

    private  String categorie;

    public Event(int id, String nom, Integer capacite, String local, String date, Double prix, String description, String categorie) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.local = local;
        this.date = date;
        this.prix = prix;
        this.description = description;
        this.categorie = categorie;
    }

    public Event(String nom, Integer capacite, String local, String date, Double prix, String description) {
        this.nom = nom;
        this.capacite = capacite;
        this.local = local;
        this.date = date;
        this.prix = prix;
        this.description = description;
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

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
