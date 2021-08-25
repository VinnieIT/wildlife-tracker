package models;


import org.sql2o.*;

public class DB {
    public static Sql2o sql2o= new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker","postgres","hello");

}
//    private static URI url;
//    public static Sql2o sql2o;
//    static Logger logger = LoggerFactory.getLogger(DB.class);
//    static {
//
//        try{
//            if(System.getenv("DATABASE_URL") == null){
//                url = new URI("postgres://localhost:5432/wildlife_tracker");
//            }
//            else {
//                url = new URI(System.getenv("DATABASE_URL"));
//            }
//
//            int port = url.getPort();
//            String host = url.getHost();
//            String path = url.getPath();
//            String username = (url.getUserInfo() == null) ? "nick" : url.getUserInfo().split(":")[0];
//            String password = (url.getUserInfo() == null) ? "00000000" : url.getUserInfo().split(":")[1];
//            sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
//        } catch (URISyntaxException e){
//            logger.error("unable to connect to database");
//        }
//
//    }

