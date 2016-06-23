package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathTrigTest {
    @Test
    public void Test() throws Exception {
        final String a = "MathSin(0)MathCos(0)MathTan(0)MathAsin(0)MathAcos(0)MathAtan(0)";

        Helper.assertCodeIsOk(a, "/syntax/math/math_trig.xml");
    }

    //
    public void Test1() throws Exception {
<<<<<<< HEAD
        final String a = "if(0==MathSin(0)){OnFwdReg(OUT_CB,MathAcos(0),OUT_REGMODE_SYNC);}";
=======
        final String a = "if(0==math_sin(0)){OnFwdReg(OUT_BC,math_acos(0),OUT_REGMODE_SYNC);}";
>>>>>>> nxtCode

        Helper.assertCodeIsOk(a, "/syntax/math/math_trig1.xml");
    }

}
