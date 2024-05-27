package org.example.iec61850.modelXml;

import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.lodicalNodes.measurement.MMXU;
import org.example.iec61850.lodicalNodes.protection.PTOC;
import org.example.iec61850.lodicalNodes.supervisory_control.CSWI;
import org.example.iec61850.lodicalNodes.switchgear.XCBR;
import org.example.iec61850.lodicalNodes.system_logic_nodes.LSVS;

import java.util.List;
import java.util.Optional;

public class ParserXml {
    private String path;
    Optional<xmlConfig> parse;
    private List<LN> lns;

    public ParserXml(String path, List<LN> lns) throws Exception {
        this.path = path;
        this.lns = lns;
        /**
         * Считываем и парсим файл по заданному пути.
         */
        parse = XmlUtils.parse(this.path, xmlConfig.class);

//        lns.add(createLsvs());
//        lns.add(createMmxu());
//        lns.add(createCswi());
//        lns.add(createXcbr());
//
//        for (int i = 0; i < parse.get().getPtocList().get(0).getLogicNodePTOC().size(); i++) {
//            lns.add(createPtoc(i, (MMXU) lns.get(1)));
//        }

    }

    public PTOC createPtoc(int i, MMXU mmxu) {
        PTOC ptoc = new PTOC();
        ptoc.setA(mmxu.getA());
        ptoc.getStrVal().getSetMag().getFloatVal().setValue(parse.get().getPtocList().get(i).getLogicNodePTOC().get(i).getParametrList().get(i).getCurrent());
        ptoc.getOpDlOpTmms().getSetVal().setValue(parse.get().getPtocList().get(i).getLogicNodePTOC().get(i).getParametrList().get(i).getTimeDelay());
        ptoc.getTmMult().getSetMag().getFloatVal().setValue(parse.get().getPtocList().get(i).getLogicNodePTOC().get(i).getParametrList().get(i).getTimeMult());
        return ptoc;
    }

    public MMXU createMmxu() {
        MMXU mmxu = new MMXU();

        return mmxu;
    }

    public CSWI createCswi() {
        CSWI cswi = new CSWI();
        return cswi;
    }

    public XCBR createXcbr() {
        XCBR xcbr = new XCBR();
        return xcbr;
    }

    public LSVS createLsvs() throws Exception {
        LSVS lsvs = new LSVS();
        lsvs.setPath(parse.get().getLsvsList().get(0).getLogicNodeLSVS().get(0).getPath());
        lsvs.setFileName(parse.get().getLsvsList().get(0).getLogicNodeLSVS().get(0).getNamePath());
        return lsvs;
    }
}
