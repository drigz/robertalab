package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathRoundTest {
    @Test
    public void Test() throws Exception {
        final String a = "math_round(0)math_round_up(0)math_floor(0)";

        Helper.assertCodeIsOk(a, "/syntax/math/math_round.xml");
    }
}
