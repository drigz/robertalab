package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class TextAppendTest {
    @Test
    public void Test() throws Exception {
        final String a = "item+String(SENSOR_1)item+String(0)item+String(\"aaa\")";

        Helper.assertCodeIsOk(a, "/syntax/text/text_append.xml");
    }
}
