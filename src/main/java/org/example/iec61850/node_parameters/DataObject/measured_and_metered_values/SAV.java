package org.example.iec61850.node_parameters.DataObject.measured_and_metered_values;

import lombok.Getter;
import lombok.Setter;
import org.example.iec61850.common.datatypes.MyData;
import org.example.iec61850.common.modelData.*;

@Getter
@Setter
public class SAV extends MyData {
    /**
     * Sampled value (SAV)
     * */
    /**
     * Measured attributes
     * */
    private AnalogueValue instMag = new AnalogueValue();
    private Quality q = new Quality();
    private TimeStamp t = new TimeStamp();
    /**
     * Configuration, description and extension
     * */
    private Unit units = new Unit();
    private AnalogueValue min = new AnalogueValue();
    private AnalogueValue max = new AnalogueValue();
    private Attribute<String> d = new Attribute<>();
    private Attribute<Character.UnicodeBlock> dU = new Attribute<>();
    private Attribute<String> cdcNs = new Attribute<>();
    private Attribute<String> cdcName = new Attribute<>();
    private Attribute<String> dataNs = new Attribute<>();
}
