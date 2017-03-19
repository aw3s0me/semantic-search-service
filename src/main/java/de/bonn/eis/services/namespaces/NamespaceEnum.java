package de.bonn.eis.services.namespaces;

/**
 * Created by korovin on 3/19/2017.
 */
public enum NamespaceEnum {
    EX("http://example.org/"),
    DBPEDIA("http://dbpedia.org/ontology/");

    private final String uri;

    private NamespaceEnum(String text) {
        this.uri = text;
    }

    public String getURI() {
        return uri;
    }
}
