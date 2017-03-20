package de.bonn.eis.models;

/**
 * Created by korovin on 3/20/2017.
 */
public class SemanticDeckRelevanceResult {
    private Integer deck;
    private Integer count;

    public SemanticDeckRelevanceResult(Integer deck, Integer count) {
        this.deck = deck;
        this.count = count;
    }

    public Integer getDeck() {
        return deck;
    }

    public Integer getCount() {
        return count;
    }
}
