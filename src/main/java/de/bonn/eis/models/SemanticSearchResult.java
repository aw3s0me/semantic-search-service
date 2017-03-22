package de.bonn.eis.models;

/**
 * Created by korovin on 3/18/2017.
 */
public class SemanticSearchResult {
    String slideId;
    String deckId;

    public SemanticSearchResult(String deckId, String slideId) {
        this.deckId = deckId;
        this.slideId = slideId;
    }

    public String getSlideId() {
        return slideId;
    }

    public String getDeckId() {
        return deckId;
    }
}
