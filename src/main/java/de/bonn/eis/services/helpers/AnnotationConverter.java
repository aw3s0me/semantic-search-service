package de.bonn.eis.services.helpers;

import com.github.anno4j.model.Annotation;
import de.bonn.eis.models.AnnotationRequestModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.openrdf.repository.RepositoryException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by korovin on 3/18/2017.
 */
public final class AnnotationConverter {
    public static final Annotation createAnnotationFromRequest(AnnotationRequestModel annotation) throws RepositoryException, IllegalAccessException, InstantiationException {
        Model bodyModel = AnnotationConverter.getBodyModel(annotation.getBody());
        return null;
    }

    public static final Model getBodyModel(String body) {
        Model model = ModelFactory.createDefaultModel();
        InputStream is = new ByteArrayInputStream(body.getBytes());
        model.read(is, null, "TTL");
        return model;
    }
}
