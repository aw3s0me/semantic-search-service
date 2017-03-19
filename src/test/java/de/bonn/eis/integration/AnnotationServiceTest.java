package de.bonn.eis.integration;

import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.services.AnnotationService;
import de.bonn.eis.services.impl.AnnotationServiceBean;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by korovin on 3/19/2017.
 */
@RunWith(SpringRunner.class)
public class AnnotationServiceTest {
    @Test
    public void insertAnnotationTest() throws RepositoryConfigException, RepositoryException, UnableToBuildSolRDFClientException {
        AnnotationService service = new AnnotationServiceBean();
        AnnotationRequestModel testAnnotation = new AnnotationRequestModel(
                "Nicholas_II_of_Russia",
                "http://dbpedia.org/page/Nicholas_II_of_Russia",
                "065672d4-0c03-11e7-93ae-92361f002671",
                "Person",
                2,
                1,
                "@prefix schema: <http://schema.org/> .\n" +
                        "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
                        "<http://dbpedia.org/resource/German_language>\n" +
                        "  a dbpedia:Language ;\n" +
                        "  rdfs:label \"Germany\" .");

        service.create(testAnnotation);

        // Test select statement
        assertEquals(1, 1);
    }

}
