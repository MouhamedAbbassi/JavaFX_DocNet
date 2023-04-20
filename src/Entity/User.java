package Entity;

 
public class User
{
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Role role;
    private String image; 
    private String numero;  
    private String reset_token;




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

    public User(String nom, String prenom, String email,  String image, String numero) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.image = image;
        this.numero = numero;
    }
        public User(String nom, String prenom , String numero) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
    }


      
      
    public int getId(){return id;}

    public void setId(int id){this.id = id;}

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }
    
    
 
}
