package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathTrigTest {
    @Test
    public void Test() throws Exception {
        final String a = "0)0)0)0)0)0)";

        Helper.assertCodeIsOk(a, "/syntax/math/math_trig.xml");
    }

    @Test
    public void Test1() throws Exception {
        final String a = "if(0==0)){OnFwd(OUT_BC,0));}";

        Helper.assertCodeIsOk(a, "/syntax/math/math_trig1.xml");
    }

}
