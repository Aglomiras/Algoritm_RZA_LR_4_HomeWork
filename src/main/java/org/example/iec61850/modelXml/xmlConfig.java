package org.example.iec61850.modelXml;

import lombok.Data;
import org.example.iec61850.modelXml.modelCSWI.logicNodeSCSWI;
import org.example.iec61850.modelXml.modelLSVS.logicNodeSLSVS;
import org.example.iec61850.modelXml.modelMMXU.logicNodeSMMXU;
import org.example.iec61850.modelXml.modelPTOC.logicNodeSPTOC;
import org.example.iec61850.modelXml.modelXCBR.logicNodeSXCBR;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class xmlConfig {
    @XmlElementWrapper(name = "LogicalNodes")
    @XmlElement(name = "logicNodeSPTOC")
    private List<logicNodeSPTOC> ptocList;

    @XmlElementWrapper(name = "LogicalNodes")
    @XmlElement(name = "logicNodeSMMXU")
    private List<logicNodeSMMXU> mmxuList;

    @XmlElementWrapper(name = "LogicalNodes")
    @XmlElement(name = "logicNodeSCSWI")
    private List<logicNodeSCSWI> cswiList;

    @XmlElementWrapper(name = "LogicalNodes")
    @XmlElement(name = "logicNodeSXCBR")
    private List<logicNodeSXCBR> xcbrList;

    @XmlElementWrapper(name = "LogicalNodes")
    @XmlElement(name = "logicNodeSLSVS")
    private List<logicNodeSLSVS> lsvsList;
}
