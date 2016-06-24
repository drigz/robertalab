package de.fhg.iais.roberta.ast.syntax.sensors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class ColorSensorTest {

    @Test
    public void setColor() throws Exception {
        final String a =
            "\nSensor(IN_)IN_TYPE_COLORCOLOUR);" + "Sensor(IN_)IN_TYPE_COLORRED);" + "Sensor(IN_)IN_TYPE_COLORRGB);" + "Sensor(IN_)IN_TYPE_COLORAMBIENT);";

        Helper.assertCodeIsOk(a, "/ast/sensors/sensor_setColor.xml");
    }
}
