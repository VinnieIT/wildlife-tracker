package models;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;

public class Rangers {
    private int id;
    private String name;

    public Rangers(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rangers ranger = (Rangers) o;
        return name.equals(ranger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /*-------------- models.DB OPERATIONS --------------*/
    public void save(){
        if(!crossCheck()){
            String sql = "INSERT INTO rangers(name) VALUES(:name)";
            try(Connection con = DB.sql2o.open()){
                this.id = (int) con.createQuery(sql,true)
                        .addParameter("name",this.name)
                        .executeUpdate()
                        .getKey();
            } catch (Sql2oException ex) {
                System.out.println(ex);
            }
        }
    }

    public static List<Rangers> all(){
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM rangers")
                    .executeAndFetch(Rangers.class);
        }
    }

    public static Rangers find(int searchId){
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM rangers WHERE id=:id")
                    .addParameter("id",searchId)
                    .executeAndFetchFirst(Rangers.class);
        }
    }

    public List<Sightings> mySightings(){
        String sql = "SELECT * FROM sightings WHERE rangersid=:id";
        try(Connection con = DB.sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeAndFetch(Sightings.class);
        }
    }

    public void delete(){
        try(Connection con = DB.sql2o.open()){
            con.createQuery("DELETE FROM rangers WHERE id=:id")
                    .addParameter("id",this.id)
                    .executeUpdate();
        }
    }

    private boolean crossCheck(){
        for(Rangers ranger: Rangers.all()){
            if(this.getName().equals(ranger.getName())){
                this.id = ranger.id;
                this.name = ranger.name;
                return true;
            }
        }
        return false;
    }

}
