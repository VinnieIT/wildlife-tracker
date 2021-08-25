package models;


import org.sql2o.Sql2o;

public class DB {
    public static Sql2o sql2o= new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker","postgres","hello");
//    public static Sql2o sql2o= new Sql2o("jdbc:postgresql://ec2-52-3-130-181.compute-1.amazonaws.com:5432/d8pblams15al9p","bkzygooenuvxbb","365a651f45c130a0f6bb5a548cbf921330760dfab9289f9a56b5b81694baca25");

}


