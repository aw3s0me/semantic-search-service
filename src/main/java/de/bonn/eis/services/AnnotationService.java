package de.bonn.eis.services;

import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.models.SemanticSearchResult;

/**
 * Created by korovin on 3/18/2017.
 */
public interface AnnotationService {
    AnnotationRequestModel create(AnnotationRequestModel annotation);

    AnnotationRequestModel update(AnnotationRequestModel annotation);

    SemanticSearchResult get(String id);

    boolean delete(String id);
}
