/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author selmi
 */
public class Consultation {
     private int id;
        private String date;
         private String comment;
     private String medecin;
     
     public Consultation(String date, String comment, String medecin) {
        this.date = date;
        this.comment = comment;
        this.medecin = medecin;
       
    }
 public Consultation(int id,String date, String comment, String medecin) {
        this.id = id;
       this.date = date;
        this.comment = comment;
        this.medecin = medecin;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }
    
    public String getcomment() {
        return comment;
    }

    public void setcomment(String comment) {
        this.comment = comment;
    }
     
    public String getmedecin() {
        return medecin;
    }
    

    public void setmedecin(String medecin) {
        this.medecin = medecin;
    }
   
    
 

    @Override
    public String toString() {
        return "Cons{" + "id=" + id + ", date=" + date + ", comment=" + comment + ", medecin=" + medecin +  '}';
    }
     
    
    
}
