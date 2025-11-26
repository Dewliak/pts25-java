package sk.uniba.fmph.dcs.terra_futura;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GridTest {
    class FakeCard extends Card {
        public final String name;
        public FakeCard(String name) {
            super();
            this.name = name;
        }


        @Override
        public String state() {
            return name;
        }


    }

    @Test
    public void testGrid() {

        String expectedState = """
                {"grid": [
                    [
                        "Upper",
                        null,
                        null
                    ],
                    [
                        "Starter",
                        null,
                        null
                    ],
                    [
                        "Lower",
                        null,
                        null
                    ]
                ]}""";

        Grid grid = new Grid(new FakeCard("Starter")); // puts it in 0,0 on creation

        grid.putCard(new GridPosition(0,1), new FakeCard("Upper"));
        grid.putCard(new GridPosition(0,-1), new FakeCard("Lower"));

        assertEquals(expectedState, grid.state());
    }

    @Test
    public void testGridCanPut() {

        /*
        So the grid looks like this

        # # # # #
        # # U # #
        # # S R B
        # # L # #
        # # # # #

        we want to test these numbered
           -2 -1  0  1  2  = X
           ---------------
         2| #  #  4  #  #
         1| #  #  U  #  2
         0| #  3  S  R  B
        -1| #  #  L  1  #
        -2| #  #  #  #  #
         ||
         Y
         */

        Grid grid = new Grid(new FakeCard("Starter")); // puts it in 0,0 on creation

        grid.putCard(new GridPosition(0,1), new FakeCard("Upper"));
        grid.putCard(new GridPosition(0,-1), new FakeCard("Lower"));
        grid.putCard(new GridPosition(1,0), new FakeCard("Right"));
        grid.putCard(new GridPosition(2,0), new FakeCard("Border"));

        assertTrue(grid.canPutCard(new GridPosition(1,-1))); // 1  = tests inside bounds
        assertTrue(grid.canPutCard(new GridPosition(2,1))); // 2 = tests inside bound
        assertFalse(grid.canPutCard(new GridPosition(-1,0))); // 3 = tests outside bound
        assertFalse(grid.canPutCard(new GridPosition(0,2))); // 4 = tests outside bound
    }

    @Test
    public void testPutCardStateChange(){
        Grid grid = new Grid(new FakeCard("Starter")); // puts it in 0,0 on creation

        grid.putCard(new GridPosition(0,1), new FakeCard("Upper"));
        grid.putCard(new GridPosition(0,-1), new FakeCard("Lower"));
        grid.putCard(new GridPosition(1,0), new FakeCard("Right"));
        grid.putCard(new GridPosition(2,0), new FakeCard("Border"));

        String stateBeforePut = grid.state();

        FakeCard toAddCard = new FakeCard("New");
        GridPosition toAddPosition = new GridPosition(2,1);

        assertTrue(grid.canPutCard(new GridPosition(1,-1))); // 1  = tests inside bounds
        grid.putCard(toAddPosition, toAddCard);

        assertEquals(toAddCard,grid.getCard(toAddPosition)); // so we get the same card

        assertNotEquals(stateBeforePut, grid.state()); // the states should be equal
    }
}
