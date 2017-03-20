package de.bonn.eis.services.impl.solr;

import de.bonn.eis.services.impl.solr.xml.IntegerElement;
import de.bonn.eis.services.impl.solr.xml.Response;
import de.bonn.eis.services.impl.solr.xml.ResponseHeader;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by korovin on 3/20/2017.
 */
public final class SolrRequestHelper {
    private static final String SOLR_API_SPARQL = "http://127.0.0.1:8080/solr/store/sparql?commit=true";

    private static final Response parseSolrResponse(String solrResponse) throws JAXBException {
        JAXBContext jaxbContext =  JAXBContextFactory.createContext(new Class[] {ResponseHeader.class, Response.class, IntegerElement.class}, null);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(solrResponse);
        return (Response) unmarshaller.unmarshal(reader);
    }

    public static final ResponseEntity<String> postUpdate(String sparql) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(new ArrayList<MediaType>() {{ add(MediaType.APPLICATION_JSON); }});
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("update", sparql);
        map.add("commit", "true");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(SOLR_API_SPARQL, request, String.class);
    }

    public static final boolean isRequestSucc(ResponseEntity<String> response) throws JAXBException {
        Response response1 = SolrRequestHelper.parseSolrResponse(response.getBody());
        ResponseHeader header = response1.getResponseHeader();
        return header.getStatus().getValue() == 0;
    }
}
