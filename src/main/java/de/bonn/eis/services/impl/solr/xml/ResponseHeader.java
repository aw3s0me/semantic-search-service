package de.bonn.eis.services.impl.solr.xml;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by korovin on 3/20/2017.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseHeader {

    @XmlPath("int[1]")
    private IntegerElement status;

    @XmlPath("int[2]")
    private IntegerElement QTime;

    public IntegerElement getStatus() {
        return status;
    }

    public IntegerElement getQTime() {
        return QTime;
    }

    public void setStatus(IntegerElement status) {
        this.status = status;
    }

    public void setQTime(IntegerElement QTime) {
        this.QTime = QTime;
    }

    public ResponseHeader(IntegerElement status, IntegerElement QTime) {
        this.status = status;
        this.QTime = QTime;
    }

    public ResponseHeader() {
    }
}
