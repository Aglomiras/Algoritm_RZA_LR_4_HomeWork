package org.example.iec61850.modelXml.modelMMXU;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class logicNodeMMXU {
    @XmlElement(name = "nameLogic")
    public String nameLogic;
}
