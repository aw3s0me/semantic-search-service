package de.bonn.eis.models;

/**
 * Created by korovin on 3/20/2017.
 */
public class SemanticSearchRequest {
    private String property;
    private String type;
    private String object;

    public SemanticSearchRequest(String object, String type, String property) {
        this.object = object;
        this.type = type;
        this.property = property;
    }

    public SemanticSearchRequest(String type, String property) {
        this.type = type;
        this.property = property;
    }

    public String getObject() {
        return object;
    }

    public String getType() {
        return type;
    }

    public String getProperty() {
        return property;
    }
}
