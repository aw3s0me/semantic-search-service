package de.bonn.eis.services.impl.solr.xml;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by korovin on 3/20/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class IntegerElement implements Serializable {

    @XmlTransient
    private static final long serialVersionUID = 1L;

    @XmlAttribute
    protected String name;

    @XmlValue
    private int value;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public IntegerElement(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public IntegerElement() {
    }
}
