package models;

import org.sql2o.Connection;

import java.util.List;

public class EndangeredAnimals extends Animal{
    private static final String DB_TYPE = "Endangered";

    public EndangeredAnimals(String name, String health, String age) {
        this.name = name;
        this.health = health;
        this.age = age;
        this.type = DB_TYPE;
    }

    /* ----------------- models.DB OPERATIONS ---------------- */

    public static List<EndangeredAnimals> all(){
        String sql = "SELECT * FROM animals where type=:type";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("type",DB_TYPE)
                    .executeAndFetch(EndangeredAnimals.class);
        }
    }

    public static EndangeredAnimals find(int searchId){
        String sql = "SELECT * FROM animals where (id=:id AND type=:type)";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id",searchId)
                    .addParameter("type",DB_TYPE)
                    .executeAndFetchFirst(EndangeredAnimals.class);
        }
    }

}
