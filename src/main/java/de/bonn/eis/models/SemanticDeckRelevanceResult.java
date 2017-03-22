package de.bonn.eis.models;

/**
 * Created by korovin on 3/20/2017.
 */
public class SemanticDeckRelevanceResult {
    private String deck;
    private String count;

    public SemanticDeckRelevanceResult(String deck, String count) {
        this.deck = deck;
        this.count = count;
    }

    public String getDeck() {
        return deck;
    }

    public String getCount() {
        return count;
    }
}
