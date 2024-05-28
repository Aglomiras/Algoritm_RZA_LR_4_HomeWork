package org.example.iec61850.lodicalNodes.function_protection;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.node_parameters.DataObject.CMV;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.SAV;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.WYE;

@Getter
@Setter
public class RMXU extends LN {
    private WYE ALoc = new WYE();
    private SAV AmpLocPhsA = new SAV();
    private SAV AmpLocPhsB = new SAV();
    private SAV AmpLocPhsC = new SAV();
    private SAV AmpLocRes = new SAV();
    public WYE Ann = new WYE();
    public WYE Avn = new WYE();

    public WYE diffI = new WYE();
    public WYE tormI = new WYE();

    @Override
    public void process() {
        diffI.getPhsA().getCVal().getMag().getFloatVal().setValue(
                getDif(Avn.getPhsA(), Ann.getPhsA()));
        diffI.getPhsB().getCVal().getMag().getFloatVal().setValue(
                getDif(Avn.getPhsB(), Ann.getPhsB()));
        diffI.getPhsC().getCVal().getMag().getFloatVal().setValue(
                getDif(Avn.getPhsC(), Ann.getPhsC()));

        tormI.getPhsA().getCVal().getMag().getFloatVal().setValue(
                getRes(Avn.getPhsA(), Ann.getPhsA()));
        tormI.getPhsB().getCVal().getMag().getFloatVal().setValue(
                getRes(Avn.getPhsB(), Ann.getPhsB()));
        tormI.getPhsC().getCVal().getMag().getFloatVal().setValue(
                getRes(Avn.getPhsC(), Ann.getPhsC()));

    }

    private double getDif(CMV phaseVN, CMV phaseNN) {
        double ReVN = phaseNN.getCVal().getReal().getFloatVal().getValue()
                + phaseVN.getCVal().getReal().getFloatVal().getValue();
        double ImVN = phaseNN.getCVal().getImag().getFloatVal().getValue()
                + phaseVN.getCVal().getImag().getFloatVal().getValue();
        return Math.abs(Math.sqrt(ReVN * ReVN + ImVN * ImVN) / Math.sqrt(2));
    }

    private double getRes(CMV phaseVN, CMV phaseNN) {
        double ReVN = phaseVN.getCVal().getReal().getFloatVal().getValue();
        double ImVN = phaseVN.getCVal().getImag().getFloatVal().getValue();
        double ReNN = phaseNN.getCVal().getReal().getFloatVal().getValue();
        double ImNN = phaseNN.getCVal().getImag().getFloatVal().getValue();
        double summVN = Math.sqrt(ReVN * ReVN + ImVN * ImVN);
        double summNN = Math.sqrt(ReNN * ReNN + ImNN * ImNN);
        return ((summNN - summVN) / (2 * Math.sqrt(2)));
    }
}
