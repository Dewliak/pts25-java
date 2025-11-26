package sk.uniba.fmph.dcs.terra_futura;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private int numberOfCards = 0;
    private List<List<Card>> cardGrid;
    private List<List<Boolean>> canBeActivatedGrid;
    private List<GridPosition> activationPattern;

    private static int GRID_SIZE = 5;

    private int max_left = 0;
    private int max_right = 0;
    private int max_up = 0;
    private int max_down = 0;
    private static int MAX_DISTANCE = 2;
    private static int OFFSET = 2;

    private boolean isInDistance(int a, int b) {
        return Math.abs(a - b) <= MAX_DISTANCE;
    }

    public Grid() {
        cardGrid = new ArrayList<>();
        canBeActivatedGrid = new ArrayList<>();
        activationPattern = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            List<Card> cards = new ArrayList<>();
            List<Boolean> canBeActivatedRow = new ArrayList<>();
            for (int j = 0; j < GRID_SIZE; j++) {
                cards.add(null);
                canBeActivatedRow.add(false);
            }
            cardGrid.add(cards);
            canBeActivatedGrid.add(canBeActivatedRow);
        }

    }

    private void clearCanBeActivatedGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                canBeActivatedGrid.get(i).set(j, false);
            }
        }
    }

    public Card getCard(GridPosition coordinate) {
        return cardGrid.get(coordinate.getY() + OFFSET).get(coordinate.getX() + OFFSET);
    }

    public boolean canPutCard(GridPosition coordinate) {
        //check if there is a card
        if (cardGrid.get(coordinate.getY() + OFFSET).get(coordinate.getX() + OFFSET) != null) {
            return false;
        }

        int x = coordinate.getX();
        int y = coordinate.getY();

        // check horizontally if it is inside the 3x3 grid
        if (!isInDistance(x, max_left) || !isInDistance(x, max_right)) {
            return false;
        }

        // check vertically if it is inside the 3x3 grid
        if (!isInDistance(y, max_up) || !isInDistance(y, max_down)) {
            return false;
        }

        //check if adjecent to a card
        boolean hasNeighbours = false;
        // check if exists upper neighbour
        hasNeighbours = hasNeighbours || ((y + 1 + OFFSET) < GRID_SIZE && (cardGrid.get(y + 1 + OFFSET).get(x + OFFSET) != null));
        // check if exists lower neighbour
        hasNeighbours = hasNeighbours || ((y - 1 + OFFSET) >= 0 && (cardGrid.get(y - 1 + OFFSET).get(x + OFFSET) != null));

        // check if exists neighbour on the right
        hasNeighbours = hasNeighbours || ((x + 1 + OFFSET) < GRID_SIZE && (cardGrid.get(y + OFFSET).get(x + 1 + OFFSET) != null));

        // check if exists neighbour on the right
        hasNeighbours = hasNeighbours || ((x - 1 + OFFSET) >= 0 && (cardGrid.get(y + OFFSET).get(x - 1 + OFFSET) != null));

        return hasNeighbours; // can only put if it has atleast one neighbour
    }

    public void putCard(GridPosition coordinate, Card card) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (!canPutCard(coordinate)) {
           return;
        }

        cardGrid.get(y + OFFSET).set(x + OFFSET, card);
        // update max_l max_r
        max_up = Math.max(max_up,y);
        max_down = Math.min(max_down,y);
        max_right = Math.max(max_right,x);
        max_left = Math.min(max_left,x);

        clearCanBeActivatedGrid();
        for (int j = 0; j < GRID_SIZE; j++) {
            canBeActivatedGrid.get(j).set(x + OFFSET, true);
            canBeActivatedGrid.get(y + OFFSET).set(j, true);
        }

    }


    public boolean canBeActivated(GridPosition coordinate) {
        return canBeActivatedGrid.get(coordinate.getY() + OFFSET).get(coordinate.getX() + OFFSET);
    }

    public void setActivated(GridPosition coordinate) {
        canBeActivatedGrid.get(coordinate.getY() + OFFSET).set(coordinate.getX() + OFFSET, false);
    }

    public void setActivationPattern(List<GridPosition> pattern) {
        activationPattern = pattern;
    }

    public void endTurn() {
        //
        clearCanBeActivatedGrid();
    }

    public void state() {

    }


}
