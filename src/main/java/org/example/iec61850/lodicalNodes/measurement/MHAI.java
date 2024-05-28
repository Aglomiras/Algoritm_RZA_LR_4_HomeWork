package org.example.iec61850.lodicalNodes.measurement;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.Filter.FourierHarmonic;
import org.example.iec61850.lodicalNodes.LN;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.MV;
import org.example.iec61850.node_parameters.DataObject.measured_and_metered_values.WYE;
import org.example.iec61850.node_parameters.DataObject.status_information.ACT;

@Getter
@Setter
public class MHAI extends LN {
    public MV IAvnInst = new MV();
    public MV IBvnInst = new MV();
    public MV ICvnInst = new MV();
    public MV IAnnInst = new MV();
    public MV IBnnInst = new MV();
    public MV ICnnInst = new MV();

    public WYE I = new WYE();
    public WYE IHar5 = new WYE();
    public ACT Blok = new ACT();

    private FourierHarmonic I_VN_A = new FourierHarmonic(5);
    private FourierHarmonic I_VN_B = new FourierHarmonic(5);
    private FourierHarmonic I_VN_C = new FourierHarmonic(5);

    @Override
    public void process() {
        I_VN_A.process(IAvnInst, IHar5.getPhsA());
        I_VN_B.process(IAvnInst, IHar5.getPhsB());
        I_VN_C.process(IAvnInst, IHar5.getPhsC());
    }
}
