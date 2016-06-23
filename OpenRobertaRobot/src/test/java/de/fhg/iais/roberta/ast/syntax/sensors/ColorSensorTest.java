package de.fhg.iais.roberta.ast.syntax.sensors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class ColorSensorTest {

    @Test
    public void setColor() throws Exception {
        final String a =
            "\nSetSensorLight(IN_3,IN_TYPE_COLORCOLOUR);"
                + "SetSensorLight(IN_3,IN_TYPE_COLORRED);"
                + "SetSensorLight(IN_3,IN_TYPE_COLORRGB);"
                + "SetSensorLight(IN_3,IN_TYPE_COLORAMBIENT);";

        Helper.assertCodeIsOk(a, "/ast/sensors/sensor_setColor.xml");
    }
}
