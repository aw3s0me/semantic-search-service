package de.bonn.eis.integration.search;

import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.models.SemanticDeckRelevanceResult;
import de.bonn.eis.models.SemanticSearchRequest;
import de.bonn.eis.services.AnnotationService;
import de.bonn.eis.services.SearchService;
import de.bonn.eis.services.impl.AnnotationServiceBean;
import de.bonn.eis.services.impl.SearchServiceBean;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by korovin on 3/20/2017.
 */
@RunWith(SpringRunner.class)
public class SearchServiceTest {
    SearchService searchService = new SearchServiceBean();
    AnnotationService annotationService = new AnnotationServiceBean();

    AnnotationRequestModel testAnnotation1 = new AnnotationRequestModel(
            "Nicholas_II_of_Russia",
            "http://dbpedia.org/page/Nicholas_II_of_Russia",
            "507f1f77bcf86cd799439011",
            "Person",
            2,
            1,
            "@prefix dbpedia: <http://dbpedia.org/ontology/> .\n" +
                    "@prefix dbr: <http://dbpedia.org/page/> .\n" +
                    "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
                    "<http://dbpedia.org/resource/Nicholas_II_of_Russia>\n" +
                    "  dbpedia:birthPlace dbr:Saint_Petersburg ;\n" +
                    "  a dbpedia:Person ;\n" +
                    "  rdfs:label \"Nicholas II of Russia\" .");
    AnnotationRequestModel testAnnotation2 = new AnnotationRequestModel(
            "World_War_II",
            "http://dbpedia.org/page/World_War_II",
            "507f1f77bcf87cd799439012",
            "Event",
            3,
            2,
            "@prefix dbpedia: <http://dbpedia.org/ontology/> .\n" +
                    "@prefix dbr: <http://dbpedia.org/page/> .\n" +
                    "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
                    "<http://dbpedia.org/resource/World_War_II>\n" +
                    "  dbpedia:commander dbr:Hirohito ;\n" +
                    "  a dbpedia:Event ;\n" +
                    "  rdfs:label \"World War II\" .");

    public SearchServiceTest() throws UnableToBuildSolRDFClientException {
    }

    @Test
    public void searchByTypeTest() throws UnableToBuildSolRDFClientException, InterruptedException {
        annotationService.create(testAnnotation1);
        annotationService.create(testAnnotation2);
        TimeUnit.SECONDS.sleep(10);
        Collection<SemanticDeckRelevanceResult> res = searchService.searchByTypeAndProperty(
                new SemanticSearchRequest("http://dbpedia.org/ontology/Event", null));

        assertTrue(res.size() == 1);
        SemanticDeckRelevanceResult result = (SemanticDeckRelevanceResult) res.toArray()[0];

        assertTrue(result.getDeck() == 3);
        assertTrue(result.getCount() == 1);
        annotationService.delete(testAnnotation1.getId());
        annotationService.delete(testAnnotation2.getId());
    }

    @Test
    public void searchByPropertyTest() throws UnableToBuildSolRDFClientException, InterruptedException {
        annotationService.create(testAnnotation1);
        annotationService.create(testAnnotation2);
        TimeUnit.SECONDS.sleep(10);
        Collection<SemanticDeckRelevanceResult> res = searchService.searchByTypeAndProperty(
                new SemanticSearchRequest(null, "http://dbpedia.org/ontology/birthPlace"));

        assertTrue(res.size() == 1);
        SemanticDeckRelevanceResult result = (SemanticDeckRelevanceResult) res.toArray()[0];

        assertTrue(result.getDeck() == 2);
        assertTrue(result.getCount() == 1);
        annotationService.delete(testAnnotation1.getId());
        annotationService.delete(testAnnotation2.getId());
    }
}
