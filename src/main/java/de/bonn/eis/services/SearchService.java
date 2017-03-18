package de.bonn.eis.services;

import de.bonn.eis.models.SemanticSearchResult;

import java.util.Collection;

/**
 * Created by korovin on 3/18/2017.
 */
public interface SearchService {
    Collection<SemanticSearchResult> searchByCriterias(Collection<Object> criterias);

    Collection<SemanticSearchResult> searchByKeywords(Collection<String> keywords);
}
