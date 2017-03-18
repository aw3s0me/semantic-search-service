package de.bonn.eis;

import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.services.helpers.AnnotationConverter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by korovin on 3/18/2017.
 */
@RunWith(SpringRunner.class)
public class AnnotationConverterTest {
    /**
     * RDfa
     * <span class="r_entity r_language automatic"
     *   resource="http://dbpedia.org/resource/German_language"
     *   typeof="schema:Language">
     *     <span class="r_prop r_name" property="rdfs:label">
     *         German
     *     </span>
     * </span>
     *
     *
     */
    @Test
    public void shouldConvertBodyToJenaModel() {
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
                        "  a schema:Language ;\n" +
                        "  rdfs:label \"Germany\" .");
        Model model = AnnotationConverter.getBodyModel(testAnnotation.getBody());
        System.out.println(model.toString());
        Resource res = model.getResource("http://dbpedia.org/resource/German_language");
        assertNotNull(res);
        assertEquals(res.getProperty(RDFS.label).getObject().toString(), "Germany");
    }
}
