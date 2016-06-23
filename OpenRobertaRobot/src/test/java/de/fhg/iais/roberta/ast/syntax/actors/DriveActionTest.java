package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class DriveActionTest {
    //
    public void drive() throws Exception {
        final String a = "OnFwdReg(OUT_CB,50,OUT_REGMODE_SYNC)";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorDiffOn.xml");
    }

    @Test
    public void driveFor() throws Exception {
        final String a = "\nRotateMotorEx(OUT_CB,50,18.0*20,0,true,true);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorDiffOnFor.xml");
    }
}