package org.example.iec61850.Filter;

import org.example.iec61850.common.modelData.Attribute;
import org.example.iec61850.node_parameters.DataObject.CMV;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.MV;
import org.example.iec61850.node_parameters.DataObject.settings.ING;

public class FourierFilter extends FilterDiff {
    private ING bSize = new ING();
    private MV[] buffer;
    public Attribute<Integer> bCount = new Attribute<>();
    public Attribute<Double> rVal = new Attribute<>();
    public Attribute<Double> imVal = new Attribute<>();
    public Attribute<Double> freq = new Attribute<>();
    public Attribute<Double> dT = new Attribute<>();
    private double accurateCount;

    public FourierFilter(double bufferSize, double frHarm) {
        accurateCount = bufferSize;
        bSize.getSetVal().setValue((int) bufferSize);
        bCount.setValue(0);
        rVal.setValue(0D);
        imVal.setValue(0D);
        freq.setValue(frHarm);
        dT.setValue(0.02 / bSize.getSetVal().getValue());
        buffer = new MV[bSize.getSetVal().getValue()];

        for (int i = 0; i < bSize.getSetVal().getValue(); i++) {
            MV tempVal = new MV();
            tempVal.getInstMag().getFloatVal().setValue(0D);
            buffer[i] = tempVal;
        }
    }

    @Override
    public void process(MV measuredValue, CMV complexMeasurementValue, double bufferSize, MV frequency) {
//        this.bufferSize.setValue((int) (1 / (this.dt.getValue() * freq.getInstMag().getFloatVal().getValue())));

        if (((int) bufferSize) != bSize.getSetVal().getValue()) {
            accurateCount = bufferSize;
            rVal.setValue(0D);
            imVal.setValue(0D);
            bCount.setValue(0);
            bSize.getSetVal().setValue((int) bufferSize);
            freq.setValue(frequency.getInstMag().getFloatVal().getValue());
            dT.setValue(0.02 / bSize.getSetVal().getValue());

            buffer = new MV[bSize.getSetVal().getValue()];
            for (int i = 0; i < bSize.getSetVal().getValue(); i++) {
                MV tempVal = new MV();
                tempVal.getInstMag().getFloatVal().setValue(0D);
                buffer[i] = tempVal;
            }
        }

        if (accurateCount > 24) {
            System.out.println(bCount.getValue());
        }

        if (bCount.getValue() == bSize.getSetVal().getValue() - 1) {
            System.out.println(bCount.getValue() + "   " + accurateCount);
            rVal.setValue(rVal.getValue()
                    + ((measuredValue.getInstMag().getFloatVal().getValue())
                    - (buffer[bCount.getValue()].getInstMag().getFloatVal().getValue())) * (-bCount.getValue() + accurateCount)
                    * Math.sin(2 * Math.PI * freq.getValue() * bCount.getValue() * dT.getValue())
                    * 2 / bSize.getSetVal().getValue()
            );
            imVal.setValue(imVal.getValue()
                    + ((measuredValue.getInstMag().getFloatVal().getValue())
                    - (buffer[bCount.getValue()].getInstMag().getFloatVal().getValue())) * (-bCount.getValue() + accurateCount)
                    * Math.cos(2 * Math.PI * freq.getValue() * bCount.getValue() * dT.getValue())
                    * 2 / bSize.getSetVal().getValue()
            );
        } else {
            rVal.setValue(rVal.getValue()
                    + (measuredValue.getInstMag().getFloatVal().getValue()
                    - buffer[bCount.getValue()].getInstMag().getFloatVal().getValue())
                    * Math.sin(2 * Math.PI * freq.getValue() * bCount.getValue() * dT.getValue())
                    * 2 / bSize.getSetVal().getValue()
            );
            imVal.setValue(imVal.getValue()
                    + (measuredValue.getInstMag().getFloatVal().getValue()
                    - buffer[bCount.getValue()].getInstMag().getFloatVal().getValue())
                    * Math.cos(2 * Math.PI * freq.getValue() * bCount.getValue() * dT.getValue())
                    * 2 / bSize.getSetVal().getValue()
            );
        }

        complexMeasurementValue.getCVal().getReal().getFloatVal().setValue(rVal.getValue());
        complexMeasurementValue.getCVal().getImag().getFloatVal().setValue(imVal.getValue());

        complexMeasurementValue.getCVal().getMag().getFloatVal().setValue(
                0.7071068 * Math.sqrt(Math.pow(rVal.getValue(), 2) + Math.pow(imVal.getValue(), 2))
        );
        complexMeasurementValue.getCVal().getAng().getFloatVal().setValue(
                Math.atan(imVal.getValue() / rVal.getValue()) * 180 / Math.PI
        );


        buffer[bCount.getValue()].getInstMag().getFloatVal().setValue(measuredValue.getInstMag().getFloatVal().getValue());
        bCount.setValue(bCount.getValue() + 1);

        if (bCount.getValue() >= bSize.getSetVal().getValue()) {
            bCount.setValue(0);
        }
    }

//    @Override
//    public void process(MV measuredValue, CMV complexMeasurementValue, MV frequency) {
//
//    }
}
