package de.bonn.eis.models;

/**
 * Created by korovin on 3/18/2017.
 */
public class AnnotationRequestModel {
    private String body;
    private Integer slide;
    private Integer deck;
    private String typeof;
    private String id;
    private String resource;
    private String keyword;

    public AnnotationRequestModel(String keyword, String resource, String id, String typeof, Integer deck, Integer slide, String body) {
        this.keyword = keyword;
        this.resource = resource;
        this.id = id;
        this.typeof = typeof;
        this.deck = deck;
        this.slide = slide;
        this.body = body;
    }

    public AnnotationRequestModel() {
    }

    public String getBody() {
        return body;
    }

    public Integer getSlide() {
        return slide;
    }

    public Integer getDeck() {
        return deck;
    }

    public String getTypeof() {
        return typeof;
    }

    public String getId() {
        return id;
    }

    public String getResource() {
        return resource;
    }

    public String getKeyword() {
        return keyword;
    }
}
