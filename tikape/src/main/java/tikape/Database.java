package tikape;

import java.net.*;
import java.sql.*;
import java.util.*;

public class Database {

    private String databaseAddress;
    private boolean debug;

    public Database(String databaseAddress) throws Exception {
        this.databaseAddress = databaseAddress;

        init();
    }

    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE Tuote;");
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Tuote (id SERIAL PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Tuote (nimi) VALUES ('postgresql-tuote');");

        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Tuote (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Tuote (nimi) VALUES ('sqlite-tuote');");

        return lista;
    }

    public void setDebugMode(boolean d) {
        debug = d;
    }

    public int update(String updateQuery) throws SQLException {
        Statement stmt = this.getConnection().createStatement();
        int changes = stmt.executeUpdate(updateQuery);

        if (debug) {
            System.out.println("---");
            System.out.println(updateQuery);
            System.out.println("Changed rows: " + changes);
            System.out.println("---");
        }
        stmt.close();

        return changes;
    }
}
