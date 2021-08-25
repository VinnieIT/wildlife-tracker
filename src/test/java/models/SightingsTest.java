package models;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

class SightingsTest {

    @Rule
    public DBTest databaseRule = new DBTest();

    private Sightings newSighting() {
        return new Sightings("Ducklings", "Nairobi", 1);
    }
    private Sightings secondSighting() {
        return new Sightings("Lions", "Nakuru", 2);
    }
    @Test
    public void sighting_instantiatesCorrectly(){
        Sightings sighting = newSighting();
        Assertions.assertTrue(sighting instanceof Sightings);
    }

    @Test
    public void getAnimalId_returnAnimalName_true(){
        Sightings sighting = newSighting();
        Assertions.assertEquals("Ducklings",sighting.getnameOfAnimal());
    }

    @Test
    public void getLocation_returnSightingLocation_true(){
        Sightings sighting = newSighting();
        Assertions.assertEquals("Nairobi",sighting.getlocationofSighting());
    }

    @Test
    public void getRangerId_returnRangerId_true(){
        Sightings sighting = newSighting();
        Assertions.assertEquals(1,sighting.getRangerid());
    }

    @Test
    public void getTimestamp_returnRangerId_true(){
        Sightings sighting = newSighting();
        Timestamp testTimestamp = new Timestamp(new Date().getTime());
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        Assertions.assertEquals(dateFormat.format(testTimestamp),dateFormat.format(sighting.getTimestamp()));
    }

    @Test
    public void save_savesSightingIntoDB_true(){
        Sightings sighting = newSighting();
        int idBefore = sighting.getId();
        sighting.save();
        Assertions.assertNotEquals(idBefore,sighting.getId());
    }

    @Test
    public void find_searchForSighting_true(){
        Sightings sighting1 = newSighting();
        Sightings sighting2 = secondSighting();
        sighting1.save();
        sighting2.save();
        System.out.println();
        Assertions.assertEquals(Sightings.find(sighting2.getId()), sighting2);
    }

    @Test
    public void all_getAllSightings_true(){
        Sightings sighting1 = newSighting();
        Sightings sighting2 = secondSighting();
        sighting1.save();
        sighting2.save();
        Assertions.assertTrue(Sightings.all().contains(sighting1));
        Assertions.assertTrue(Sightings.all().contains(sighting2));
    }

    @Test
    public void all_getAllLocations_true(){
        Sightings sighting1 = newSighting();
        Sightings sighting2 = secondSighting();
        sighting1.save();
        sighting2.save();
        Assertions.assertEquals(2, Sightings.getAllLocations().size());
    }

    @Test
    public void filter_getSightingInSingleLocation_true(){
        Sightings sighting1 = newSighting();
        Sightings sighting2 = secondSighting();
        Sightings sighting3 = new Sightings("Wild Cows","Nyeri",3);
        sighting1.save();
        sighting2.save();
        sighting3.save();
        Assertions.assertTrue(Sightings.getAllSightingsInLocation("Nairobi").contains(sighting1));
        Assertions.assertTrue(Sightings.getAllSightingsInLocation("Nyeri").contains(sighting3));
    }


}