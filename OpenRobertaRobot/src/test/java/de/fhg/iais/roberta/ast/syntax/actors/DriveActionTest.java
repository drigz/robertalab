package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class DriveActionTest {

    @Test
    public void drive() throws Exception {
        final String a = "OnFwd(OUT_BC, 50)";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorDiffOn.xml");
    }

    //Ignore
    public void driveFor() throws Exception {
        final String a = "\nhal.driveDistance(DriveDirection.FOREWARD, 50, 20);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorDiffOnFor.xml");
    }
}