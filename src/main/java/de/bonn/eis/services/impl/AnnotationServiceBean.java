package de.bonn.eis.services.impl;

import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.services.AnnotationService;
import org.apache.jena.rdf.model.Statement;
import org.gazzax.labs.solrdf.client.SolRDF;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
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
            return null;
            // return new AnnotationRequestModel();
        } catch (RepositoryException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AnnotationRequestModel update(AnnotationRequestModel annotation) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
