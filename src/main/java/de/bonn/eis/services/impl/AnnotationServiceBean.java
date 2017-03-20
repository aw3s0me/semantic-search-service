package de.bonn.eis.services.impl;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.models.SemanticSearchResult;
import de.bonn.eis.services.AnnotationService;
import de.bonn.eis.services.impl.solr.SolrRequestHelper;
import de.bonn.eis.services.namespaces.NamespaceEnum;
import org.gazzax.labs.solrdf.client.SolRDF;
import org.gazzax.labs.solrdf.client.UnableToAddException;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.gazzax.labs.solrdf.client.UnableToExecuteQueryException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
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
        Resource mainAnno = AnnotationRequestModel.getMainAnnotationResource(id);
        String sparql = String.format("SELECT * WHERE {<%1$s> ?p ?o} LIMIT 10", mainAnno.getURI());

        try {
            ResultSet result = solrClient.select(sparql);
            Integer deckId = null;
            Integer slideId = null;

            while (result.hasNext()) {
                QuerySolution solution = result.nextSolution();

                Resource node = solution.getResource("?p");

                if (node.getURI().equals(NamespaceEnum.EX.getURI() + "slide")) {
                    slideId = solution.getLiteral("?o").getInt();
                } else if (node.getURI().equals(NamespaceEnum.EX.getURI() + "deck")) {
                    deckId = solution.getLiteral("?o").getInt();
                }

                // if deck id and slide id initialized, no need to iterate anymore
                if (deckId != null && slideId != null) {
                    return new SemanticSearchResult(deckId, slideId);
                }
            }
            // if could not find deck id and slide id
            return null;
        } catch (UnableToExecuteQueryException e) {
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        Resource mainAnno = AnnotationRequestModel.getMainAnnotationResource(id);
        String sparql = String.format("DELETE WHERE {<%1$s> ?p ?o}", mainAnno.getURI());

        try {
            ResponseEntity<String> parsedResponse = SolrRequestHelper.postDeleteForEntity(sparql);
            return SolrRequestHelper.isRequestSucc(parsedResponse);
        } catch (JAXBException e) {
            return false;
        }
    }

    @Override
    public boolean isExist(String id) {
        Resource mainAnno = AnnotationRequestModel.getMainAnnotationResource(id);
        String sparql = String.format("ASK {<%1$s> ?p ?o}", mainAnno.getURI());
        try {
            return solrClient.ask(sparql);
        } catch (UnableToExecuteQueryException e) {
            return false;
        }
    }
}
