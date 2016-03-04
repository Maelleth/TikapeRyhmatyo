package tikape;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeskustelualueDao implements Dao<Keskustelualue, Integer> {

    private Database database;

    public KeskustelualueDao(Database database) {
        this.database = database;
    }

    public void lisaa(String nimi, String kuvaus) throws SQLException {
        String sql = "INSERT INTO Keskustelualue (nimi, kuvaus, pvm) VALUES ('" + nimi + "',"
                + "'" + kuvaus + "', strftime('%s','now'))";

        this.database.update(sql);
    }

    @Override
    public Keskustelualue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        String kuvaus = rs.getString("kuvaus");
        System.out.println(rs.getString("pvm"));

        Timestamp pvm = rs.getTimestamp("pvm");

        Keskustelualue k = new Keskustelualue(id, nimi, kuvaus, pvm);
        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Keskustelualue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue");
        ResultSet rs = stmt.executeQuery();
        List<Keskustelualue> keskustelualueet = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            String kuvaus = rs.getString("kuvaus");
            Timestamp pvm = rs.getTimestamp("pvm");

            keskustelualueet.add(new Keskustelualue(id, nimi, kuvaus, pvm));

        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelualueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
