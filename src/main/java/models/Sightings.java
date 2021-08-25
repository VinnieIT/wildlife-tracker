package models;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Sightings {
    private String nameofanimal;
    private int rangerid;
    private String locationofSighting;
    private Timestamp timestamp;
    private int id;

    public Sightings(String nameofanimal, String locationofSighting, int rangerid) {
        this.nameofanimal = nameofanimal;
        this.locationofSighting = locationofSighting.trim();
        this.timestamp = new Timestamp(new Date().getTime());
        this.rangerid = rangerid;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sightings sighting = (Sightings) o;
        return nameofanimal.equals(sighting.nameofanimal) &&
                rangerid == sighting.rangerid &&
                Objects.equals(locationofSighting, sighting.locationofSighting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameofanimal, locationofSighting, rangerid);
    }

    public String getnameOfAnimal() {
        return nameofanimal;
    }

    public String getlocationofSighting() {
        return locationofSighting;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getReadableTimestamp(){
        return DateFormat.getDateTimeInstance().format(getTimestamp());
    }

    public int getRangerid() {
        return rangerid;
    }

    public int getId() {
        return id;
    }

    /*-------------- models.DB OPERATIONS --------------*/
    public void save(){
        String sql = "INSERT INTO sightings(nameofanimal, locationofsighting, timestamp, rangerid) values (:nameofanimal,:locationofsighting,:timestamp,:rangerid)";
        try(Connection con = DB.sql2o.open()){
            this.id = (int) con.createQuery(sql,true)
                    .addParameter("nameofanimal", this.nameofanimal)
                    .addParameter("locationofsighting",this.locationofSighting)
                    .addParameter("timestamp",this.timestamp)
                    .addParameter("rangerid",this.rangerid)
                    .executeUpdate()
                    .getKey();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public String getRangerName(){
        return Rangers.find(rangerid).getName();
    }

    public static List<Sightings> all(){
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM sightings")
                    .executeAndFetch(Sightings.class);// fetches a list
        }
    }

    public static Sightings find(int searchId){
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM sightings WHERE id=:id")
                    .addParameter("id",searchId)
                    .executeAndFetchFirst(Sightings.class);
        }
    }

    public static List<String> getAllLocations(){
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT locationofSighting FROM sightings")
                    .executeAndFetch(String.class);
        }
    }

    public static List<Sightings> getAllSightingsInLocation(String locationFilter){
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM sightings where locationofSighting = :locationofSighting")
                    .addParameter("locationofSighting",locationFilter)
                    .executeAndFetch(Sightings.class);
        }
    }

    @Override
    public String toString() {
        return "Sightings{" +
                "nameofanimal='" + nameofanimal + '\'' +
                ", rangerid=" + rangerid +
                ", locationofSighting='" + locationofSighting + '\'' +
                ", timestamp=" + timestamp +
                ", id=" + id +
                '}';
    }
}
