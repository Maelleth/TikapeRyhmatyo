package tikape;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        port(12348);
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        String jdbcOsoite = "jdbc:sqlite:lopullinenHarkkatyo.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Database database = new Database(jdbcOsoite);

        database.setDebugMode(true);
        // database.update("INSERT INTO Kayttaja(nimi) VALUES ('Blerine')");

        KayttajaDao kayttajaDao = new KayttajaDao(database);
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        KeskustelualueenViestiketjutDao kvDao = new KeskustelualueenViestiketjutDao(database, keskustelualueDao);
        ViestitDao viestitDao = new ViestitDao(database, kayttajaDao, kvDao);

        get("/sivu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("teksti", "Alue");
            map.put("keskustelualueet", keskustelualueDao.findAll());
            map.put("teksti2", "Viestiketjujen määrä");
            map.put("viestiketjut", kvDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
    }

}
