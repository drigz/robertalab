package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathTrigTest {
    @Test
    public void Test() throws Exception {
        String a = "sin(0)cos(0)tan(0)asin(0)acos(0)atan(0)";

        Helper.assertCodeIsOk(a, "/syntax/math/math_trig.xml");
    }

    //ignore
    public void Test1() throws Exception {
        String a = "if(0==sin(0)){OnFwd(OUT_BC,acos(0));}";

        Helper.assertCodeIsOk(a, "/syntax/math/math_trig1.xml");
    }

}
