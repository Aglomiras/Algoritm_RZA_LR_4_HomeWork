package org.example.iec61850.node_parameters.DataObject.measured_and_metered_values;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.common.datatypes.MyData;
import org.example.iec61850.common.modelData.Attribute;
import org.example.iec61850.node_parameters.DataObject.CMV;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HWYE extends MyData {
    private List<CMV> phsAHar = new ArrayList<>();
    private List<CMV> phsBHar = new ArrayList<>();
    private List<CMV> phsCHar = new ArrayList<>();
    private Attribute<Integer> numHar = new Attribute<>(10);

    public HWYE(int numHar) {
        this.numHar.setValue(numHar);
        for (int i = 0; i < numHar; i++) {
            phsAHar.add(new CMV());
            phsBHar.add(new CMV());
            phsCHar.add(new CMV());
        }
    }

    /**
     * Configuration, description and extension
     */
//    private Attribute<Integer> numHar = new Attribute<>();
    private Attribute<Integer> numCyc = new Attribute<>();
    private Attribute<Integer> evalTm = new Attribute<>();
    private Attribute<angRef> angRefAttribute = new Attribute<>();

    private enum angRef {
        VA,
        VB,
        VC,
        AA,
        AB,
        AC,
        VAB,
        VBC,
        VCA,
        VOTHER,
        AOTHER,
        SYNCHROPHASOR
    }

    private Attribute<Integer> smpRate = new Attribute<>();
    private Attribute<Float> frequency = new Attribute<>();
    private Attribute<hvRef> hvRefAttribute = new Attribute<>();

    private enum hvRef {
        FUNDAMENTAL,
        RMS,
        ABSOLUTE
    }

    private Attribute<Integer> rmsCyc = new Attribute<>();
    private Attribute<String> d = new Attribute<>();
    private Attribute<Character.UnicodeBlock> dU = new Attribute<>();
    private Attribute<String> cdcNs = new Attribute<>();
    private Attribute<String> cdcName = new Attribute<>();
    private Attribute<String> dataNs = new Attribute<>();
}
