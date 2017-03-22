package de.bonn.eis;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import de.bonn.eis.services.impl.rdfa.RDFaParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.semarglproject.rdf.ParseException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by korovin on 3/22/2017.
 */
@RunWith(SpringRunner.class)
public class RDFaConverterTest {
    private static final String testRdfa = "<span class=\"r_entity r_organization\" typeof=\"http://dbpedia.org/ontology/Organization\" " +
            "data-id=\"r_HyL67eehg\" resource=\"http://dbpedia.org/resource/Congress_of_Vienna\" " +
            "data-hasqtip=\"0\" aria-describedby=\"qtip-0\">" +
            "<span class=\"r_prop r_name\" property=\"http://www.w3.org/2000/01/rdf-schema#label\">" +
            "Congress of Vienna</span></span>";

    @Test
    public void isRdfaConvertedToRightModelTest() throws ParseException {
        Model model = RDFaParser.parse(testRdfa);

        System.out.println(model.toString());

        assertTrue(model.contains(ResourceFactory.createStatement(
                ResourceFactory.createResource("http://dbpedia.org/resource/Congress_of_Vienna"),
                ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#label"),
                ResourceFactory.createPlainLiteral("Congress of Vienna"))));

        assertTrue(model.contains(ResourceFactory.createStatement(
                ResourceFactory.createResource("http://dbpedia.org/resource/Congress_of_Vienna"),
                ResourceFactory.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                ResourceFactory.createResource("http://dbpedia.org/ontology/Organization"))));
    }
}
