package org.example.iec61850.modelXml.modelXCBR;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class logicNodeXCBR {
    @XmlElement(name = "nameLogic")
    public String nameLogic;
}
