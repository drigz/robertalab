package de.fhg.iais.roberta.ast.syntax.sensors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class InfraredSensorTest {
    @Test
    public void setInfrared() throws Exception {
        final String a = "\nSetSensorInfrared(IN_SensorPort.S4,DISTANCE);" + "SetSensorInfrared(IN_SensorPort.S3,SEEK);";

        Helper.assertCodeIsOk(a, "/ast/sensors/sensor_setInfrared.xml");
    }
}
