package org.example.iec61850.modelXml.modelCSWI;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class logicNodeCSWI {
    @XmlElement(name = "nameLogic")
    public String nameLogic;
}
