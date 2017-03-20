package de.bonn.eis.api;

import de.bonn.eis.models.SemanticDeckRelevanceResult;
import de.bonn.eis.models.SemanticSearchRequest;
import de.bonn.eis.services.SearchService;
import de.bonn.eis.services.impl.SearchServiceBean;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by korovin on 3/18/2017.
 */
@RestController
public class SearchController {
    private SearchService service = new SearchServiceBean();

    public SearchController() throws UnableToBuildSolRDFClientException {
    }

    @RequestMapping(
            value = "/api/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SemanticDeckRelevanceResult>> search(@RequestBody SemanticSearchRequest request) {
        // TODO: check request and call different methods
        Collection<SemanticDeckRelevanceResult> results = service.searchByTypeAndProperty(request);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
