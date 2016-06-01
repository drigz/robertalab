package de.fhg.iais.roberta.syntax.codegen.ev3;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.functions.FunctionNames;
import de.fhg.iais.roberta.testutil.Helper;

public class CustomFunctionsVisitorTest {

    @Test
    public void test0ok() throws Exception {
        ArrayList<ArrayList<Phrase<Void>>> phrases = Helper.generateASTs("/syntax/code_generator/java/BricCCPrimeTest.xml");

        Set<FunctionNames> customFunctionsVicitor = CustomFunctionsVisitor.check(phrases);
        Assert.assertEquals("[PRIME]", customFunctionsVicitor.toString());
    }
}
