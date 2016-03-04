package tikape;

public class Kayttaja {

    private int id;
    private String nimi;

    public Kayttaja(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public int getId() {
        return this.id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    @Override
    public String toString() {
        return "(" + id + ") " + nimi;
    }
}
