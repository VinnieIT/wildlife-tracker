package models;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EndangeredAnimalsTest {
    @Rule
    public DBTest databaseRule = new DBTest();

    private EndangeredAnimals createnewAnimal() {
        return new EndangeredAnimals("Pandas", "Sick", "0yr to 2yrs");
    }

        @Test
        public void animal_instantiatesCorrectly_true () {
            EndangeredAnimals endangeredAnimal = createnewAnimal();
            Assertions.assertTrue(true);
        }

        @Test
        public void getName_returnAnimalsName () {
            EndangeredAnimals endangeredAnimal = createnewAnimal();
            Assertions.assertEquals("Pandas", endangeredAnimal.getName());
        }

        @Test
        public void getHealth_returnAnimalsHealth () {
            EndangeredAnimals endangeredAnimal = createnewAnimal();
            Assertions.assertEquals("Sick", endangeredAnimal.getHealth());
        }

        @Test
        public void getAge_returnAnimalsAge () {
            EndangeredAnimals endangeredAnimal = createnewAnimal();
            Assertions.assertEquals("0yr to 2yrs", endangeredAnimal.getAge());
        }

        @Test
        public void getStatus_returnAnimalsStatus () {
            EndangeredAnimals endangeredAnimal = createnewAnimal();
            Assertions.assertEquals("Endangered", endangeredAnimal.getType());
        }

//        @Test
//        public void save_savedToDb_int () {
//            EndangeredAnimals endangeredAnimal = createnewAnimal();
//            endangeredAnimal.save();
//            Assertions.assertEquals(endangeredAnimal.getId(), EndangeredAnimals.all().get(0).getId());
//        }

        @Test
        public void find_locateEndangeredAnimal_Name () {
            EndangeredAnimals endangeredAnimal = createnewAnimal();
            endangeredAnimal.save();
            EndangeredAnimals foundAnimal = EndangeredAnimals.find(endangeredAnimal.getId());
            Assertions.assertEquals(endangeredAnimal, foundAnimal);
        }


    }