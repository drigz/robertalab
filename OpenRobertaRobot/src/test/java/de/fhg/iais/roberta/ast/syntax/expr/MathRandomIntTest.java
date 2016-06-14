package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathRandomIntTest {
    @Test
    public void Test() throws Exception {
        final String a = "abs(1-100)*Random(100)/100+math_min(1,100)";

        Helper.assertCodeIsOk(a, "/syntax/math/math_random_int.xml");
    }
}
