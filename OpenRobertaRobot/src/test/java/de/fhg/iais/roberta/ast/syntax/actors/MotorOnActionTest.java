package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MotorOnActionTest {

    @Test
    public void motorOn() throws Exception {
        String a = "RotateMotor(OUT_B, 30);" + "RotateMotor(OUT_C, 50);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorOn.xml");
    }

    @Test
    public void motorOnFor() throws Exception {
        String a = "RotateMotor(OUT_B, 30, 360.0* 1);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorOnFor.xml");
    }
}