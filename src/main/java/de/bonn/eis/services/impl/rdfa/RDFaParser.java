package de.bonn.eis.services.impl.rdfa;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import de.bonn.eis.services.namespaces.NamespaceEnum;
import org.semarglproject.jena.core.sink.JenaSink;
import org.semarglproject.rdf.ParseException;
import org.semarglproject.rdf.rdfa.RdfaParser;
import org.semarglproject.source.StreamProcessor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by korovin on 3/22/2017.
 */
public final class RDFaParser {
    public static Model parse(String rdfa) throws ParseException {
        Model model = ModelFactory.createDefaultModel();

        StreamProcessor streamProcessor = new StreamProcessor(RdfaParser.connect(JenaSink.connect(model)));
        InputStream stream = new ByteArrayInputStream(rdfa.getBytes(StandardCharsets.UTF_8));

        streamProcessor.process(stream, NamespaceEnum.DBO.getURI());
        return model;
    }
}
