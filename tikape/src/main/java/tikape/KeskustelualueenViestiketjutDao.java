package tikape;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeskustelualueenViestiketjutDao implements Dao<KeskustelualueenViestiketjut, Integer> {

    private Database database;
    private Dao<Keskustelualue, Integer> keskustelualueDao;
    private Map<Integer, List<KeskustelualueenViestiketjut>> keskustelualueenKetjut;
    private List<Integer> keskustelualueenIdt;

    public KeskustelualueenViestiketjutDao(Database database, Dao<Keskustelualue, Integer> keskustelualueDao) {
        this.database = database;
        this.keskustelualueDao = keskustelualueDao;
        this.keskustelualueenKetjut = new HashMap<>();
        this.keskustelualueenIdt = new ArrayList<>();
    }

    public void lisaa(Integer kId, String nimi) throws SQLException {
       // Keskustelualue k = keskustelualueDao.findOne(kId);

        String sql = "INSERT INTO Keskustelualueen_viestiketjut (keskustelualueen_id, nimi, pvm) VALUES (" + kId + ", '" + nimi + "', strftime('%s','now'))";

        this.database.update(sql);
    }

    @Override
    public KeskustelualueenViestiketjut findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualueen_viestiketjut WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer keskustelualueenId = rs.getInt("keskustelualueen_id");
        String nimi = rs.getString("nimi");
        Timestamp pvm = rs.getTimestamp("pvm");
        
        this.keskustelualueenIdt.add(keskustelualueenId);

        Keskustelualue k = keskustelualueDao.findOne(keskustelualueenId);
        KeskustelualueenViestiketjut kv = new KeskustelualueenViestiketjut(id, nimi, pvm, k);

        rs.close();
        stmt.close();
        connection.close();

        return kv;
    }

    public List<Integer> getKeskustelualueenIdt() {
        return keskustelualueenIdt;
    }

    @Override
    public List<KeskustelualueenViestiketjut> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualueen_viestiketjut");
        ResultSet rs = stmt.executeQuery();

        List<KeskustelualueenViestiketjut> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer keskustelualueenId = rs.getInt("keskustelualueen_id");
            String nimi = rs.getString("nimi");
            Timestamp pvm = rs.getTimestamp("pvm");

            Keskustelualue k = keskustelualueDao.findOne(keskustelualueenId);
            KeskustelualueenViestiketjut kv = new KeskustelualueenViestiketjut(id, nimi, pvm, k);
            viestiketjut.add(kv);

            if (!this.keskustelualueenKetjut.containsKey(keskustelualueenId)) {
                this.keskustelualueenKetjut.put(keskustelualueenId, new ArrayList<>());

            }
            this.keskustelualueenKetjut.get(keskustelualueenId).add(kv);

        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;

    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int ketjujenMaara(Integer id) {
        return this.keskustelualueenKetjut.get(id).size();
    }
}

