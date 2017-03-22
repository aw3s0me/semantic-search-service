package de.bonn.eis.integration.search;

import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueString;
import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.models.SemanticDeckRelevanceResult;
import de.bonn.eis.models.SemanticSearchRequest;
import de.bonn.eis.services.AnnotationService;
import de.bonn.eis.services.SearchService;
import de.bonn.eis.services.impl.AnnotationServiceBean;
import de.bonn.eis.services.impl.SearchServiceBean;
import de.bonn.eis.services.impl.arq.ARQFilterEnum;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.junit.After;
import org.junit.Before;
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
            "<span class=\"r_entity r_organization\" typeof=\"http://dbpedia.org/ontology/Person\" " +
                    "data-id=\"r_HyL67eehg\" resource=\"http://dbpedia.org/resource/Nicholas_II_of_Russia\" " +
                    "data-hasqtip=\"0\" aria-describedby=\"qtip-0\"> " +
                    "<span class=\"r_prop r_name\" property=\"http://www.w3.org/2000/01/rdf-schema#label\">" +
                    "Nicholas II of Russia</span>" +
                    "<meta property=\"http://dbpedia.org/ontology/birthPlace\" resource=\"http://dbpedia.org/page/Saint_Petersburg\" />" +
                    "</span>");
    AnnotationRequestModel testAnnotation2 = new AnnotationRequestModel(
            "World_War_II",
            "http://dbpedia.org/page/World_War_II",
            "507f1f77bcf87cd799439012",
            "Event",
            3,
            2,
            "<span class=\"r_entity r_organization\" typeof=\"http://dbpedia.org/ontology/Event\" " +
                    "data-id=\"r_HyL67eehg\" resource=\"http://dbpedia.org/resource/World_War_II\" " +
                    "data-hasqtip=\"0\" aria-describedby=\"qtip-0\"> " +
                    "<span class=\"r_prop r_name\" property=\"http://www.w3.org/2000/01/rdf-schema#label\">" +
                    "World War II</span>" +
                    "<meta property=\"http://dbpedia.org/ontology/commander\" resource=\"http://dbpedia.org/page/Hirohito\" />" +
                    "</span>");

    AnnotationRequestModel testAnnotation3 = new AnnotationRequestModel(
            "Vietnam_War",
            "http://dbpedia.org/page/Vietnam_War",
            "507f1237bcf87cd799439012",
            "Event",
            5,
            2,
            "<span class=\"r_entity r_organization\" typeof=\"http://dbpedia.org/ontology/Event\" " +
                    "data-id=\"r_HyL67eehg\" resource=\"http://dbpedia.org/resource/Vietnam_War\" " +
                    "data-hasqtip=\"0\" aria-describedby=\"qtip-0\"> " +
                    "<span class=\"r_prop r_name\" property=\"http://www.w3.org/2000/01/rdf-schema#label\">" +
                    "Vietnam War</span>" +
                    "<meta property=\"http://dbpedia.org/ontology/place\" resource=\"http://dbpedia.org/page/North_Vietnam\" />" +
                    "</span>");

    @Before
    public void initAnnoations() throws InterruptedException {
        annotationService.create(testAnnotation1);
        annotationService.create(testAnnotation2);
        annotationService.create(testAnnotation3);
        TimeUnit.SECONDS.sleep(10);
    }

    @After
    public void removeAnnotations() {
        annotationService.delete(testAnnotation1.getId());
        annotationService.delete(testAnnotation2.getId());
        annotationService.delete(testAnnotation3.getId());
    }

    public SearchServiceTest() throws UnableToBuildSolRDFClientException {
    }

    @Test
    public void searchByTypeTest() throws UnableToBuildSolRDFClientException, InterruptedException {
        Collection<SemanticDeckRelevanceResult> res = searchService.searchByTypeAndProperty(
                new SemanticSearchRequest("http://dbpedia.org/ontology/Person", null));

        assertTrue(res.size() == 1);
        SemanticDeckRelevanceResult result = (SemanticDeckRelevanceResult) res.toArray()[0];

        assertTrue(result.getDeck() == 2);
        assertTrue(result.getCount() == 1);
    }

    @Test
    public void searchByPropertyTest() throws UnableToBuildSolRDFClientException, InterruptedException {
        Collection<SemanticDeckRelevanceResult> res = searchService.searchByTypeAndProperty(
                new SemanticSearchRequest(null, "http://dbpedia.org/ontology/birthPlace"));

        assertTrue(res.size() == 1);
        SemanticDeckRelevanceResult result = (SemanticDeckRelevanceResult) res.toArray()[0];

        assertTrue(result.getDeck() == 2);
        assertTrue(result.getCount() == 1);
    }

    @Test
    public void searchByPropertyAndTypeTest() throws UnableToBuildSolRDFClientException, InterruptedException {
        Collection<SemanticDeckRelevanceResult> res = searchService.searchByTypeAndProperty(
                new SemanticSearchRequest("http://dbpedia.org/ontology/Event", "http://dbpedia.org/ontology/place"));

        assertTrue(res.size() == 1);
        SemanticDeckRelevanceResult result = (SemanticDeckRelevanceResult) res.toArray()[0];

        assertTrue(result.getDeck() == 5);
        assertTrue(result.getCount() == 1);
    }

    @Test
    public void searchByPropertyValueTest() throws InterruptedException {
        Collection<SemanticDeckRelevanceResult> res = searchService.searchByTypeAndProperty(
                new SemanticSearchRequest("http://dbpedia.org/ontology/Event", "http://www.w3.org/2000/01/rdf-schema#label"),
                ARQFilterEnum.EQUALS.getExpression("o", new NodeValueString("Vietnam War")));

        assertTrue(res.size() == 1);
        SemanticDeckRelevanceResult result = (SemanticDeckRelevanceResult) res.toArray()[0];

        assertTrue(result.getDeck() == 5);
        assertTrue(result.getCount() == 1);
    }

    @Test
    public void searchByPropertyRegexTest() throws InterruptedException {
        Collection<SemanticDeckRelevanceResult> res = searchService.searchByTypeAndProperty(
                new SemanticSearchRequest("http://dbpedia.org/ontology/Event", "http://www.w3.org/2000/01/rdf-schema#label"),
                ARQFilterEnum.REGEX.getExpression("o", new NodeValueString("War")));

        assertTrue(res.size() == 2);
    }
}
