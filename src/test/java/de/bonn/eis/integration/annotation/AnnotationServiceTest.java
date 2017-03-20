package de.bonn.eis.integration.annotation;

import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.models.SemanticSearchResult;
import de.bonn.eis.services.AnnotationService;
import de.bonn.eis.services.impl.AnnotationServiceBean;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by korovin on 3/19/2017.
 */
@RunWith(SpringRunner.class)
public class AnnotationServiceTest {
    private AnnotationRequestModel testAnnotation = new AnnotationRequestModel(
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
                    "  rdfs:label \"Nicholas II of Russia\" .");

    @Test
    public void insertAnnotationTest() throws RepositoryConfigException, RepositoryException, UnableToBuildSolRDFClientException, InterruptedException {
        AnnotationService service = new AnnotationServiceBean();

        // SELECT * WHERE { ?s <http://purl.org/dc/elements/1.1/date> ?o } LIMIT 10
        // service.get("http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/ProductFeature142");
        boolean isExist = service.isExist(testAnnotation.getId());
        if (isExist) {
            service.delete(testAnnotation.getId());
        }

        service.create(testAnnotation);
        TimeUnit.SECONDS.sleep(10);
        SemanticSearchResult res = service.get(testAnnotation.getId());
        // Test select statement
        assertTrue((res.getSlideId().equals(1)));
        assertTrue((res.getDeckId().equals(2)));
        service.delete(testAnnotation.getId());
    }

    @Test
    public void deleteAnnotationTest() throws RepositoryConfigException, RepositoryException, UnableToBuildSolRDFClientException {
        AnnotationService service = new AnnotationServiceBean();
        service.create(testAnnotation);

        boolean res = service.delete(testAnnotation.getId());

        assertTrue(res);
    }

    @Test
    public void isExistAnnotationTest() throws RepositoryConfigException, RepositoryException, UnableToBuildSolRDFClientException, InterruptedException {
        AnnotationService service = new AnnotationServiceBean();
        service.create(testAnnotation);
        TimeUnit.SECONDS.sleep(5);
        boolean isExist = service.isExist(testAnnotation.getId());

        assertTrue(isExist);
        service.delete(testAnnotation.getId());
        TimeUnit.SECONDS.sleep(5);
        // need to wait until is deleted completely
        boolean isNotExist = service.isExist(testAnnotation.getId());
        assertFalse(isNotExist);
    }

    @Test
    public void updateAnnotationTest() throws RepositoryConfigException, RepositoryException, UnableToBuildSolRDFClientException, InterruptedException {
        AnnotationService service = new AnnotationServiceBean();
        AnnotationRequestModel newAnnotation = new AnnotationRequestModel(
                "Nicholas_II_of_Russia",
                "http://dbpedia.org/page/Nicholas_III_of_Russia",
                "507f1f77bcf86cd799439011",
                "Person",
                5,
                5,
                "@prefix dbpedia: <http://dbpedia.org/ontology/> .\n" +
                        "@prefix dbr: <http://dbpedia.org/page/> .\n" +
                        "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
                        "<http://dbpedia.org/resource/Nicholas_II_of_Russia>\n" +
                        "  dbpedia:predecessor dbr:Alexander_III_of_Russia ;\n" +
                        "  rdfs:label \"Nicholas II of Russia\" .");
        service.create(testAnnotation);
        TimeUnit.SECONDS.sleep(5);
        service.update(newAnnotation);
        TimeUnit.SECONDS.sleep(5);
        SemanticSearchResult res = service.get(testAnnotation.getId());
        // Test select statement
        assertTrue((res.getSlideId().equals(5)));
        assertTrue((res.getDeckId().equals(5)));
    }
}
