package de.bonn.eis.models;

/**
 * Created by korovin on 3/18/2017.
 */
public class SemanticSearchResult {
    Integer slideId;
    Integer deckId;

    public SemanticSearchResult(Integer deckId, Integer slideId) {
        this.deckId = deckId;
        this.slideId = slideId;
    }

    public Integer getSlideId() {
        return slideId;
    }

    public Integer getDeckId() {
        return deckId;
    }
}
