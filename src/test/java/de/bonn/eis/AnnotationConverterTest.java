package de.bonn.eis;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDFS;
import de.bonn.eis.models.AnnotationRequestModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.semarglproject.rdf.ParseException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by korovin on 3/18/2017.
 */
@RunWith(SpringRunner.class)
public class AnnotationConverterTest {
    private AnnotationRequestModel testAnnotation = new AnnotationRequestModel(
            "Nicholas_II_of_Russia",
            "http://dbpedia.org/page/Nicholas_II_of_Russia",
            "507f1f77bcf86cd799439011",
            "Person",
            "2",
            "1",
            "<span class=\"r_entity r_organization\" typeof=\"http://dbpedia.org/ontology/Person\" " +
                    "data-id=\"r_HyL67eehg\" resource=\"http://dbpedia.org/resource/Nicholas_II_of_Russia\" " +
                    "data-hasqtip=\"0\" aria-describedby=\"qtip-0\"> " +
                    "<span class=\"r_prop r_name\" property=\"http://www.w3.org/2000/01/rdf-schema#label\">" +
                    "Nicholas II of Russia</span>" +
                    "<meta property=\"http://dbpedia.org/ontology/birthPlace\" resource=\"http://dbpedia.org/page/Saint_Petersburg\" />" +
                    "</span>");

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
    public void shouldConvertBodyToJenaModel() throws ParseException {
        Model model = this.testAnnotation.getBodyModel();
        System.out.println(model.toString());
        Resource res = model.getResource("http://dbpedia.org/resource/Nicholas_II_of_Russia");
        assertNotNull(res);
        assertEquals(res.getProperty(RDFS.label).getObject().toString(), "Nicholas II of Russia");
    }

    @Test
    public void shouldGetAllStatements() throws IllegalAccessException, InstantiationException, ParseException {
        List<Statement> statementList = this.testAnnotation.getStatements();
        statementList.forEach(System.out::println);

        assertEquals(5, statementList.size());
    }
}
