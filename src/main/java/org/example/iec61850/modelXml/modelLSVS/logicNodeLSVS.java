package org.example.iec61850.modelXml.modelLSVS;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class logicNodeLSVS {
    @XmlElement(name = "nameLogic")
    public String nameLogic;
    @XmlElement(name = "path")
    private String path;
    @XmlElement(name = "namePath")
    private String namePath;
}
