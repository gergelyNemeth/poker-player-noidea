package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    static final String VERSION = "0.2 strategy";

    public static int betRequest(JsonElement request) {
        JsonObject jobj = request.getAsJsonObject();
        int currentBuyIn = jobj.get("current_buy_in").getAsInt();

        int minRaise = jobj.get("minimum_raise").getAsInt();
        int bet = 0;
        List<String> highCards = new ArrayList<>(Arrays.asList("10", "J", "Q", "K", "A"));
        List<String> allCards = new ArrayList<>(Arrays.asList("", "", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "J", "Q", "K", "A"));
        List<String> commCards = new ArrayList<>();


        JsonArray comm_cards = jobj.getAsJsonArray("community_cards");
        for (JsonElement comm_card : comm_cards) {
            JsonObject comm_cardObj = comm_card.getAsJsonObject();
            String comm_rank = comm_cardObj.get("rank").getAsString();
            commCards.add(comm_rank);
        }

        JsonArray players = jobj.getAsJsonArray("players");
        for (JsonElement player : players) {
            JsonObject playerObj = player.getAsJsonObject();
            String playerName = playerObj.get("name").getAsString();
            if (playerName.equals("noIDEa")) {
                JsonArray cards = playerObj.getAsJsonArray("hole_cards");
                int ourBet = playerObj.get("bet").getAsInt();
                int highCardCounter = 0;
                String previousCard = null;
                for (JsonElement card : cards) {

                    JsonObject cardObj = card.getAsJsonObject();
                    String rank = cardObj.get("rank").getAsString();
                    if (highCards.contains(rank)) {
                        highCardCounter++;
                    }
                    if (previousCard != null) {
                        if (previousCard.equals(rank)) {
                            int rankValue = allCards.indexOf(rank);
                            if (rankValue > 7) {
                                bet = currentBuyIn - ourBet + minRaise;
                            } else {
                                bet = currentBuyIn - ourBet;
                            }
                            if (commCards.size() > 0 && rankValue < 8) {
                                if (commCards.contains(rank)) {
                                    bet = currentBuyIn + minRaise;
                                } else {
                                    bet = 0;
                                }
                            }
                        }
                    }
                    if (commCards.contains(rank)) {
                        if (highCards.contains(rank)) {
                            bet = currentBuyIn + minRaise;
                        } else {
                            bet = 0;
                        }

                    }
                    previousCard = rank;
                }
                if (highCardCounter == 2) {
                    bet = currentBuyIn - ourBet + minRaise;
                }
            }
        }

        return bet;
    }

    public static void showdown(JsonElement game) {
    }

}
