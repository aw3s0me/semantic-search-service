package de.bonn.eis.services.impl;

import de.bonn.eis.models.SemanticSearchResult;
import de.bonn.eis.services.SearchService;

import java.util.Collection;

/**
 * Created by korovin on 3/18/2017.
 */
public class SearchServiceBean implements SearchService {
    @Override
    public Collection<SemanticSearchResult> searchByCriterias(Collection<Object> criterias) {
        return null;
    }

    @Override
    public Collection<SemanticSearchResult> searchByKeywords(Collection<String> keywords) {
        return null;
    }
}
