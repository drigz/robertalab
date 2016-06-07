package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathRoundTest {
    @Test
    public void Test() throws Exception {
        final String a = "mathFloor(0.5+0)1+mathFloor(0)mathFloor(0)";

        Helper.assertCodeIsOk(a, "/syntax/math/math_round.xml");
    }
}
