package tikape;

import java.sql.Timestamp;
import java.util.Date;

public class Keskustelualue {

    private int id;
    private String nimi;
    private String kuvaus;
    private Timestamp pvm;

    public Keskustelualue(int id, String nimi, String kuvaus, Timestamp pvm) {
        this.id = id;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.pvm = pvm;
    }

    public Timestamp getPvm() {
        return pvm;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public void setPvm(Timestamp pvm) {
        this.pvm = pvm;
    }

    public int getId() {
        return this.id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public String getKuvaus() {
        return this.kuvaus;
    }

    public Date getDate() {
        return new Date(this.pvm.getTime() * 1000);
    }

    @Override
    public String toString() {
        return "(" + id + ") " + nimi + " " + kuvaus + " " + this.getDate();
    }
}

