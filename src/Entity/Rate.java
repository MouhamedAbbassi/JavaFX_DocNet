
package Entity;

/**
 *
 * @author selmi
 */
public class Rate {


      private int id;
       private int med_id;
        private int make_rate_id;
    private Double note;
     private String opinion;

     public Rate(int med_id, int make_rate_id, Double note, String opinion) {
        this.med_id = med_id;
        this.make_rate_id = make_rate_id;
        this.note = note;
        this.opinion = opinion;
    }
 public Rate(int id, int med_id, int make_rate_id, Double note, String opinion) {
        this.id = id;
        this.med_id = med_id;
        this.make_rate_id = make_rate_id;
        this.note = note;
        this.opinion = opinion;
    }

    public Rate(Double note) {
        this.note = note;
    }

    public Rate(int id, Double note, String opinion) {
         this.id = id;
        this.note = note;
        this.opinion = opinion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getmed_id() {
        return med_id;
    }

    public void setmed_id(int med_id) {
        this.med_id = med_id;
    }
    
    public int getmake_rate_id() {
        return make_rate_id;
    }

    public void setmake_rate_id(int make_rate_id) {
        this.make_rate_id = make_rate_id;
    }
     
    public Double getnote() {
        return note;
    }
    

    public void setnote(Double note) {
        this.note = note;
    }
   
    
    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    @Override
    public String toString() {
        return "Rate{" + "id=" + id + ", med_id=" + med_id + ", make_rate_id=" + make_rate_id + ", note=" + note + ", opinion=" + opinion + '}';
    }


}

    

