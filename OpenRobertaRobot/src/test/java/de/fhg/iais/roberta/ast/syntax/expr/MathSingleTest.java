package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathSingleTest {
    @Test
    public void Test() throws Exception {
        final String a = "sqrt(0)abs(0)-0math_ln(0)math_log(0)math_pow(E,0)math_pow(10,0)";

        Helper.assertCodeIsOk(a, "/syntax/math/math_single.xml");
    }

    @Test
    public void Test1() throws Exception {
        final String a = "setVolume(sqrt(0));";

        Helper.assertCodeIsOk(a, "/syntax/math/math_single1.xml");
    }

    @Test
    public void Test2() throws Exception {
        final String a = "item=sqrt(0);";

        Helper.assertCodeIsOk(a, "/syntax/math/math_single2.xml");
    }
}
