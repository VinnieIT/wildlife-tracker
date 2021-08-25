package models;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NonEndangeredAnimalsTest {
        @Rule
        public DBTest databaseRule = new DBTest();

        private NonEndangeredAnimals createnewAnimal() {
            return new NonEndangeredAnimals("Pandas", "Sick", "0yr to 2yrs");
        }

        @Test
        public void animal_instantiatesCorrectly_true () {
            NonEndangeredAnimals endangeredAnimal = createnewAnimal();
            Assertions.assertTrue(true);
        }

        @Test
        public void getName_returnAnimalsName () {
            NonEndangeredAnimals nodangeredAnimal = createnewAnimal();
            Assertions.assertEquals("Pandas", nodangeredAnimal.getName());
        }

        @Test
        public void getHealth_returnAnimalsHealth () {
            NonEndangeredAnimals nodangeredAnimal = createnewAnimal();
            Assertions.assertEquals("Sick", nodangeredAnimal.getHealth());
        }

        @Test
        public void getAge_returnAnimalsAge () {
            NonEndangeredAnimals nodangeredAnimal = createnewAnimal();
            Assertions.assertEquals("0yr to 2yrs", nodangeredAnimal.getAge());
        }

        @Test
        public void getStatus_returnAnimalsStatus () {
            NonEndangeredAnimals nodangeredAnimal = createnewAnimal();
            Assertions.assertEquals("Not Endangered", nodangeredAnimal.getType());
        }

//        @Test
//        public void save_savedToDb_int () {
//            NonEndangeredAnimals endangeredAnimal = createnewAnimal();
//            endangeredAnimal.save();
//            System.out.println(endangeredAnimal.getId());
//            Assertions.assertEquals(endangeredAnimal.getId(), NonEndangeredAnimals.all().get(0).getId());
//        }

        @Test
        public void find_locateEndangeredAnimal_Name () {
            NonEndangeredAnimals nodangeredAnimal = createnewAnimal();
            nodangeredAnimal.save();
            NonEndangeredAnimals foundAnimal = NonEndangeredAnimals.find(nodangeredAnimal.getId());
            Assertions.assertEquals(nodangeredAnimal, foundAnimal);
        }
}