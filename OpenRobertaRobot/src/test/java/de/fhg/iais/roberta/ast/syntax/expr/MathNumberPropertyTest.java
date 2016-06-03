package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathNumberPropertyTest {
    @Test
    public void Test() throws Exception {
        String a = "(0%2==0)(0%2==1)mathPrime(0)(0%1==0)(0>0)(0<0)(0%0==0)";

        Helper.assertCodeIsOk(a, "/syntax/math/math_number_property.xml");
    }

    @Test
    public void Test1() throws Exception {
        String a = "item=(0%2==0);";

        Helper.assertCodeIsOk(a, "/syntax/math/math_number_property1.xml");
    }

}
