/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.sql.Date;




public class Reservation {

    private int id;
    private int users_id;
    private int patient_id;
    private Date start;
    private Date end;
    private String comment;

    public Reservation(int id,int users_id, int patient_id, Date start, Date end, String comment) {
         this.id = id;  
        this.users_id = users_id;
        this.patient_id = patient_id;
        this.start = start;
         this.end = end;
        this.comment = comment;
    }
    public Reservation(int users_id, int patient_id, Date start, Date end, String comment) {
          this.users_id = users_id;
        this.patient_id = patient_id;
        this.start = start;
         this.end = end;
        this.comment = comment;
    }

    public Reservation(int id, int users_id, int patient_id, String comment) {
         this.id = id;  
        this.users_id = users_id;
        this.patient_id = patient_id;
       
        this.comment = comment;
    }

    public Reservation(int users_id, int patient_id, String comment) {
        
        this.users_id = users_id;
        this.patient_id = patient_id;
       
        this.comment = comment;
    }

    public Reservation(int id,Date start, Date end, String comment) {
         this.id = id;  
         this.start = start;
         this.end = end;
        this.comment = comment;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getusers_id() {
        return users_id;
    }

    public void setusers_id(int users_id) {
        this.users_id = users_id;
    }
    
    public int getpatient_id() {
        return patient_id;
    }

    public void setpatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
     
    public Date getstart() {
        return start;
    }
    public Date getend() {
        return end;
    }

    public void setstart(Date start) {
        this.start = start;
    }
    public void setend(Date end) {
        this.end = end;
    }
    
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

 @Override
    public String toString() {
        return "Reservation {" + "id=" + id + ", users_id=" + users_id + ", patient_id=" + patient_id + ", start=" + start + ", end=" + end + ", comment=" +comment +  '}';
    }
}
