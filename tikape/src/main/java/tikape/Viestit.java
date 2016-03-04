package tikape;

import java.sql.Timestamp;
import java.util.Date;

public class Viestit {

    private int id;
    private String otsikko;
    private String sisalto;
    private Timestamp pvm;
    private Kayttaja kayttaja;
    private KeskustelualueenViestiketjut viestiketju;

    public Viestit(int id, String otsikko, String sisalto, Timestamp pvm, Kayttaja kayttaja, KeskustelualueenViestiketjut viestiketju) {
        this.id = id;
        this.otsikko = otsikko;
        this.sisalto = sisalto;
        this.pvm = pvm;
        this.kayttaja = kayttaja;
        this.viestiketju = viestiketju;
    }

    public int getId() {
        return this.id;
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setPvm(Timestamp pvm) {
        this.pvm = pvm;
    }

    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
    }

    public void setViestiketju(KeskustelualueenViestiketjut viestiketju) {
        this.viestiketju = viestiketju;
    }

    public Timestamp getPvm() {
        return pvm;
    }

    public Kayttaja getKayttaja() {
        return kayttaja;
    }

    public KeskustelualueenViestiketjut getViestiketju() {
        return viestiketju;
    }

    public String getSisalto() {
        return this.sisalto;
    }
    
     public Date getDate() {
        return new Date(this.pvm.getTime() * 1000);
    }

    @Override
    public String toString() {
        return "(" + id + ") " + this.getViestiketju() + " " + sisalto + " " + this.getDate();
    }
}


