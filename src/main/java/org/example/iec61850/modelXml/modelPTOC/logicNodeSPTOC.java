package org.example.iec61850.modelXml.modelPTOC;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class logicNodeSPTOC {
    @XmlElement(name = "logicNodePTOC")
    private List<logicNodePTOC> logicNodePTOC;
}
