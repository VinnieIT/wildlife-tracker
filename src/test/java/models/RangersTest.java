package models;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RangersTest {

    @Rule
    public DBTest databaseRule = new DBTest();

    private Rangers newRanger() {
        return new Rangers("Vincent");
    }

    @Test
    public void ranger_instantiatesCorrectly() {
        Rangers ranger = newRanger();
        assertTrue(true);
    }

    @Test
    public void getName_returnRangerName_true() {
        Rangers ranger = newRanger();
        Assertions.assertEquals("Vincent", ranger.getName());
    }

    @Test
    public void save_getRangerId() {
        Rangers ranger = newRanger();
        int idBefore = ranger.getId();
        ranger.save();
        Assertions.assertNotEquals(idBefore, ranger.getId());
    }

    @Test
    public void all_getAllRangers() {
        Rangers ranger = newRanger();
        Rangers ranger1 = new Rangers("Greatness");
        ranger.save();
        ranger1.save();
        assertTrue(Rangers.all().contains(ranger));
        assertTrue(Rangers.all().contains(ranger1));
    }

    @Test
    public void find_getParticularRanger() {
        Rangers ranger = newRanger();
        Rangers ranger1 = new Rangers("Greatness");
        ranger.save();
        ranger1.save();
        Assertions.assertEquals(Rangers.find(ranger.getId()), ranger);
    }

    @Test
    public void add_preventDuplicateRanger() {
        Rangers ranger1 = newRanger();
        Rangers ranger2 = newRanger();
        ranger1.save();
        ranger2.save();
        for (Rangers ranger : Rangers.all()) {
            if (ranger2.equals(ranger)) {
                ranger2.delete();
                break;
            }
        }
        Assertions.assertEquals(3, Rangers.all().size());
    }
}

