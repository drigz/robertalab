package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class DriveActionTest {

    //ignore
    public void drive() throws Exception {
        final String a = "OnFwdReg(OUT_BC,50)";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorDiffOn.xml");
    }

    @Test
    public void driveFor() throws Exception {
        final String a = "\nOnFwdReg(OUT_BC,50,18.0*20);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorDiffOnFor.xml");
    }
}