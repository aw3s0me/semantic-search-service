package de.bonn.eis.services.impl;

import com.github.anno4j.model.Annotation;
import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.services.AnnotationService;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.springframework.stereotype.Service;

import static de.bonn.eis.services.helpers.AnnotationConverter.createAnnotationFromRequest;

/**
 * Created by korovin on 3/18/2017.
 */
@Service
public class AnnotationServiceBean implements AnnotationService {
    private final RDFConnection connection;

    public AnnotationServiceBean(String connectionUri) throws RepositoryConfigException, RepositoryException {
        this.connection = RDFConnectionFactory.connect(connectionUri);
    }

    @Override
    public AnnotationRequestModel create(AnnotationRequestModel annotation) {
        try {
            Annotation anno = createAnnotationFromRequest(annotation);
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
