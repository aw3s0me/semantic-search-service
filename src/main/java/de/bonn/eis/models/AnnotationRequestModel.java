package de.bonn.eis.models;

import org.apache.commons.collections4.ListUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;
import org.openrdf.repository.RepositoryException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    private static final String EX_NS = "http://example.org/";
    private static final String DBPEDIA_NS = "http://dbpedia.org/ontology/";

    public AnnotationRequestModel(String keyword, String resource, String id, String typeof, Integer deck, Integer slide, String body) {
        this.keyword = keyword;
        this.resource = resource;
        this.id = id;
        this.typeof = typeof;
        this.deck = deck;
        this.slide = slide;
        this.body = body;
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

    private Statement getAnnotationStatement(Resource resource, String propertyUri, RDFNode obj) {
        return ResourceFactory.createStatement(
                resource,
                ResourceFactory.createProperty(propertyUri),
                obj);
    }

    public List<Statement> getStatementListFromBody(Model body, Resource mainSubject) {
        StmtIterator iter = body.listStatements();
        List<Statement> statements = new ArrayList<>();

        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subj = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            statements.add(ResourceFactory.createStatement(mainSubject, predicate, object));
        }

        return statements;
    }

    public Model getBodyModel() {
        Model model = ModelFactory.createDefaultModel();
        InputStream is = new ByteArrayInputStream(body.getBytes());
        model.read(is, null, "TTL");
        return model;
    }

    public List<Statement> getStatements() throws RepositoryException, IllegalAccessException, InstantiationException {
        List<Statement> statements = new ArrayList<>();
        Model bodyModel = this.getBodyModel();
        Resource mainAnnoSubject = ResourceFactory.createResource(EX_NS + getId());
        List<Statement> bodyStatements = this.getStatementListFromBody(bodyModel, mainAnnoSubject);

        statements.add(this.getAnnotationStatement(
                mainAnnoSubject,
                EX_NS + "slide",
                ResourceFactory.createPlainLiteral(getSlide().toString())));
        statements.add(this.getAnnotationStatement(
                mainAnnoSubject,
                EX_NS + "deck",
                ResourceFactory.createPlainLiteral(getDeck().toString())));
//        statements.add(this.getAnnotationStatement(
//                mainAnnoSubject,
//                RDF.type.getURI(),
//                ResourceFactory.createResource(DBPEDIA_NS + getTypeof())));
        statements.add(this.getAnnotationStatement(
                mainAnnoSubject,
                RDFS.label.getURI(),
                ResourceFactory.createPlainLiteral(getKeyword())
        ));

        return ListUtils.union(statements, bodyStatements);
    }
}
