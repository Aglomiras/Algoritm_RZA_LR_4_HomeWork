package org.example.iec61850.lodicalNodes.measurement;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.Filter.FilterDiff;
import org.example.iec61850.Filter.FourierFilter;
import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.DEL;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.MV;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.WYE;

import static java.lang.Math.abs;

@Getter
@Setter
public class MMXU extends LN {
    /**
     * LN: Measurement Name: MMXU (LN: Название измерения: MMXU)
     *
     * Получать значения от CTs и VTs и вычислять измеряемые величины, такие как среднеквадратичные значения
     * тока и напряжения или потоки мощности из полученных выборок напряжения и тока. Эти значения обычно
     * используются для оперативных целей, таких как контроль и управление потоком мощности, отображение
     * на экране, оценка состояния и т.д. Должна быть обеспечена требуемая точность для этих функций.
     * (61850-5 - IEC: 2013)
     *
     * Функциональный класс LN: LN MMXU
     * Процедуры измерения в устройствах защиты являются частью специального алгоритма защиты,
     * представленного логическими узлами Xyz. Алгоритмы защиты, как и любая функция, выходят за рамки
     * стандарта связи. Поэтому LN Mxyz не должен использоваться в качестве входных данных для Pxyz.
     * Данные, связанные с неисправностью, такие как пиковое значение неисправности и т.д.,
     * всегда предоставляются строками типа Xyz, а не заимствованиями типа Xyz.
     */
    /**
     * Measured and metered values
     */
    private MV TotW = new MV();
    private MV TotVAr = new MV();
    private MV TotVA = new MV();
    private MV TotPF = new MV();
    public MV Hz = new MV();
    private DEL PPV = new DEL();
    public WYE PNV = new WYE();
    public WYE PhV = new WYE();

    public WYE Ann = new WYE();
    public WYE Avn = new WYE();

    public WYE A = new WYE();
    private WYE W = new WYE();
    private WYE VAr = new WYE();
    private WYE VA = new WYE();
    private WYE PF = new WYE();
    public WYE Z = new WYE();

    private double dt = 0.00025; // ms = 0.02/80
    private int cntTime = 0;
    private double[] previous = {0, 0};
    private double[] prevTime = {0, 0};
    private double[] zeroTime = {0, 0};
    boolean isFirst = true;


    /**
     * input
     */
    public MV iAnnInst = new MV();
    public MV iBnnInst = new MV();
    public MV iCnnInst = new MV();
    public MV iAvnInst = new MV();
    public MV iBvnInst = new MV();
    public MV iCvnInst = new MV();

    private int bufSize = 80;
    //ВН
    private final FilterDiff I_Avn = new FourierFilter(bufSize, 50.0);
    private final FilterDiff I_Bvn = new FourierFilter(bufSize, 50.0);
    private final FilterDiff I_Cvn = new FourierFilter(bufSize, 50.0);
    //НН
    private final FilterDiff I_Ann = new FourierFilter(bufSize, 50.0);
    private final FilterDiff I_Bnn = new FourierFilter(bufSize, 50.0);
    private final FilterDiff I_Cnn = new FourierFilter(bufSize, 50.0);

    @Override
    public void process() {
        freqMeasur(Hz);

        I_Ann.process(iAnnInst, Ann.getPhsA(), bufSize, Hz);
        I_Avn.process(iAvnInst, Avn.getPhsA(), bufSize, Hz);
        I_Bnn.process(iBnnInst, Ann.getPhsB(), bufSize, Hz);
        I_Bvn.process(iBvnInst, Avn.getPhsB(), bufSize, Hz);
        I_Cnn.process(iCnnInst, Ann.getPhsC(), bufSize, Hz);
        I_Cvn.process(iCvnInst, Avn.getPhsC(), bufSize, Hz);
    }

    private void freqMeasur(MV Hz) {
        cntTime++;
        previous[1] = previous[0];
        previous[0] = iAvnInst.getInstMag().getFloatVal().getValue();

        prevTime[1] = prevTime[0];
        prevTime[0] = cntTime * dt;

        if ((previous[0] * previous[1]) < 0) {
            double zeroT = prevTime[1] + (abs(previous[1]) * (prevTime[0] - prevTime[1])) / abs(previous[0] - previous[1]);
            zeroTime[1] = zeroTime[0];
            zeroTime[0] = zeroT;
            if (!isFirst) {
                Hz.getInstMag().getFloatVal().setValue(1 / (2 * (zeroTime[0] - zeroTime[1])));
                isFirst = true;
                System.out.println(prevTime[0] + "  " + Hz.getInstMag().getFloatVal().getValue());
                if (Math.abs(1 / Hz.getInstMag().getFloatVal().getValue() - bufSize * dt) > dt / 2) {
                    bufSize = (int) Math.abs(1 / (Hz.getInstMag().getFloatVal().getValue() * dt));
                }
            } else {
                isFirst = false;
            }
        }
    }
}
