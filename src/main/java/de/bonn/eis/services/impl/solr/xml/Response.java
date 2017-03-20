package de.bonn.eis.services.impl.solr.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by korovin on 3/20/2017.
 */
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {

    @XmlElement(name="lst")
    private ResponseHeader responseHeader;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Response() {}

    public Response(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }
}
