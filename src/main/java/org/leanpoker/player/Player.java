package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    static final String VERSION = "0.1 strategy";

    public static int betRequest(JsonElement request) {
        JsonObject jobj = request.getAsJsonObject();
        int pot = jobj.get("pot").getAsInt();
        int bet = 0;
        List<String> cardList = new ArrayList<>(Arrays.asList("J", "Q", "K", "A"));

        JsonArray players = jobj.getAsJsonArray("players");
        for (JsonElement player : players) {
            JsonObject playerObj = player.getAsJsonObject();
            String playerName = playerObj.get("name").getAsString();
            if (playerName.equals("noIDEa")) {
                JsonArray cards = playerObj.getAsJsonArray("hole_cards");
                int highCardCounter = 0;
                String previousCard = null;
                for (JsonElement card : cards) {
                    JsonObject cardObj = card.getAsJsonObject();
                    String rank = cardObj.get("rank").getAsString();
                    if (cardList.contains(rank)) {
                        highCardCounter++;
                    }
                    if (previousCard != null) {
                        if (previousCard.equals(rank)) {
                           bet = (int) Math.ceil(pot * 0.8);
                        }
                    }
                    previousCard = rank;
                }
                if (highCardCounter == 2) {
                    bet = (int) Math.ceil(pot * 0.5);
                }
            }
        }

        return bet;
    }

    public static void showdown(JsonElement game) {
    }
}
