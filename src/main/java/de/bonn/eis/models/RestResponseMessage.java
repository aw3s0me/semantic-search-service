package de.bonn.eis.models;

import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by korovin on 3/22/2017.
 */
@XmlRootElement(name = "response")
public class RestResponseMessage {

    private String code;
    private String message;

    public RestResponseMessage() {}

    public RestResponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResponseMessage(HttpStatus status) {
        this.code = String.valueOf(status.value());
        this.message = status.getReasonPhrase();
    }

    public String getCode() {
        return code;
    }

    @XmlElement
    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    @XmlElement
    public void setMessage(String message) {
        this.message = message;
    }
}
