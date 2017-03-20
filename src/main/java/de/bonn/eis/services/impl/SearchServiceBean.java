package de.bonn.eis.services.impl;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import de.bonn.eis.models.SemanticDeckRelevanceResult;
import de.bonn.eis.models.SemanticSearchRequest;
import de.bonn.eis.models.SemanticSearchResult;
import de.bonn.eis.services.SearchService;
import de.bonn.eis.services.namespaces.NamespaceEnum;
import org.apache.commons.lang3.StringUtils;
import org.gazzax.labs.solrdf.client.SolRDF;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.gazzax.labs.solrdf.client.UnableToExecuteQueryException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by korovin on 3/18/2017.
 */
@Service
public class SearchServiceBean implements SearchService {
    private final SolRDF solrClient;
    private final static String SPARQL_BEGIN = String.format("SELECT ?deck (count(?deck) as ?c) WHERE { ?annotation <%1$s> ?deck; ", NamespaceEnum.EX.getURI() + "deck");
    private final static String SPARQL_END = "} GROUP BY ?deck ORDER BY ?deck LIMIT 10";

    private String sparqlByType(String type) {
        return SPARQL_BEGIN + String.format(" a <%1$s> . ", type) + SPARQL_END;
    }

    private String sparqlByProperty(String property) {
        return SPARQL_BEGIN + String.format(" <%1$s> ?o . ", property) + SPARQL_END;
    }

    private String sparqlByTypeandProperty(String type, String property) {
        return SPARQL_BEGIN + String.format(" a <%1$s> ; ", type) + String.format(" <%1$s> ?o . ", property) + SPARQL_END;
    }

    public SearchServiceBean() throws UnableToBuildSolRDFClientException {
        this.solrClient = SolRDF.newBuilder().build();
    }

    @Override
    public Collection<SemanticSearchResult> searchByCriterias(Collection<Object> criterias) {
        return null;
    }

    @Override
    public Collection<SemanticDeckRelevanceResult> searchByTypeAndProperty(SemanticSearchRequest request) {
        String property = request.getProperty();
        String type = request.getType();
        boolean isTypeNotBlank = StringUtils.isNotBlank(type);
        boolean isPropertyNotBlank = StringUtils.isNotBlank(property);
        String finalSparql;
        List<SemanticDeckRelevanceResult> results = new ArrayList<>();

        if (isTypeNotBlank && isPropertyNotBlank) {
            finalSparql = this.sparqlByTypeandProperty(type, property);
        } else if (isTypeNotBlank) {
            finalSparql = this.sparqlByType(type);
        } else if (isPropertyNotBlank) {
            finalSparql = this.sparqlByProperty(property);
        } else {
            return null;
        }

        try {
            System.out.println(finalSparql);
            ResultSet result = solrClient.select(finalSparql);
            while (result.hasNext()) {
                QuerySolution solution = result.nextSolution();
                results.add(new SemanticDeckRelevanceResult(solution.getLiteral("?deck").getInt(), solution.getLiteral("?c").getInt()));
                System.out.println(solution.toString());
            }

            // TODO: add collection
            return results;
        } catch (UnableToExecuteQueryException e) {
            return null;
        }
    }

    public static void main(String[] args) throws UnableToBuildSolRDFClientException, UnableToExecuteQueryException {
        String sparql = "SELECT ?deck (count(?deck) as ?c) WHERE { ?annotation <http://example.org/deck> ?deck;  a <http://dbpedia.org/ontology/Event> . } GROUP BY ?deck ORDER BY ?deck LIMIT 10";
        SolRDF solrClient = SolRDF.newBuilder().build();
        ResultSet result = solrClient.select(sparql);
        while (result.hasNext()) {
            QuerySolution solution = result.nextSolution();
            System.out.println(solution.getLiteral("?deck").getInt());
            System.out.println(solution.getLiteral("?c").getInt());

            System.out.println(solution.toString());
        }
    }

    @Override
    public Collection<SemanticSearchResult> searchByKeywords(Collection<String> keywords) {
        return null;
    }
}
