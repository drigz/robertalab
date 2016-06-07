package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class LightActionTest {
    @Test
    public void ledOn() throws Exception {
        final String a = "\nSENSOR_TYPE_LIGHT_ACTIVE;SetSensorLight(IN_3,IN_TYPE_COLORGREEN);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_BrickLight.xml");
    }
}
