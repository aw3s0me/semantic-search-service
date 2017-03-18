package de.bonn.eis.api;

import de.bonn.eis.models.Annotation;
import de.bonn.eis.services.AnnotationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by korovin on 3/18/2017.
 */
@RestController
public class AnnotationController {
    private AnnotationService service;

    @RequestMapping(
            value = "/api/annotations",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Annotation> createAnnotation(@RequestBody Annotation annotation) {
        Annotation savedAnnotation = service.create(annotation);
        return new ResponseEntity<>(savedAnnotation, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/annotations",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Annotation> updateAnnotation(@RequestBody Annotation annotation) {
        Annotation savedAnnotation = service.update(annotation);

        if (savedAnnotation == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(savedAnnotation, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/annotations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Annotation> removeAnnotation(@PathVariable String id) {
        boolean res = service.delete(id);

        if (!res) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
