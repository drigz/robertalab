package de.fhg.iais.roberta.ast.syntax.sensors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class GyroSensorTest {

    @Test
    public void setGyro() throws Exception {
        final String a = "\n SetSensorGyro(IN_SensorPort.S2,ANGLE);" + "SetSensorGyro(IN_SensorPort.S4,RATE);";

        Helper.assertCodeIsOk(a, "/ast/sensors/sensor_setGyro.xml");
    }

    @Test
    public void resetGyroSensor() throws Exception {
        final String a = "\n SetSensorGyro(IN_SensorPort.S2,RESET);";

        Helper.assertCodeIsOk(a, "/ast/sensors/sensor_resetGyro.xml");
    }
}
