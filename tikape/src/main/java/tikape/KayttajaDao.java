package tikape;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KayttajaDao implements Dao<Kayttaja, Integer> {

    private Database database;

    public KayttajaDao(Database database) {
        this.database = database;
    }
    
    public void lisaa(String nimi) throws SQLException{
        String sql = "INSERT INTO Kayttaja(nimi) VALUES ('" + nimi + "')";
        database.update(sql);
    }
    @Override
    public Kayttaja findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja WHERE id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Kayttaja k = new Kayttaja(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return k;

    }

    @Override
    public List<Kayttaja> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja");
        ResultSet rs = stmt.executeQuery();
        List<Kayttaja> kayttajat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            kayttajat.add(new Kayttaja(id, nimi));

        }

        rs.close();
        stmt.close();
        connection.close();

        return kayttajat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

