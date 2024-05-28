package org.example.iec61850.lodicalNodes.protection;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.node_parameters.DataObject.controls.INC;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.WYE;
import org.example.iec61850.node_parameters.DataObject.settings.ASG;
import org.example.iec61850.node_parameters.DataObject.settings.ING;
import org.example.iec61850.node_parameters.DataObject.status_information.ACT;

@Getter
@Setter
public class PHAR extends LN {
    public ACT Str = new ACT();
    private INC OpCntRs = new INC();
    private ING HaRst = new ING();
    public ASG PhStr = new ASG();
    private ASG PhStop = new ASG();
    private ING OpDlTmms = new ING();
    private ING RsDlTmms = new ING();

    public WYE I = new WYE();
    public WYE IHarmonic5 = new WYE();

    @Override
    public void process() {
        Str.getPhsA().setValue(
                IHarmonic5.getPhsA().getCVal().getMag().getFloatVal().getValue()
                        / I.getPhsA().getCVal().getMag().getFloatVal().getValue() > 0.2);
        Str.getPhsB().setValue(
                IHarmonic5.getPhsB().getCVal().getMag().getFloatVal().getValue()
                        / I.getPhsB().getCVal().getMag().getFloatVal().getValue() > 0.2);
        Str.getPhsC().setValue(
                IHarmonic5.getPhsC().getCVal().getMag().getFloatVal().getValue()
                        / I.getPhsC().getCVal().getMag().getFloatVal().getValue() > 0.2);
        Str.getGeneral().setValue(
                Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue());
    }
}
