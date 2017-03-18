package de.bonn.eis.services;

import de.bonn.eis.models.Annotation;

/**
 * Created by korovin on 3/18/2017.
 */
public interface AnnotationService {
    Annotation create(Annotation annotation);

    Annotation update(Annotation annotation);

    boolean delete(String id);
}
