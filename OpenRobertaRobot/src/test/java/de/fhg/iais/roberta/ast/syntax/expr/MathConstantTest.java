package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathConstantTest {
    @Test
    public void Test() throws Exception {
<<<<<<< df6b7826d4f4c545778eaa8807ae5cdd95191a69
        String a = "PI2.71828((1.0+sqrt(5.0))/2.0)sqrt(2)sqrt(1.0/2.0)0x7f800000";
        //"Float.POSITIVE_INFINITY";
=======
        final String a =
            "BlocklyMethods.PIBlocklyMethods.EBlocklyMethods.GOLDEN_RATIOBlocklyMethods.sqrt(2)BlocklyMethods.sqrt(1.0/2.0)Float.POSITIVE_INFINITY";
>>>>>>> #128 added nxc code for driveaction,sensors

        Helper.assertCodeIsOk(a, "/syntax/math/math_constant.xml");
    }

    @Test
    public void Test1() throws Exception {
<<<<<<< df6b7826d4f4c545778eaa8807ae5cdd95191a69
        String a = "hal.rotateRegulatedMotor(ActorPort.B,PI,MotorMoveMode.ROTATIONS,((1.0+sqrt(5.0))/2.0));";
=======
        final String a = "RotateMotor(B,BlocklyMethods.PI,360.0*BlocklyMethods.GOLDEN_RATIO))";
>>>>>>> #128 added nxc code for driveaction,sensors

        Helper.assertCodeIsOk(a, "/syntax/math/math_constant1.xml");
    }

}
