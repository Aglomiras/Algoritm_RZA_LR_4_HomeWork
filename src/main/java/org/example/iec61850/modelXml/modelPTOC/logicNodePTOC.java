package org.example.iec61850.modelXml.modelPTOC;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class logicNodePTOC {
    @XmlElement(name = "nameLogic")
    private String nameLogic;
    @XmlElement(name = "parametrs")
    private List<parametrs> parametrList;
}
