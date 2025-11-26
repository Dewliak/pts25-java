package sk.uniba.fmph.dcs.terra_futura;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SelectRewardTest {

    class FakeCard extends Card {
        List<Resource> received = new ArrayList<>();
        boolean canPut = true;

        @Override
        public boolean canPutResources(List<Resource> res) {
            return canPut;
        }

        @Override
        public void putResources(List<Resource> res) {
            received.addAll(res);
        }

        @Override
        public String state() {
            JSONObject stateJSON = new JSONObject();
            stateJSON.put("canPut", canPut);
            stateJSON.put("received", received);

            return stateJSON.toString();
        }

        public List<Resource> getReceived() {
            return received;
        }
    }

    // test1 player cannot select nothing before setting the reward -> state doesnt change
    @Test
    public void testBeforeSetReward() {
        SelectReward selectReward = new SelectReward();

        String stateBefore = selectReward.state();

        assertFalse(selectReward.canSelectReward(3, Resource.Green));
        assertEquals(stateBefore, selectReward.state());
    }

    // test2 test if we can select good rewards, good person, wrong etc...
    @Test
    public void testWhatCanWeSelect() {
        SelectReward selectReward = new SelectReward();

        int assistingPlayer = 3;
        List<Resource> resources = List.of(Resource.Green, Resource.Red);
        Card card = new FakeCard();

        selectReward.setReward(assistingPlayer, card, resources);

        String stateAfterSetReward = selectReward.state();

        assertTrue(selectReward.canSelectReward(3, Resource.Green)); // Good player, good resource
        assertTrue(selectReward.canSelectReward(3, Resource.Red)); // Good player good resource

        assertFalse(selectReward.canSelectReward(1, Resource.Green)); // bad player good resource
        assertFalse(selectReward.canSelectReward(0, Resource.Red)); // bad player good resource
        assertFalse(selectReward.canSelectReward(0, Resource.Yellow)); // bad player bad resource
        assertFalse(selectReward.canSelectReward(3, Resource.Yellow)); // good player bad resoruce

        assertEquals(stateAfterSetReward, selectReward.state());

    }

    // test3 apply reward when it is legal and only then
    // test4 clear select reward after selectReward -> also tested here
    @Test
    public void testLegalSetReward() {
        SelectReward selectReward = new SelectReward();
        int assistingPlayer = 3;
        List<Resource> resources = List.of(Resource.Green, Resource.Red);
        Card card = new FakeCard();

        selectReward.setReward(assistingPlayer, card, resources);

        String selectRewardStateBefore = selectReward.state();

        selectReward.selectReward(3, Resource.Green);

        String selectRewardStateAfter = selectReward.state();

        assertEquals(List.of(Resource.Green), ((FakeCard) card).getReceived());
        assertNotEquals(selectRewardStateBefore, selectRewardStateAfter);
    }

    // test5 if wrong player or resource ,doesnt modify the state -> next two tests
    @Test
    public void testIllegalSetRewardWrongPlayer() {
        SelectReward selectReward = new SelectReward();
        int assistingPlayer = 3;
        List<Resource> resources = List.of(Resource.Green, Resource.Red);
        Card card = new FakeCard();

        String cardStateBefore = card.state();


        selectReward.setReward(assistingPlayer, card, resources);

        String selectRewardStateBeforeSelectReward = selectReward.state();

        selectReward.selectReward(1, Resource.Green);

        assertTrue(((FakeCard) card).received.isEmpty());
        assertEquals(cardStateBefore, card.state());
        assertEquals(selectRewardStateBeforeSelectReward, selectReward.state());
    }

    @Test
    public void testIllegalSetRewardWrongResource() {
        SelectReward selectReward = new SelectReward();
        int assistingPlayer = 3;
        List<Resource> resources = List.of(Resource.Green, Resource.Red);
        Card card = new FakeCard();

        String cardStateBefore = card.state();


        selectReward.setReward(assistingPlayer, card, resources);

        String selectRewardStateBeforeSelectReward = selectReward.state();

        selectReward.selectReward(3, Resource.Yellow);

        assertTrue(((FakeCard) card).received.isEmpty());
        assertEquals(cardStateBefore, card.state());
        assertEquals(selectRewardStateBeforeSelectReward, selectReward.state());
    }


}
