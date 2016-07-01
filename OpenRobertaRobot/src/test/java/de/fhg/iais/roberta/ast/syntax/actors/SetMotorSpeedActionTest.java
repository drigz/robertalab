package de.fhg.iais.roberta.ast.syntax.actors;

import de.fhg.iais.roberta.testutil.Helper;

public class SetMotorSpeedActionTest {

    //
    public void setMotorSpeed() throws Exception {
        final String a = "OnRevReg(OUT_B,-30,OUT_REGMODE_SPEED);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorSetPower.xml");
    }
}