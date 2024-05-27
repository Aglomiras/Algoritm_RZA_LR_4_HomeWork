package org.example.iec61850.modelXml.modelPTOC;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class parametrs {
    @XmlElement(name = "current")
    private double current;
    @XmlElement(name = "timeDelay")
    private int timeDelay;
    @XmlElement(name = "timeMult")
    private double timeMult;
}
