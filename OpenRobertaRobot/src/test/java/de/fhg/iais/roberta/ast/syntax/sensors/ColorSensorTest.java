package de.fhg.iais.roberta.ast.syntax.sensors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class ColorSensorTest {

    @Test
    public void setColor() throws Exception {
        final String a =
            "\nSetSensor(IN_SensorPort.S3,COLOUR);"
                + "SetSensor(IN_SensorPort.S1,RED);"
                + "SetSensor(IN_SensorPort.S2,RGB);"
                + "SetSensor(IN_SensorPort.S4,AMBIENT);";

        Helper.assertCodeIsOk(a, "/ast/sensors/sensor_setColor.xml");
    }
}
