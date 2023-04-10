/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.sql.Date;
import java.time.LocalDate;


public class Reservation {

      private int id;
       private int users_id;
        private int patient_id;
    private Date start;
    private Date end;
     private String comment;

    public Reservation(int id, int users_id, int patient_id, LocalDate Localdate1, LocalDate Localdate2, String comment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Reservation(int users_id, int patient_id, LocalDate Localdate1, LocalDate Localdate2, String comment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getusers_id() {
        return id;
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


}
