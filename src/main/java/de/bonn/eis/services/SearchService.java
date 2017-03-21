package de.bonn.eis.services;

import com.hp.hpl.jena.sparql.expr.Expr;
import de.bonn.eis.models.SemanticDeckRelevanceResult;
import de.bonn.eis.models.SemanticSearchRequest;
import de.bonn.eis.models.SemanticSearchResult;

import java.util.Collection;

/**
 * Created by korovin on 3/18/2017.
 */
public interface SearchService {
    Collection<SemanticDeckRelevanceResult> searchByTypeAndProperty(SemanticSearchRequest request);

    Collection<SemanticDeckRelevanceResult> searchByTypeAndProperty(SemanticSearchRequest request, Expr filter);

    Collection<SemanticSearchResult> searchByKeywords(Collection<String> keywords);
}
