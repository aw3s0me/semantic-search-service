package de.bonn.eis.services.impl;

import com.github.anno4j.Anno4j;
import de.bonn.eis.models.Annotation;
import de.bonn.eis.services.AnnotationService;
import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDBFactory;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;

/**
 * Created by korovin on 3/18/2017.
 */
public class AnnotationServiceBean implements AnnotationService {
    private String schemaName = "schema";
    private Dataset dataset;
    private Anno4j anno4j;

    public AnnotationServiceBean() throws RepositoryConfigException, RepositoryException {
        this.dataset = TDBFactory.createDataset(schemaName);
        this.anno4j = new Anno4j();
    }

    @Override
    public Annotation create(Annotation annotation) {
        return null;
    }

    @Override
    public Annotation update(Annotation annotation) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
