package org.example.iec61850.modelXml.modelLSVS;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class logicNodeSLSVS {
    @XmlElement(name = "logicNodeLSVS")
    private List<logicNodeLSVS> logicNodeLSVS;
}
