package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        JsonObject jobj = request.getAsJsonObject();
        int pot = jobj.get("pot").getAsInt();
        int bet = 0;

        JsonArray players = jobj.getAsJsonArray("players");
        for (JsonElement player : players) {
            JsonObject playerObj = player.getAsJsonObject();
            String playerName = playerObj.get("name").getAsString();
            if (playerName.equals("noIDEa")) {
                JsonArray cards = playerObj.getAsJsonArray("hole_cards");
                int counter = 0;
                for (JsonElement card : cards) {
                    JsonObject cardObj = card.getAsJsonObject();
                    int rank = cardObj.get("rank").getAsInt();
                    if (rank > 10) {
                        counter++;
                    }
                }
                if (counter == 2) {
                    bet = (int) Math.ceil(pot * 0.5);                }
            }
        }

        return bet;
    }

    public static void showdown(JsonElement game) {
    }
}
