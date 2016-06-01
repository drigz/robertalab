package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class SetMotorSpeedActionTest {

    @Test
    public void setMotorSpeed() throws Exception {
        final String a = "MotorPower(OUT_B,30);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorSetPower.xml");
    }
}