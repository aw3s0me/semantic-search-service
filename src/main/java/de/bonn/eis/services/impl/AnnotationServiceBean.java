package de.bonn.eis.services.impl;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.models.SemanticSearchResult;
import de.bonn.eis.services.AnnotationService;
import de.bonn.eis.services.impl.solr.IntegerElement;
import de.bonn.eis.services.impl.solr.Response;
import de.bonn.eis.services.impl.solr.ResponseHeader;
import de.bonn.eis.services.namespaces.NamespaceEnum;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.gazzax.labs.solrdf.client.SolRDF;
import org.gazzax.labs.solrdf.client.UnableToAddException;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.gazzax.labs.solrdf.client.UnableToExecuteQueryException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by korovin on 3/18/2017.
 */
@Service
public class AnnotationServiceBean implements AnnotationService {
    private final SolRDF solrClient;
    //private static final String SOLR_API_SPARQL = "http://127.0.0.1:8080/solr/store/sparql";
    private static final String SOLR_API_SPARQL = "http://127.0.0.1:8080/solr/store/sparql";


    public AnnotationServiceBean() throws RepositoryConfigException, RepositoryException, UnableToBuildSolRDFClientException {
        this.solrClient = SolRDF.newBuilder().build();
    }

    @Override
    public AnnotationRequestModel create(AnnotationRequestModel annotation) {
        try {
            List<Statement> statements = annotation.getStatements();
            this.solrClient.add(statements);
            return annotation;
        } catch (IllegalAccessException | RepositoryException | InstantiationException | UnableToAddException e) {
            return null;
        }
    }

    @Override
    public AnnotationRequestModel update(AnnotationRequestModel annotation) {
        return null;
    }

    @Override
    public SemanticSearchResult get(String id) {
        Resource mainAnno = AnnotationRequestModel.getMainAnnotationResource(id);
        String sparql = String.format("SELECT * WHERE {<%1$s> ?p ?o} LIMIT 10", mainAnno.getURI());

        try {
            ResultSet result = solrClient.select(sparql);
            Integer deckId = null;
            Integer slideId = null;

            while (result.hasNext()) {
                QuerySolution solution = result.nextSolution();

                Resource node = solution.getResource("?p");

                if (node.getURI().equals(NamespaceEnum.EX.getURI() + "slide")) {
                    slideId = solution.getLiteral("?o").getInt();
                } else if (node.getURI().equals(NamespaceEnum.EX.getURI() + "deck")) {
                    deckId = solution.getLiteral("?o").getInt();
                }

                // if deck id and slide id initialized, no need to iterate anymore
                if (deckId != null && slideId != null) {
                    return new SemanticSearchResult(deckId, slideId);
                }
            }
            // if could not find deck id and slide id
            return null;
        } catch (UnableToExecuteQueryException e) {
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        Resource mainAnno = AnnotationRequestModel.getMainAnnotationResource(id);
        String sparql = String.format("DELETE WHERE {<%1$s> ?p ?o}", mainAnno.getURI());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(new ArrayList<MediaType>() {{ add(MediaType.APPLICATION_JSON); }});
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("update", sparql);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(SOLR_API_SPARQL, request, String.class);
        System.out.println(responseEntity.getBody());
        try {
            Response parsedResponse = this.parseSolrResponse(responseEntity.getBody());
            ResponseHeader header = parsedResponse.getResponseHeader();
            return header.getStatus().getValue() == 0;
        } catch (JAXBException e) {
            return false;
        }
    }

    private Response parseSolrResponse(String solrResponse) throws JAXBException {
        JAXBContext jaxbContext =  JAXBContextFactory.createContext(new Class[] {ResponseHeader.class, Response.class, IntegerElement.class}, null);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(solrResponse);
        return (Response) unmarshaller.unmarshal(reader);
    }
}
