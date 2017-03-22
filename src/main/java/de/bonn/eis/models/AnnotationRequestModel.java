package de.bonn.eis.models;

import com.hp.hpl.jena.rdf.model.*;
import de.bonn.eis.services.impl.rdfa.RDFaParser;
import de.bonn.eis.services.namespaces.NamespaceEnum;
import org.apache.commons.collections4.ListUtils;
import org.semarglproject.rdf.ParseException;

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

    private Statement getAnnotationStatement(Resource resource, String propertyUri, RDFNode obj) {
        return ResourceFactory.createStatement(
                resource,
                ResourceFactory.createProperty(propertyUri),
                obj);
    }

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

    public Resource getMainAnnotationResource() {
        return ResourceFactory.createResource(NamespaceEnum.EX.getURI() + this.getId());
    }

    public final static Resource getMainAnnotationResource(String annoId) {
        return ResourceFactory.createResource(NamespaceEnum.EX.getURI() + annoId);
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

    public Model getBodyModel() throws ParseException {
        System.out.println(this.getBody());
        return RDFaParser.parse(this.getBody());
    }

    public List<Statement> getStatements() throws IllegalAccessException, InstantiationException, ParseException {
        List<Statement> statements = new ArrayList<>();
        Model bodyModel = this.getBodyModel();
        Resource mainAnnoSubject = ResourceFactory.createResource(NamespaceEnum.EX.getURI() + getId());
        List<Statement> bodyStatements = this.getStatementListFromBody(bodyModel, mainAnnoSubject);

        statements.add(this.getAnnotationStatement(
                mainAnnoSubject,
                NamespaceEnum.EX.getURI() + "slide",
                ResourceFactory.createPlainLiteral(getSlide().toString())));
        statements.add(this.getAnnotationStatement(
                mainAnnoSubject,
                NamespaceEnum.EX.getURI() + "deck",
                ResourceFactory.createPlainLiteral(getDeck().toString())));

        return ListUtils.union(statements, bodyStatements);
    }
}
