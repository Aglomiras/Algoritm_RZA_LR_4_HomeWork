package org.example.iec61850.lodicalNodes.protection;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.lodicalNodes.hmi.other.NHMIPoint;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.WYE;
import org.example.iec61850.node_parameters.DataObject.settings.ING;
import org.example.iec61850.node_parameters.DataObject.status_information.ACD;
import org.example.iec61850.node_parameters.DataObject.status_information.ACT;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PDIF extends LN {
    /**
     * LN: Differential Name: PDIF (LN: Дифференциальное имя: PDIF)
     * -----------------------------------------------------------------------------------------------------------------
     * Тормозная характеристика
     * -----------------------------------------------------------------------------------------------------------------
     * <p>
     * Смотрите IEC 61850-5 (LNS PDF, PDF, PDF, PDF, PDF, PDF и PDF). Этот LNS должен использоваться для
     * всех видов дифференциальной защиты по току. Необходимо предоставить соответствующие образцы
     * тока для специального применения.
     */
    /**
     * Status information
     */
    public WYE diffI = new WYE();
    public WYE tormI = new WYE();
    public ACT Op = new ACT();
    public ACD Str = new ACD();
    private ING LoSet = new ING();
    private ING HiSet = new ING();
    private ING MinOpTmms = new ING();
    private ING MaxOpTmms = new ING();
    public ACT Blok = new ACT();
    private ING RsDITmms = new ING();

    public double diff_min = 0.2;
    public double Kreturn = 0.9;
    public double Kt = 0.5;
    public double I_nom = 1000.0;
    public double OpDlTmms = 0;
    //Счетчики
    private int cntA = 0;
    private int cntB = 0;
    private int cntC = 0;
    public static double dt = 0.00025; // ms = 20/80

    public List<NHMIPoint<Double, Double>> pointsA = new ArrayList<>();
    public List<NHMIPoint<Double, Double>> pointsB = new ArrayList<>();
    public List<NHMIPoint<Double, Double>> pointsC = new ArrayList<>();

    @Override
    public void process() {
        pointsA.add(new NHMIPoint<>(diffI.getPhsA().getCVal().getMag().getFloatVal().getValue()
                / I_nom, -tormI.getPhsA().getCVal().getMag().getFloatVal().getValue() / I_nom));
        pointsB.add(new NHMIPoint<>(diffI.getPhsB().getCVal().getMag().getFloatVal().getValue()
                / I_nom, -tormI.getPhsB().getCVal().getMag().getFloatVal().getValue() / I_nom));
        pointsC.add(new NHMIPoint<>(diffI.getPhsC().getCVal().getMag().getFloatVal().getValue()
                / I_nom, -tormI.getPhsC().getCVal().getMag().getFloatVal().getValue() / I_nom));

        boolean strA = calcSr(diffI.getPhsA().getCVal().getMag().getFloatVal().getValue(),
                tormI.getPhsA().getCVal().getMag().getFloatVal().getValue());
        boolean strB = calcSr(diffI.getPhsB().getCVal().getMag().getFloatVal().getValue(),
                tormI.getPhsB().getCVal().getMag().getFloatVal().getValue());
        boolean strC = calcSr(diffI.getPhsC().getCVal().getMag().getFloatVal().getValue(),
                tormI.getPhsC().getCVal().getMag().getFloatVal().getValue());

        boolean strAv = calcVoz(diffI.getPhsA().getCVal().getMag().getFloatVal().getValue(),
                tormI.getPhsA().getCVal().getMag().getFloatVal().getValue());
        boolean strBv = calcVoz(diffI.getPhsB().getCVal().getMag().getFloatVal().getValue(),
                tormI.getPhsB().getCVal().getMag().getFloatVal().getValue());
        boolean strCv = calcVoz(diffI.getPhsC().getCVal().getMag().getFloatVal().getValue(),
                tormI.getPhsC().getCVal().getMag().getFloatVal().getValue());

        Str.getGeneral().setValue((strA || strB || strC) && !Blok.getGeneral().getValue());

        /**
         * Выдержка времени
         * */
        if (strA && !Blok.getGeneral().getValue()) {
            Str.getPhsA().setValue(strA);
            cntA++;
        } else if (strAv) {
            Str.getPhsA().setValue(strA);
            cntA = 0;
        }

        if (strB && !Blok.getGeneral().getValue()) {
            Str.getPhsB().setValue(strB);
            cntB++;
        } else if (strBv) {
            Str.getPhsB().setValue(strB);
            cntB = 0;
        }

        if (strC && !Blok.getGeneral().getValue()) {
            Str.getPhsC().setValue(strC);
            cntC++;
        } else if (strCv) {
            Str.getPhsC().setValue(strC);
            cntC = 0;
        }

        Op.getPhsA().setValue(cntA * dt > OpDlTmms);
        Op.getPhsB().setValue(cntB * dt > OpDlTmms);
        Op.getPhsC().setValue(cntC * dt > OpDlTmms);

        Op.getGeneral().setValue(Op.getPhsA().getValue() || Op.getPhsB().getValue() || Op.getPhsC().getValue());

    }

    private boolean calcSr(double diff_I, double torm_I) {
        return ((diff_I / I_nom >= diff_min) && (torm_I / I_nom <= diff_min / Kt) && (diff_I / I_nom >= Kt * torm_I / I_nom));
    }

    private boolean calcVoz(double diff_I, double torm_I) {
        return ((diff_I / I_nom < Kreturn * diff_min) && (diff_I / I_nom >= diff_min) && (diff_I / I_nom < (Kt * torm_I / I_nom - (1 - Kreturn) * diff_min)));
    }
}
