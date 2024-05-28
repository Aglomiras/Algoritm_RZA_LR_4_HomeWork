package org.example.iec61850.lodicalNodes.protection;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.node_parameters.DataObject.controls.INC;
import org.example.iec61850.node_parameters.DataObject.settings.ENG;
import org.example.iec61850.node_parameters.DataObject.settings.ING;
import org.example.iec61850.node_parameters.DataObject.status_information.ACD;
import org.example.iec61850.node_parameters.DataObject.status_information.ACT;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PTRC extends LN {
    /**
     * LN: Protection trip conditioning Name: PTRC (LN: Система кондиционирования защитного отключения Название: PTRC)
     * */
    /**
     * Status information
     */
    private ACT Tr = new ACT();
    private ACT Op = new ACT();
    private ACD Str = new ACD();
    /**
     * Controls
     */
    private INC OpCntRs = new INC();
    /**
     * Settings
     */
    private ENG TrMod = new ENG();
    private ING TrPlsTmms = new ING();
    /**
     * input
     */
    public List<ACT> opList = new ArrayList<>();

    public PTRC() {
        OpCntRs.getStVal().setValue(0);
        Op.getGeneral().setValue(false);
    }

    @Override
    public void process() {
        if (OpCntRs.getStVal().getValue() != 0) return;
        for (ACT el : opList) {
            if (el.getGeneral().getValue()) {
                Op.getGeneral().setValue(true);
                OpCntRs.getStVal().setValue(OpCntRs.getStVal().getValue() + 1);
            }
        }
    }
}
