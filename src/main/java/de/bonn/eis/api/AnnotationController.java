package de.bonn.eis.api;

import de.bonn.eis.models.AnnotationRequestModel;
import de.bonn.eis.services.AnnotationService;
import de.bonn.eis.services.impl.AnnotationServiceBean;
import org.gazzax.labs.solrdf.client.UnableToBuildSolRDFClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by korovin on 3/18/2017.
 */
@RestController
public class AnnotationController {
    @Autowired
    private AnnotationService service;

    public AnnotationController()  {
        try {
            service = new AnnotationServiceBean();
        } catch (UnableToBuildSolRDFClientException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @RequestMapping(
            value = "/api/annotations",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnnotationRequestModel> createAnnotation(@RequestBody AnnotationRequestModel annotation) {
        AnnotationRequestModel savedAnnotation = service.create(annotation);
        return new ResponseEntity<>(savedAnnotation, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/annotations",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnnotationRequestModel> updateAnnotation(@RequestBody AnnotationRequestModel annotation) {
        AnnotationRequestModel savedAnnotation = service.update(annotation);

        if (savedAnnotation == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(savedAnnotation, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/annotations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnnotationRequestModel> removeAnnotation(@PathVariable String id) {
        boolean res = service.delete(id);

        if (!res) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
