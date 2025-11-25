package sk.uniba.fmph.dcs.terra_futura;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private int numberOfCards = 0;
    private List<List<Card>> cardGrid;
    private List<List<Boolean>> canBeActivatedGrid;
    private static int GRID_SIZE = 3;
    public Grid() {
        // todo: implement this, it is just aplaceholder so we dont get linter error
        cardGrid = new ArrayList<>();
        canBeActivatedGrid = new ArrayList<>();

        for(int i = 0; i < GRID_SIZE; i++) {
            List<Card> cards = new ArrayList<>();
            List<Boolean> canBeActivatedRow= new ArrayList<>();
            for(int j = 0; j < GRID_SIZE; j++) {
                cards.add(null);
                canBeActivatedRow.add(false);
            }
            cardGrid.add(cards);
            canBeActivatedGrid.add(canBeActivatedRow);
        }

    }

    private void clearCanBeActivatedGrid() {
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                canBeActivatedGrid.get(i).set(j, false);
            }
        }
    }

    public Card getCard(GridPosition coordinate) {
        return cardGrid.get(coordinate.getY()+1).get(coordinate.getX()+1);
    }

    public boolean canPutCard(GridPosition coordinate) {
        return cardGrid.get(coordinate.getY()+1).get(coordinate.getX()+1) == null;
    }

    public void putCard(GridPosition coordinate, Card card) {
        if(canPutCard(coordinate)) {
            cardGrid.get(coordinate.getY()+1).set(coordinate.getX()+1, card);
        }
        clearCanBeActivatedGrid();
        for(int j = 0; j < GRID_SIZE; j++) {
            canBeActivatedGrid.get(j).set(coordinate.getY()+1, true);
            canBeActivatedGrid.get(coordinate.getY()+1).set(j, true);
        }
    }


    public boolean canBeActivated(GridPosition coordinate) {
        return  canBeActivatedGrid.get(coordinate.getY()+1).get(coordinate.getX()+1);
    }

    public void setActivated(GridPosition coordinate) {
        canBeActivatedGrid.get(coordinate.getY()+1).set(coordinate.getX()+1, false);
    }

    public void setActivationPattern(List<GridPosition> pattern) {}

    public void endTurn(){

    }

    public void state(){

    }


}
