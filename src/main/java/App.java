import models.EndangeredAnimals;
import models.NonEndangeredAnimals;
import models.Rangers;
import models.Sightings;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.sql.Timestamp;
import java.util.*;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");



        //get: Homepage
        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Sightings> allSightings = Sightings.all();


//            model.put("sightings", allSightings);

//
            List<Sightings> data = new ArrayList<>();


            for (Sightings sight: allSightings){
                String nameOfAnimal = sight.getnameOfAnimal();
                String locationOfSighing = sight.getlocationofSighting();
                Timestamp timestamp = sight.getTimestamp();
                int rangerId = sight.getRangerid();
                Sightings tempSighing = new Sightings(nameOfAnimal,locationOfSighing,rangerId);
                data.add(tempSighing);
            }

            for (Sightings x: data)
                System.out.println(x.toString());

            model.put("sightings", data);

            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        //get: retrieve endangered animals
        get("/animals/endangered",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("endangered", EndangeredAnimals.all());
            return new ModelAndView(model,"endangered-animals.hbs");
        },new HandlebarsTemplateEngine());

        //get: retrieve non-endangered animals
        get("/animals/not-endangered",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("normal", NonEndangeredAnimals.all());
            return new ModelAndView(model,"nonendangered-animals.hbs");
        },new HandlebarsTemplateEngine());

        //get: New Sighting Form
        get("/sighting/new",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sightings", Sightings.all());
            return new ModelAndView(model,"sightings-form.hbs");
        },new HandlebarsTemplateEngine());

        //Post: Sighting Submission
        post("/sighting/new",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String rangerName = request.queryParams("rangersName").trim();
            String animalName = request.queryParams("nameOfAnimal").trim();
            String animalAge = request.queryParams("ageOfAnimal").trim();
            String animalHealth = request.queryParams("animalHealthStatus").trim();
            String location = request.queryParams("animalsLocation").trim();
            String animalType = request.queryParams("animalType").trim();

            Rangers newRanger = new Rangers(rangerName);
            newRanger.save();

            if(animalType.equalsIgnoreCase("Endangered")){
                EndangeredAnimals endangeredAnimal = new EndangeredAnimals(animalName,animalHealth,animalAge);
                endangeredAnimal.save();
                Sightings newSighting = new Sightings(endangeredAnimal.getName(),location,newRanger.getId());
                newSighting.save();
            }
            else{
                NonEndangeredAnimals normalAnimal = new NonEndangeredAnimals(animalName,animalHealth,animalAge);
                normalAnimal.save();
                Sightings newSighting = new Sightings(normalAnimal.getName(),location,newRanger.getId());
                newSighting.save();
            }
            return new ModelAndView(model,"success.hbs");
        },new HandlebarsTemplateEngine());

        //get: retrieve all sightings by location
        get("/sightings",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sightings", Sightings.all());
            return new ModelAndView(model,"sightings-location.hbs");
        },new HandlebarsTemplateEngine());

        //get: retrieve all sightings by location
        get("/sightings/:location/details",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String filter = request.params("animalsLocation");
            model.put("location",filter);
            model.put("sightings", Sightings.getAllSightingsInLocation(filter));
            return new ModelAndView(model,"sightings-locdetails.hbs");
        },new HandlebarsTemplateEngine());

        //get: ranger details
        get("/rangers/:id/details",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params("id"));
            Rangers foundRanger = Rangers.find(id);
            List<Sightings> mySightings = foundRanger.mySightings();
            model.put("ranger",foundRanger);
            model.put("sightings",mySightings);
            return new ModelAndView(model,"viewAllRangers.hbs");
        },new HandlebarsTemplateEngine());

        //get: all rangers
        get("/rangers",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("rangers", Rangers.all());
            return new ModelAndView(model,"viewAllRangers.hbs");
        },new HandlebarsTemplateEngine());

        //get: form to add animals to specific ranger
        get("/rangers/:id/sighting/new",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params("id"));
            Rangers specificRanger = Rangers.find(id);
            model.put("specificRanger",specificRanger);
            model.put("sightings", Sightings.all());
            return new ModelAndView(model,"sightings-form.hbs");
        },new HandlebarsTemplateEngine());

        //post: form to add animals to specific ranger
        post("/rangers/:id/sighting/new",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params("id"));
            Rangers specificRanger = Rangers.find(id);
            String animalName = request.queryParams("nameOfAnimal").trim();
            String animalAge = request.queryParams("ageOfAnimal").trim();
            String animalHealth = request.queryParams("animalHealthStatus").trim();
            String location = request.queryParams("animalsLocation").trim();
            String animalType = request.queryParams("animalType").trim();

            if(animalType.equalsIgnoreCase("Endangered")){
                EndangeredAnimals endangeredAnimal = new EndangeredAnimals(animalName,animalHealth,animalAge);
                endangeredAnimal.save();
                Sightings newSighting = new Sightings(endangeredAnimal.getName(),location,specificRanger.getId());
                newSighting.save();
            }
            else{
                NonEndangeredAnimals normalAnimal = new NonEndangeredAnimals(animalName,animalHealth,animalAge);
                normalAnimal.save();
                Sightings newSighting = new Sightings(normalAnimal.getName(),location,specificRanger.getId());
                newSighting.save();
            }

            return new ModelAndView(model,"success.hbs");
        },new HandlebarsTemplateEngine());

    }
}
