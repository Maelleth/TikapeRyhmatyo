package tikape;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViestitDao implements Dao<Viestit, Integer> {

    private Database database;
    private Dao<Kayttaja, Integer> kayttajaDao;
    private Dao<KeskustelualueenViestiketjut, Integer> kvDao;
    private Map<Integer, List<Viestit>> viestiketjunViestit;

    public ViestitDao(Database database, Dao<Kayttaja, Integer> kayttajaDao, Dao<KeskustelualueenViestiketjut, Integer> kvDao) {
        this.database = database;
        this.kayttajaDao = kayttajaDao;
        this.kvDao = kvDao;
        this.viestiketjunViestit = new HashMap<>();
    }

    public void lisaa(Integer kayttajanId, Integer viestiketjuId, String sisalto) throws SQLException {
        String sql = "INSERT INTO Viestit (kayttajan_id, viestiketju_id, sisalto, pvm) VALUES (" + kayttajanId + ", " + viestiketjuId + ", '" + sisalto + "', strftime('%s','now'))";

        this.database.update(sql);
    }

    @Override
    public Viestit findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestit WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer kayttajanId = rs.getInt("kayttajan_id");
        Integer viestiketjuId = rs.getInt("viestiketju_id");
        String sisalto = rs.getString("sisalto");
        Timestamp pvm = rs.getTimestamp("pvm");

        Kayttaja kayttaja = kayttajaDao.findOne(kayttajanId);
        KeskustelualueenViestiketjut kv = kvDao.findOne(viestiketjuId);

        Viestit v = new Viestit(id, sisalto, sisalto, pvm, kayttaja, kv);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viestit> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestit");
        ResultSet rs = stmt.executeQuery();
        List<Viestit> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer kayttajanId = rs.getInt("kayttajan_id");
            Integer viestiketjuId = rs.getInt("viestiketju_id");
            String sisalto = rs.getString("sisalto");
            Timestamp pvm = rs.getTimestamp("pvm");

            Kayttaja kayttaja = kayttajaDao.findOne(kayttajanId);
            KeskustelualueenViestiketjut kv = kvDao.findOne(viestiketjuId);

            Viestit v = new Viestit(id, sisalto, sisalto, pvm, kayttaja, kv);
            viestit.add(v);

            if (!this.viestiketjunViestit.containsKey(viestiketjuId)) {
                this.viestiketjunViestit.put(viestiketjuId, new ArrayList<>());

            }
            this.viestiketjunViestit.get(viestiketjuId).add(v);

        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;

    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int viestienMäärä(Integer id) {
 
        return this.viestiketjunViestit.get(id).size();
    }

}

