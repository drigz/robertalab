package de.fhg.iais.roberta.syntax.codegen.ev3;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.fhg.iais.roberta.components.ev3.Ev3Configuration;
import de.fhg.iais.roberta.syntax.BlocklyBlockProperties;
import de.fhg.iais.roberta.syntax.expr.EmptyExpr;
import de.fhg.iais.roberta.syntax.expr.MathConst;
import de.fhg.iais.roberta.syntax.expr.MathConst.Const;
import de.fhg.iais.roberta.syntax.functions.FunctionNames;

public class JavaVisitorTest {
    private static final Ev3Configuration brickConfiguration = new Ev3Configuration.Builder().build();
    private static final Set<FunctionNames> usedCostumFunctions = new LinkedHashSet<FunctionNames>();

    @Test
    public void getIndentaion() throws Exception {
        Ast2Ev3JavaVisitor visitor = new Ast2Ev3JavaVisitor(brickConfiguration, 0);
        Assert.assertEquals(0, visitor.getIndentation());
    }

    // String code = AstToLejosJavaVisitor.generate("Test", brickConfiguration, transformer.getTree(), true);
    @Test
    public void getSb() throws Exception {
        Ast2Ev3JavaVisitor visitor = new Ast2Ev3JavaVisitor(brickConfiguration, 0);
        Assert.assertEquals("", visitor.getSb().toString());
    }

    //
    public void visitMathConst() throws Exception {
        MathConst<Void> mathConst = MathConst.make(Const.E, BlocklyBlockProperties.make("1", "1", false, false, false, false, false, true, false), null);
        Ast2Ev3JavaVisitor visitor = new Ast2Ev3JavaVisitor(brickConfiguration, 0);
        mathConst.visit(visitor);
        Assert.assertEquals("E", visitor.getSb().toString());
    }

    @Test
    public void visitEmptyExpr() throws Exception {
        EmptyExpr<Void> emptyExpr = EmptyExpr.make(Double.class);
        Ast2Ev3JavaVisitor visitor = new Ast2Ev3JavaVisitor(brickConfiguration, 0);
        emptyExpr.visit(visitor);
        Assert.assertEquals("[[EmptyExpr [defVal=class java.lang.Double]]]", visitor.getSb().toString());
    }
}
