package de.bonn.eis.services.impl;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.models.SemanticSearchResult;
import de.bonn.eis.services.AnnotationService;
import de.bonn.eis.services.namespaces.NamespaceEnum;
import org.gazzax.labs.solrdf.client.SolRDF;
import org.gazzax.labs.solrdf.client.UnableToAddException;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.gazzax.labs.solrdf.client.UnableToExecuteQueryException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by korovin on 3/18/2017.
 */
@Service
public class AnnotationServiceBean implements AnnotationService {
    private final SolRDF solrClient;

    public AnnotationServiceBean() throws RepositoryConfigException, RepositoryException, UnableToBuildSolRDFClientException {
        this.solrClient = SolRDF.newBuilder().build();
    }

    @Override
    public AnnotationRequestModel create(AnnotationRequestModel annotation) {
        try {
            List<Statement> statements = annotation.getStatements();
            this.solrClient.add(statements);
            return annotation;
        } catch (IllegalAccessException | RepositoryException | InstantiationException | UnableToAddException e) {
            return null;
        }
    }

    @Override
    public AnnotationRequestModel update(AnnotationRequestModel annotation) {
        return null;
    }

    @Override
    public SemanticSearchResult get(String id) {
        Resource mainAnno = ResourceFactory.createResource(NamespaceEnum.EX.getURI() + id);
        String sparql = String.format("SELECT * WHERE {<%1$s> ?p ?o} LIMIT 10", mainAnno.getURI());

        try {
            ResultSet result = solrClient.select(sparql);
            Model model = result.getResourceModel();

            if (model == null) return null;

            Statement slideProp = model.getProperty(mainAnno, ResourceFactory.createProperty(NamespaceEnum.EX.getURI() + "slide"));

            if (slideProp == null) return null;
            Integer slideId = slideProp.getInt();

            Statement deckProp = model.getProperty(mainAnno, ResourceFactory.createProperty(NamespaceEnum.EX.getURI() + "deck"));
            if (deckProp == null) return null;
            Integer deckId = deckProp.getInt();

            // TODO: parse result set to AnnotationRequestmodel
            return new SemanticSearchResult(slideId, deckId);
        } catch (UnableToExecuteQueryException e) {
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
