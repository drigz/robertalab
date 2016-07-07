package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class ListsIsEmptyTest {
    @Test
    public void Test() throws Exception {
        final String a = "IsEmpty({0,0,0})";

        Helper.assertCodeIsOk(a, "/syntax/lists/lists_is_empty.xml");
    }
}
