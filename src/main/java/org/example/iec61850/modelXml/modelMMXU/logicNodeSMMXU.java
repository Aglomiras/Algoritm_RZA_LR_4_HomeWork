package org.example.iec61850.modelXml.modelMMXU;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class logicNodeSMMXU {
    @XmlElement(name = "logicNodeMMXU")
    private List<logicNodeMMXU> logicNodeMMXU;
}
