package de.bonn.eis.services;

import de.bonn.eis.models.AnnotationRequestModel;

/**
 * Created by korovin on 3/18/2017.
 */
public interface AnnotationService {
    AnnotationRequestModel create(AnnotationRequestModel annotation);

    AnnotationRequestModel update(AnnotationRequestModel annotation);

    boolean delete(String id);
}
