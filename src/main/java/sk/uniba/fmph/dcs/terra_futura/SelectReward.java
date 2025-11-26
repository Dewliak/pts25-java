package sk.uniba.fmph.dcs.terra_futura;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectReward {
    private List<Resource> selection;
    private Card assistingCard = null;
    private int assistingPlayer = -1;
    public SelectReward() {
        selection = new ArrayList<>();
    }

    /**
     * @param playerId the id of the player
     * @param card the card granting the reward
     * @param reward the reward you want to choose
     */
    public void setReward(final int playerId, final Card card, final List<Resource> reward) {
        assistingPlayer = playerId;
        selection = new ArrayList<>(reward); // so we have the copy of the original
        assistingCard = card;

    }
    /**
     * @param reward the reward you want to check if available
     * @return boolean if you can select it
     */
    public boolean canSelectReward(final int playerId, final Resource reward) {
        return playerId == assistingPlayer && selection.contains(reward);
    }

    /**
     * @param reward the reward to be chosen
     */
    public void selectReward(final int playerId, final Resource reward) {
        if(!canSelectReward(playerId, reward)) {
            return;
        }

        if(!assistingCard.canPutResources(List.of(reward))){
            return;
        }

        assistingCard.putResources(List.of(reward));

        selection.clear();
        assistingCard = null;
        assistingPlayer = -1;
    }

    /**
     * @return returns the state of the class
     */
    public String state() {
        JSONObject jsonState = new JSONObject();

        jsonState.put("assistingPlayer", assistingPlayer);
        jsonState.put("assistingCard", assistingCard);
        jsonState.put("selection", selection);

        return jsonState.toString(4);
    }
}
