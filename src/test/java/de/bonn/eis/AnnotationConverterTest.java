package de.bonn.eis;

import de.bonn.eis.models.AnnotationRequestModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.RDFS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.repository.RepositoryException;
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
            2,
            1,
            "@prefix dbpedia: <http://dbpedia.org/ontology/> .\n" +
                    "@prefix dbr: <http://dbpedia.org/page/> .\n" +
                    "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
                    "<http://dbpedia.org/resource/Nicholas_II_of_Russia>\n" +
                    "  dbpedia:birthPlace dbr:Saint_Petersburg ;\n" +
                    "  rdfs:label \"Nicholas II of Russia\" .");

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
        Model model = this.testAnnotation.getBodyModel();
        System.out.println(model.toString());
        Resource res = model.getResource("http://dbpedia.org/resource/Nicholas_II_of_Russia");
        assertNotNull(res);
        assertEquals(res.getProperty(RDFS.label).getObject().toString(), "Nicholas II of Russia");
    }

    @Test
    public void shouldGetAllStatements() throws RepositoryException, IllegalAccessException, InstantiationException {
        List<Statement> statementList = this.testAnnotation.getStatements();
        statementList.forEach(System.out::println);

        assertEquals(statementList.size(), 5);
    }
}
