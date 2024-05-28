package org.example.iec61850.Filter;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.node_parameters.DataObject.CMV;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.MV;

@Getter
@Setter
public class FourierHarmonic extends Filter {
    private int size = 80;
    private double T = 0.02f;
    private double[] bufferX = new double[size];
    private double[] bufferY = new double[size];
    private double summValRe = 0;
    private double summValIm = 0;
    private int count = 0;
    private double Fx = 0;
    private double Fy = 0;
    private double F = 0;
    private double deg = 0;
    private int numGarm;
    public FourierHarmonic(int numGarm) {
        this.numGarm = numGarm;
    }
    public void process(MV measuredValue, CMV result) {
        summValRe += measuredValue.getInstMag().getFloatVal().getValue() *
                Math.sin(2 * Math.PI * 50 * numGarm * (T / size) * count) - bufferX[count];
        bufferX[count] = (measuredValue.getInstMag().getFloatVal().getValue() *
                Math.sin(2 * Math.PI * 50 * numGarm * (T / size) * count));
        Fx = 2 * summValRe / size;

        summValIm += measuredValue.getInstMag().getFloatVal().getValue() *
                Math.cos(2 * Math.PI * 50 * numGarm * (T / size) * count) - bufferY[count];
        bufferY[count] = (measuredValue.getInstMag().getFloatVal().getValue() *
                Math.cos(2 * Math.PI * 50 * numGarm * (T / size) * count));
        Fy = 2 * summValIm / size;
        F = ((Math.sqrt(Math.pow(Fx, 2) + Math.pow(Fy, 2))) / Math.sqrt(2));

        result.getCVal().getMag().getFloatVal().setValue(F);
        count++;
        if (count >= size) {
            count = 0;
        }
    }
}
