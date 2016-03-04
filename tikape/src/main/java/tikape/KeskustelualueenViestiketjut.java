package tikape;

import java.sql.Timestamp;
import java.util.Date;

public class KeskustelualueenViestiketjut {

    private int id;
    private String nimi;
    private Timestamp pvm;
    private Keskustelualue keskustelualue;
    private Integer keskustelualueenId;

    public KeskustelualueenViestiketjut(int id, String nimi, Timestamp pvm, Keskustelualue keskustelualue) {
        this.id = id;
        this.nimi = nimi;
        this.pvm = pvm;
        this.keskustelualue = keskustelualue;
        this.keskustelualueenId = keskustelualue.getId();
    }

    public Integer getKeskustelualueenId() {
        return keskustelualueenId;
    }

    public void setKeskustelualueenId(Integer keskustelualueenId) {
        this.keskustelualueenId = keskustelualueenId;
    }

    public Timestamp getPvm() {
        return pvm;
    }

    public void setPvm(Timestamp pvm) {
        this.pvm = pvm;
    }

    public Keskustelualue getKeskustelualue() {
        return keskustelualue;
    }

    public void setKeskustelualue(Keskustelualue keskustelualue) {
        this.keskustelualue = keskustelualue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }


    public int getId() {
        return this.id;
    }

    public String getNimi() {
        return this.nimi;
    }
     public Date getDate() {
        return new Date(this.pvm.getTime() * 1000);
    }

    @Override
    public String toString() {
        return "(" + id + ") " + nimi + " " + this.getDate();
    }
}


