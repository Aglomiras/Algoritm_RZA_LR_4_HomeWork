package org.example;

import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.lodicalNodes.function_protection.RMXU;
import org.example.iec61850.lodicalNodes.hmi.NHMI;
import org.example.iec61850.lodicalNodes.hmi.other.NHMISignal;
import org.example.iec61850.lodicalNodes.measurement.*;
import org.example.iec61850.lodicalNodes.protection.*;
import org.example.iec61850.lodicalNodes.supervisory_control.CSWI;
import org.example.iec61850.lodicalNodes.system_logic_nodes.LSVC;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final List<LN> logicalNode = new ArrayList<>();
    private static String path = "C:\\Users\\Aglomiras\\Изображения\\Рабочий стол\\AlgoritmRZAProgrammRealize\\Лабораторная №4\\Opyty\\DPT\\Trans2Obm\\";
    private static String name = "Trans2ObmVkl";
//    private static String name = "Trans2ObmVneshABC";
//    private static String name = "Trans2ObmVnutA";
//    private static String name = "Trans2ObmVnutB";
//    private static String name = "Trans2ObmVnutBC";

    public static void main(String[] args) throws Exception {
        /**Создаем узел LSVC и добавляем узел в лист узлов*/
        LSVC lsvc = new LSVC();
        lsvc.setPath(path);
        lsvc.setFileName(name);
        logicalNode.add(lsvc);

        MMXU mmxu = new MMXU();
        logicalNode.add(mmxu);
        mmxu.iAvnInst = lsvc.getOut().get(0);
        mmxu.iBvnInst = lsvc.getOut().get(1);
        mmxu.iCvnInst = lsvc.getOut().get(2);
        mmxu.iAnnInst = lsvc.getOut().get(3);
        mmxu.iBnnInst = lsvc.getOut().get(4);
        mmxu.iCnnInst = lsvc.getOut().get(5);

        MHAI mhai = new MHAI();
        logicalNode.add(mhai);
        mhai.IAvnInst = lsvc.getOut().get(0);
        mhai.IBvnInst = lsvc.getOut().get(1);
        mhai.ICvnInst = lsvc.getOut().get(2);
        mhai.IAnnInst = lsvc.getOut().get(3);
        mhai.IBnnInst = lsvc.getOut().get(4);
        mhai.ICnnInst = lsvc.getOut().get(5);

        mhai.I = mmxu.Avn;

        RMXU rmxu = new RMXU();
        logicalNode.add(rmxu);

        rmxu.Ann = mmxu.Ann;
        rmxu.Avn = mmxu.Avn;

        PHAR phar = new PHAR();
        logicalNode.add(phar);

        phar.IHarmonic5 = mhai.IHar5;
        phar.I = mhai.I;

        PDIF pdif = new PDIF();
        logicalNode.add(pdif);

        pdif.Blok = phar.Str;
        pdif.diffI = rmxu.diffI;
        pdif.tormI = rmxu.tormI;

        PTRC ptrc = new PTRC();
        logicalNode.add(ptrc);
        ptrc.opList = List.of(pdif.Op);

        CSWI cswi = new CSWI();
        logicalNode.add(cswi);
        cswi.setOpSignal(ptrc.getOp());

        NHMI nhmiInstValues = new NHMI();
        logicalNode.add(nhmiInstValues);

        nhmiInstValues.addSignals(
                "Ток в фазe A ВН",
                new NHMISignal("InstVoltPhsA", lsvc.getOut().get(0).getInstMag().getFloatVal()));
        nhmiInstValues.addSignals(
                "Ток в фазe В ВН",
                new NHMISignal("InstVoltPhsB", lsvc.getOut().get(1).getInstMag().getFloatVal()));
        nhmiInstValues.addSignals(
                "Ток в фазe С ВН",
                new NHMISignal("InstVoltPhsC", lsvc.getOut().get(2).getInstMag().getFloatVal()));
        nhmiInstValues.addSignals(
                "Ток в фазe A НН",
                new NHMISignal("InstCurrPhsA", lsvc.getOut().get(3).getInstMag().getFloatVal()));
        nhmiInstValues.addSignals(
                "Ток в фазe B НН",
                new NHMISignal("InstCurrPhsB", lsvc.getOut().get(4).getInstMag().getFloatVal()));
        nhmiInstValues.addSignals(
                "Ток в фазe C НН",
                new NHMISignal("InstCurrPhsC", lsvc.getOut().get(5).getInstMag().getFloatVal()));
        nhmiInstValues.addSignals(
                "Частота",
                new NHMISignal("HZ", mmxu.Hz.getInstMag().getFloatVal()));


        NHMI nhmiAnalogSignals = new NHMI();
        logicalNode.add(nhmiAnalogSignals);

        nhmiAnalogSignals.addSignals(
                "Действующее и угл тока в фазe A ВН",
                new NHMISignal("I_A_VN", mmxu.Avn.getPhsA().getCVal().getMag().getFloatVal()),
                new NHMISignal("I_A_VN_Ang", mmxu.Avn.getPhsA().getCVal().getAng().getFloatVal()));
        nhmiAnalogSignals.addSignals(
                "Действующее и угл тока в фазe B ВН",
                new NHMISignal("I_B_VN", mmxu.Avn.getPhsB().getCVal().getMag().getFloatVal()),
                new NHMISignal("I_B_VN_Ang", mmxu.Avn.getPhsB().getCVal().getAng().getFloatVal()));
        nhmiAnalogSignals.addSignals(
                "Действующее и угл тока в фазe C ВН",
                new NHMISignal("I_C_VN", mmxu.Avn.getPhsC().getCVal().getMag().getFloatVal()),
                new NHMISignal("I_C_VN_Ang", mmxu.Avn.getPhsC().getCVal().getAng().getFloatVal()));
        nhmiAnalogSignals.addSignals(
                "Действующее и угл тока в фазe A НН",
                new NHMISignal("I_A_NN", mmxu.Ann.getPhsA().getCVal().getMag().getFloatVal()),
                new NHMISignal("I_A_NN_Ang", mmxu.Ann.getPhsA().getCVal().getAng().getFloatVal()));
        nhmiAnalogSignals.addSignals(
                "Действующее и угл тока в фазe B НН",
                new NHMISignal("I_B_NN", mmxu.Ann.getPhsB().getCVal().getMag().getFloatVal()),
                new NHMISignal("I_B_NN_Ang", mmxu.Ann.getPhsB().getCVal().getAng().getFloatVal()));
        nhmiAnalogSignals.addSignals(
                "Действующее и угл тока в фазe C НН",
                new NHMISignal("I_C_NN", mmxu.Ann.getPhsC().getCVal().getMag().getFloatVal()),
                new NHMISignal("I_C_NN_Ang", mmxu.Ann.getPhsC().getCVal().getAng().getFloatVal()));

        NHMI nhmiDifValues = new NHMI();
        logicalNode.add(nhmiDifValues);
        nhmiDifValues.addSignals(
                "Дифф. ток A",
                new NHMISignal("PhsAdif", rmxu.diffI.getPhsA().getCVal().getMag().getFloatVal()));
        nhmiDifValues.addSignals(
                "Дифф. ток B",
                new NHMISignal("PhsBdif", rmxu.diffI.getPhsB().getCVal().getMag().getFloatVal()));
        nhmiDifValues.addSignals(
                "Дифф. ток C",
                new NHMISignal("PhsCdif", rmxu.diffI.getPhsC().getCVal().getMag().getFloatVal()));
        nhmiDifValues.addSignals(
                "Ток торможения A",
                new NHMISignal("PhsAres", rmxu.tormI.getPhsA().getCVal().getMag().getFloatVal()));
        nhmiDifValues.addSignals(
                "Ток торможения B",
                new NHMISignal("PhsBres", rmxu.tormI.getPhsB().getCVal().getMag().getFloatVal()));
        nhmiDifValues.addSignals(
                "Ток торможения C",
                new NHMISignal("PhsCres", rmxu.tormI.getPhsC().getCVal().getMag().getFloatVal()));

        NHMI nhmiTrigger = new NHMI();
        logicalNode.add(nhmiTrigger);
        nhmiTrigger.addSignals(
                "Срабатывание защиты A",
                new NHMISignal("ReleyA", pdif.Str.getPhsA()) );
        nhmiTrigger.addSignals(
                "Срабатывание защиты B",
                new NHMISignal("ReleyB", pdif.Str.getPhsB()) );
        nhmiTrigger.addSignals(
                "Срабатывание защиты C",
                new NHMISignal("ReleyC", pdif.Str.getPhsC()) );
        nhmiTrigger.addSignals(
                "Сигнал на отключение",
                new NHMISignal("General", cswi.getOpSignal().getGeneral()));


        int i = 0; //Счетчик операций
        while (lsvc.hasNext()) {
            logicalNode.forEach(LN::process);
            System.out.println(i);
            i++;
        }
    }
}
