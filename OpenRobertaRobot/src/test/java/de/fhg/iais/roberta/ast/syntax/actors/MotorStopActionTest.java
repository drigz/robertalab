package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MotorStopActionTest {

    @Test
    public void stopMotor() throws Exception {
        String a = "\nOff(ActorPort.A, MotorStopMode.FLOAT);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorStop.xml");
    }
}