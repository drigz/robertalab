package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class LightActionStatusTest {
    @Test
    public void ledOff() throws Exception {
        final String a = "\nSENSOR_TYPE_LIGHT_INACTIVE;";

        Helper.assertCodeIsOk(a, "/ast/actions/action_BrickLightStatus.xml");
    }

    @Test
    public void resetLED() throws Exception {
        final String a = "\nResetSensor(IN_);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_BrickLightStatus1.xml");
    }
}
