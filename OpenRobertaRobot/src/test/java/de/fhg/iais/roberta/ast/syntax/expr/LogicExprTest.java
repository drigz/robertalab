package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Ignore;
import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class LogicExprTest {

    @Test
    public void test1() throws Exception {
        final String a =
            "\nfalse == true\n"
                + "true != false\n"
                + "false == false\n"
                + "(5 <= 7)==(8 > 9)\n"
                + " (5 != 7)>=(8 == 9) \n"
                + "(5 + 7)>= ((8 + 4)/(9 + 3)) \n"
                + "((5 + 7)==(5 + 7 ))>= ((8 + 4 ) /( 9 + 3)) \n"
                + "((5 + 7)==(5 + 7) )>= (((5 + 7)== (5 + 7)) && ((5 + 7) <= (5 + 7)))\n"
                + "!((5 + 7)==(5 + 7) )== true";

        Helper.assertCodeIsOk(a, "/syntax/expr/logic_expr.xml");
    }

    @Test
    public void logicNegate() throws Exception {
        final String a = "\n!((0!= 0)&&false)";

        Helper.assertCodeIsOk(a, "/syntax/expr/logic_negate.xml");
    }

    @Test
    public void logicNull() throws Exception {
        final String a = "\nNULL";

        Helper.assertCodeIsOk(a, "/syntax/expr/logic_null.xml");
    }

    // The ternary was removed
    @Ignore
    public void logicTernary() throws Exception {
        final String a = "\n( 0 == 0 ) ? false : true";

        Helper.assertCodeIsOk(a, "/syntax/expr/logic_ternary.xml");
    }
}