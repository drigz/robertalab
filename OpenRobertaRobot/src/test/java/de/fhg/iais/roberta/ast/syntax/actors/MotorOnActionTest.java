package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MotorOnActionTest {

    @Test
    public void motorOn() throws Exception {
        String a = "OnFwdReg(OUT_B, 30,100);" + "OnFwdReg(OUT_C, 50,100);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorOn.xml");
    }

    //
    public void motorOnFor() throws Exception {
        String a = "RotateMotorEx(OUT_B, -30, 360.0* 1,100,true,true);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorOnFor.xml");
    }
}