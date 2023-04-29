package Entity;

 
public class User
{
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Role role; 
    private String roles;  
    private String adresse;

  
    private String image; 
    private String numero;  
    private String reset_token;  
    private String baned;  
    private int status;  
    private double rates;

    public User(int id, String nom, String prenom, String email, String image, String numero, String roles, String baned, int status) {
         this.id = id;
        this.prenom = prenom;
        this.nom = nom;
         this.email = email;
       this.image = image;
       this.numero = numero;
       this.roles = roles;
     this.baned = baned;
      this.status = status;
    }

    public double getRates() {
        return rates;
    }

    public void setRates(double rates) {
        this.rates = rates;
    }









    public User() {}
 

    public User(String nom, String prenom, String email, String password, Role role)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String nom, String prenom, String email, String numero) 
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numero = numero;
    }
 
   
 
      public User(String image)
    {
        this.image = image;
       
    }   
      public User(String reset_token,boolean dummy)
    {
        this.reset_token = reset_token;
  
    }

    public User(int id) {
        this.id = id;
    }
 

    public User(String nom, String prenom, String email,  String image, String numero,String roles,String baned,int status) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.image = image;
        this.numero = numero; 
        this.roles = roles;
        this.baned =baned;    
        this.status = status;


    }
        public User(String nom, String prenom , String numero) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
    }

    public User(int id, String nom, String prenom, String email, double rates) {
               this.id = id;   
               this.nom = nom;     
               this.prenom = prenom;    
               this.email = email;    
               this.rates = rates;




    }


      
      
    public Integer getId(){return id;}

    public void setId(Integer id){this.id = id;}

    public String getNom(){return nom;}

    public void setNom(String nom){this.nom = nom;}

    public String getPrenom(){return prenom;}

    public void setPrenom(String prenom){this.prenom = prenom;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password){this.password = password;}

    public Role getRole() {return role;}

    public void setRole(Role role) {this.role = role;}

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}

    public String getNumero() {return numero;}

    public void setNumero(String numero) {this.numero = numero;}

    public String getReset_token() {return reset_token;}

    public void setReset_token(String reset_token) {this.reset_token = reset_token;}

    public String getRoles() {return roles;}

    public void setRoles(String roles) {this.roles = roles;}

    public String getBaned() { return baned;}

    public void setBaned(String baned) {this.baned = baned;}

    public int getStatus() { return status;}

    public void setStatus(int status) {this.status = status;}

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return  "Dr." + nom + " " + prenom ;
    }
    
    
 
}
