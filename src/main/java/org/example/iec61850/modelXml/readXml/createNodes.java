package org.example.iec61850.modelXml.readXml;

import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.lodicalNodes.hmi.NHMI;
import org.example.iec61850.lodicalNodes.hmi.other.NHMISignal;
import org.example.iec61850.lodicalNodes.measurement.MMXU;
import org.example.iec61850.lodicalNodes.protection.PTOC;
import org.example.iec61850.lodicalNodes.supervisory_control.CSWI;
import org.example.iec61850.lodicalNodes.switchgear.XCBR;
import org.example.iec61850.lodicalNodes.system_logic_nodes.LSVS;
import org.example.iec61850.modelXml.ParserXml;

import java.util.ArrayList;
import java.util.List;

public class createNodes {
    private static final  List<LN> lns = new ArrayList<>();
    private String nameFile = "logicNodes";
    private static ParserXml parserXml;

    static {
        try {
            parserXml = new ParserXml("src/main/java/org/example/resources/" + "logicNodes" + ".xml", lns);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static MMXU mmxu;
    private static LSVS lsvs;
    private static PTOC ptoc1;
    private static PTOC ptoc2;
    private static PTOC ptoc3;
    private static CSWI cswi;
    private static XCBR xcbr;

    public createNodes() throws Exception {
//        parserXml =

    }

    public static void main(String[] args) throws Exception {
        mmxu = parserXml.createMmxu();
        lns.add(mmxu);
        lsvs = parserXml.createLsvs();
        lns.add(lsvs);

        mmxu.IaInst = lsvs.getOut().get(0);
        mmxu.IbInst = lsvs.getOut().get(1);
        mmxu.IcInst = lsvs.getOut().get(2);

        ptoc1 = parserXml.createPtoc(0, mmxu);
        lns.add(ptoc1);
        ptoc2 = parserXml.createPtoc(1, mmxu);
        lns.add(ptoc2);
        ptoc3 = parserXml.createPtoc(2, mmxu);
        lns.add(ptoc3);

        cswi = parserXml.createCswi();
        lns.add(cswi);
        xcbr = parserXml.createXcbr();
        lns.add(xcbr);

        NHMI nhmiMMXU = new NHMI();
        lns.add(nhmiMMXU);
        nhmiMMXU.addSignals("SignalIA", new NHMISignal("ia", mmxu.IaInst.getInstMag().getFloatVal()));
        nhmiMMXU.addSignals("SignalIB", new NHMISignal("ib", mmxu.IbInst.getInstMag().getFloatVal()));
        nhmiMMXU.addSignals("SignalIC", new NHMISignal("ic", mmxu.IcInst.getInstMag().getFloatVal()));

        NHMI nhmiPTOC = new NHMI();
        lns.add(nhmiPTOC);

        NHMI nhmiDS_A = new NHMI();
        lns.add(nhmiDS_A);

        while (lsvs.hasNext()) {
            lns.forEach(LN::process);
            System.out.println();
        }
    }

}
