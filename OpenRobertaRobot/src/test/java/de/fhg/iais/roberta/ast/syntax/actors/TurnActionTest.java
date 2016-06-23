package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class TurnActionTest {

    @Test
    public void turn() throws Exception {
        final String a = "\nOnFwdSync(OUT_BC,50);";

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorDiffTurn.xml");
    }

    @Test
    public void turnFor() throws Exception {
<<<<<<< HEAD
        final String a = "\nOnFwdSync(OUT_BC,50RotateMotorEx,20,100,true,true);";
=======
        final String a = "\nturn_right(50, 0.0028*20);";
>>>>>>> ee181ef3e089b35cdf5127869b826487f76daf1e

        Helper.assertCodeIsOk(a, "/ast/actions/action_MotorDiffTurnFor.xml");
    }
}