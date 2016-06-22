package de.fhg.iais.roberta.syntax.codegen.ev3;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

import de.fhg.iais.roberta.components.Category;
import de.fhg.iais.roberta.components.ev3.EV3Sensor;
import de.fhg.iais.roberta.components.ev3.Ev3Configuration;
import de.fhg.iais.roberta.shared.IndexLocation;
import de.fhg.iais.roberta.shared.action.ev3.ActorPort;
import de.fhg.iais.roberta.shared.action.ev3.BlinkMode;
import de.fhg.iais.roberta.shared.action.ev3.BrickLedColor;
import de.fhg.iais.roberta.shared.action.ev3.DriveDirection;
import de.fhg.iais.roberta.shared.action.ev3.ShowPicture;
import de.fhg.iais.roberta.shared.action.ev3.TurnDirection;
import de.fhg.iais.roberta.shared.sensor.ev3.MotorTachoMode;
import de.fhg.iais.roberta.shared.sensor.ev3.SensorPort;
import de.fhg.iais.roberta.syntax.BlockType;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.action.Action;
import de.fhg.iais.roberta.syntax.action.ev3.BluetoothConnectAction;
import de.fhg.iais.roberta.syntax.action.ev3.BluetoothReceiveAction;
import de.fhg.iais.roberta.syntax.action.ev3.BluetoothSendAction;
import de.fhg.iais.roberta.syntax.action.ev3.BluetoothWaitForConnectionAction;
import de.fhg.iais.roberta.syntax.action.ev3.ClearDisplayAction;
import de.fhg.iais.roberta.syntax.action.ev3.DriveAction;
import de.fhg.iais.roberta.syntax.action.ev3.LightAction;
import de.fhg.iais.roberta.syntax.action.ev3.LightStatusAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorDriveStopAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorGetPowerAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorOnAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorSetPowerAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorStopAction;
import de.fhg.iais.roberta.syntax.action.ev3.PlayFileAction;
import de.fhg.iais.roberta.syntax.action.ev3.ShowPictureAction;
import de.fhg.iais.roberta.syntax.action.ev3.ShowTextAction;
import de.fhg.iais.roberta.syntax.action.ev3.ToneAction;
import de.fhg.iais.roberta.syntax.action.ev3.TurnAction;
import de.fhg.iais.roberta.syntax.action.ev3.VolumeAction;
import de.fhg.iais.roberta.syntax.blocksequence.ActivityTask;
import de.fhg.iais.roberta.syntax.blocksequence.Location;
import de.fhg.iais.roberta.syntax.blocksequence.MainTask;
import de.fhg.iais.roberta.syntax.blocksequence.StartActivityTask;
import de.fhg.iais.roberta.syntax.expr.ActionExpr;
import de.fhg.iais.roberta.syntax.expr.Binary;
import de.fhg.iais.roberta.syntax.expr.Binary.Op;
import de.fhg.iais.roberta.syntax.expr.BoolConst;
import de.fhg.iais.roberta.syntax.expr.ColorConst;
import de.fhg.iais.roberta.syntax.expr.EmptyExpr;
import de.fhg.iais.roberta.syntax.expr.EmptyList;
import de.fhg.iais.roberta.syntax.expr.Expr;
import de.fhg.iais.roberta.syntax.expr.ExprList;
import de.fhg.iais.roberta.syntax.expr.FunctionExpr;
import de.fhg.iais.roberta.syntax.expr.ListCreate;
import de.fhg.iais.roberta.syntax.expr.MathConst;
import de.fhg.iais.roberta.syntax.expr.MethodExpr;
import de.fhg.iais.roberta.syntax.expr.NullConst;
import de.fhg.iais.roberta.syntax.expr.NumConst;
import de.fhg.iais.roberta.syntax.expr.SensorExpr;
import de.fhg.iais.roberta.syntax.expr.ShadowExpr;
import de.fhg.iais.roberta.syntax.expr.StmtExpr;
import de.fhg.iais.roberta.syntax.expr.StringConst;
import de.fhg.iais.roberta.syntax.expr.Unary;
import de.fhg.iais.roberta.syntax.expr.Var;
import de.fhg.iais.roberta.syntax.expr.VarDeclaration;
import de.fhg.iais.roberta.syntax.functions.FunctionNames;
import de.fhg.iais.roberta.syntax.functions.GetSubFunct;
import de.fhg.iais.roberta.syntax.functions.IndexOfFunct;
import de.fhg.iais.roberta.syntax.functions.LengthOfIsEmptyFunct;
import de.fhg.iais.roberta.syntax.functions.ListGetIndex;
import de.fhg.iais.roberta.syntax.functions.ListRepeat;
import de.fhg.iais.roberta.syntax.functions.ListSetIndex;
import de.fhg.iais.roberta.syntax.functions.MathConstrainFunct;
import de.fhg.iais.roberta.syntax.functions.MathNumPropFunct;
import de.fhg.iais.roberta.syntax.functions.MathOnListFunct;
import de.fhg.iais.roberta.syntax.functions.MathPowerFunct;
import de.fhg.iais.roberta.syntax.functions.MathRandomFloatFunct;
import de.fhg.iais.roberta.syntax.functions.MathRandomIntFunct;
import de.fhg.iais.roberta.syntax.functions.MathSingleFunct;
import de.fhg.iais.roberta.syntax.functions.TextJoinFunct;
import de.fhg.iais.roberta.syntax.functions.TextPrintFunct;
import de.fhg.iais.roberta.syntax.methods.MethodCall;
import de.fhg.iais.roberta.syntax.methods.MethodIfReturn;
import de.fhg.iais.roberta.syntax.methods.MethodReturn;
import de.fhg.iais.roberta.syntax.methods.MethodVoid;
import de.fhg.iais.roberta.syntax.sensor.ev3.BrickSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.ColorSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.EncoderSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.GetSampleSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.GyroSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.InfraredSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.TimerSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.TouchSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.UltrasonicSensor;
import de.fhg.iais.roberta.syntax.stmt.ActionStmt;
import de.fhg.iais.roberta.syntax.stmt.AssignStmt;
import de.fhg.iais.roberta.syntax.stmt.ExprStmt;
import de.fhg.iais.roberta.syntax.stmt.FunctionStmt;
import de.fhg.iais.roberta.syntax.stmt.IfStmt;
import de.fhg.iais.roberta.syntax.stmt.MethodStmt;
import de.fhg.iais.roberta.syntax.stmt.RepeatStmt;
import de.fhg.iais.roberta.syntax.stmt.RepeatStmt.Mode;
import de.fhg.iais.roberta.syntax.stmt.SensorStmt;
import de.fhg.iais.roberta.syntax.stmt.Stmt;
import de.fhg.iais.roberta.syntax.stmt.StmtFlowCon;
import de.fhg.iais.roberta.syntax.stmt.StmtList;
import de.fhg.iais.roberta.syntax.stmt.WaitStmt;
import de.fhg.iais.roberta.syntax.stmt.WaitTimeStmt;
import de.fhg.iais.roberta.typecheck.BlocklyType;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.util.dbc.DbcException;
import de.fhg.iais.roberta.visitor.AstVisitor;

/**
 * This class is implementing {@link AstVisitor}. All methods are implemented and they
 * append a human-readable JAVA code representation of a phrase to a StringBuilder. <b>This representation is correct JAVA code.</b> <br>
 */
public class Ast2Ev3JavaVisitor implements AstVisitor<Void> {
    public static final String INDENT = "  ";

    private final Ev3Configuration brickConfiguration;
    private final String programName;
    private final StringBuilder sb = new StringBuilder();
    private final Set<FunctionNames> usedFunctions;
    private int indentation;

    private int x;

    private String left;

    private Expr<Void> TRUE;

    private Object FALSE;

    /**
     * initialize the Java code generator visitor.
     *
     * @param programName name of the program
     * @param brickConfiguration hardware configuration of the brick
     * @param usedFunctions in the current program
     * @param indentation to start with. Will be ince/decr depending on block structure
     */
    Ast2Ev3JavaVisitor(String programName, Ev3Configuration brickConfiguration, Set<FunctionNames> usedFunctions, int indentation) {
        this.programName = programName;
        this.brickConfiguration = brickConfiguration;
        this.indentation = indentation;
        this.usedFunctions = usedFunctions;
    }

    /**
     * factory method to generate Java code from an AST.<br>
     *
     * @param programName name of the program
     * @param brickConfiguration hardware configuration of the brick
     * @param phrases to generate the code from
     */
    public static String generate(String programName, Ev3Configuration brickConfiguration, ArrayList<ArrayList<Phrase<Void>>> phrasesSet, boolean withWrapping) //
    {
        Assert.notNull(programName);
        Assert.notNull(brickConfiguration);
        Assert.isTrue(phrasesSet.size() >= 1);

        final Set<FunctionNames> usedFunctions = CustomFunctionsVisitor.check(phrasesSet);
        final Ast2Ev3JavaVisitor astVisitor = new Ast2Ev3JavaVisitor(programName, brickConfiguration, usedFunctions, withWrapping ? 1 : 0);
        astVisitor.generatePrefix(withWrapping);

        generateCodeFromPhrases(phrasesSet, withWrapping, astVisitor);

        return astVisitor.sb.toString();
    }

    private static void generateCodeFromPhrases(ArrayList<ArrayList<Phrase<Void>>> phrasesSet, boolean withWrapping, Ast2Ev3JavaVisitor astVisitor) {
        boolean mainBlock = false;
        for ( final ArrayList<Phrase<Void>> phrases : phrasesSet ) {
            for ( final Phrase<Void> phrase : phrases ) {
                mainBlock = handleMainBlocks(astVisitor, mainBlock, phrase);
                phrase.visit(astVisitor);
            }

        }
        generateSuffix(withWrapping, astVisitor);
    }

    private static boolean handleMainBlocks(Ast2Ev3JavaVisitor astVisitor, boolean mainBlock, Phrase<Void> phrase) {
        if ( phrase.getKind().getCategory() != Category.TASK ) {
            astVisitor.nlIndent();
        } else if ( phrase.getKind() != BlockType.LOCATION ) {
            mainBlock = true;
        }
        return mainBlock;
    }

    private static void generateSuffix(boolean withWrapping, Ast2Ev3JavaVisitor astVisitor) {
        if ( withWrapping ) {
            astVisitor.sb.append("\n}\n");
        }
    }

    private static String getBlocklyTypeCode(BlocklyType type) {
        switch ( type ) {
            case ANY:
            case COMPARABLE:
            case ADDABLE:
            case NULL:
            case REF:
            case PRIM:
            case NOTHING:
            case CAPTURED_TYPE:
            case R:
            case S:
            case T:
                return "";
            case ARRAY:
                //return "List";
                // there is no just "list", only certain types of the arrays
                return "int";
            case ARRAY_NUMBER:
                return "float";
            case ARRAY_STRING:
                return "string";
            case ARRAY_BOOLEAN:
                return "bool";
            case BOOLEAN:
                return "bool";
            case NUMBER:
                return "float";
            case NUMBER_INT:
                return "int";
            case STRING:
                return "string";
            case VOID:
                return "void";
            //in nxt code examples this connection is given as a simple integer
            case CONNECTION:
                return "int";
        }
        throw new IllegalArgumentException("unhandled type");
    }

    private static String getEnumCode(@SuppressWarnings("rawtypes") Enum value) {
        return value.getClass().getSimpleName() + "." + value;
    }

    /**
     * Get the current indentation of the visitor. Meaningful for tests only.
     *
     * @return indentation value of the visitor.
     */
    int getIndentation() {
        return this.indentation;
    }

    /**
     * Get the string builder of the visitor. Meaningful for tests only.
     *
     * @return (current state of) the string builder
     */
    public StringBuilder getSb() {
        return this.sb;
    }

    //nxc can't cast "(float)", it does it automatically
    @Override
    public Void visitNumConst(NumConst<Void> numConst) {
        if ( isInteger(numConst.getValue()) ) {
            this.sb.append(numConst.getValue());
        } else {
            this.sb.append("(");
            this.sb.append(numConst.getValue());
            this.sb.append(")");
        }
        return null;
    }

    @Override
    public Void visitBoolConst(BoolConst<Void> boolConst) {
        this.sb.append(boolConst.isValue());
        return null;
    };

    @Override
    public Void visitMathConst(MathConst<Void> mathConst) {
        switch ( mathConst.getMathConst() ) {
            case PI:
                this.sb.append("PI");
                break;
            case E:
                this.sb.append("2.71828");
                break;
            case GOLDEN_RATIO:
                this.sb.append("((1.0 + sqrt(5.0)) / 2.0)");
                break;
            case SQRT2:
                this.sb.append("sqrt(2)");
                break;
            case SQRT1_2:
                this.sb.append("sqrt(1.0/2.0)");
                break;
            // IEEE 754 floating point representation
            case INFINITY:
                this.sb.append("0x7f800000");
                break;
            default:
                break;
        }
        return null;
    }

    //so far NXT has only light sensor, no colors
    @Override
    public Void visitColorConst(ColorConst<Void> colorConst) {
        this.sb.append(getEnumCode(colorConst.getValue()));
        return null;
    }

    @Override
    public Void visitStringConst(StringConst<Void> stringConst) {
        this.sb.append("\"").append(StringEscapeUtils.escapeJava(stringConst.getValue())).append("\"");
        return null;
    }

    @Override
    public Void visitNullConst(NullConst<Void> nullConst) {
        this.sb.append("null");
        return null;
    }

    @Override
    public Void visitVar(Var<Void> var) {
        this.sb.append(var.getValue());
        return null;
    }

    @Override
    public Void visitVarDeclaration(VarDeclaration<Void> var) {
        this.sb.append(getBlocklyTypeCode(var.getVariableType())).append(" ");
        this.sb.append(var.getName());
        if ( var.getVariableType().isArray() ) {
            this.sb.append("[]");
        }
        if ( var.getValue().getKind() != BlockType.EMPTY_EXPR ) {
            this.sb.append(" = ");
            if ( var.getValue().getKind() == BlockType.EXPR_LIST ) {
                final ExprList<Void> list = (ExprList<Void>) var.getValue();
                if ( list.get().size() == 2 ) {
                    list.get().get(1).visit(this);
                } else {
                    list.get().get(0).visit(this);
                }
            } else {
                var.getValue().visit(this);
            }
        }
        return null;
    }

    @Override
    public Void visitUnary(Unary<Void> unary) {
        if ( unary.getOp() == Unary.Op.POSTFIX_INCREMENTS ) {
            generateExprCode(unary, this.sb);
            this.sb.append(unary.getOp().getOpSymbol());
        } else {
            this.sb.append(unary.getOp().getOpSymbol());
            generateExprCode(unary, this.sb);
        }
        return null;
    }

    @Override
    public Void visitBinary(Binary<Void> binary) {
        if ( binary.getOp() == Op.EQ || binary.getOp() == Op.NEQ ) {
            if ( isStringExpr(binary.getLeft()) && isStringExpr(binary.getRight()) ) {
                if ( binary.getOp() == Op.NEQ ) {
                    this.sb.append("!");
                }
                generateSubExpr(this.sb, false, binary.getLeft(), binary);
                this.sb.append(".equals(");
                generateSubExpr(this.sb, false, binary.getRight(), binary);
                this.sb.append(")");
                return null;
            }
        }
        generateSubExpr(this.sb, false, binary.getLeft(), binary);
        this.sb.append(whitespace() + binary.getOp().getOpSymbol() + whitespace());
        if ( binary.getOp() == Op.TEXT_APPEND ) {
            this.sb.append("String(");
            generateSubExpr(this.sb, false, binary.getRight(), binary);
            this.sb.append(")");
        } else {
            generateSubExpr(this.sb, parenthesesCheck(binary), binary.getRight(), binary);
        }
        return null;
    }

    @Override
    public Void visitActionExpr(ActionExpr<Void> actionExpr) {
        actionExpr.getAction().visit(this);
        return null;
    }

    @Override
    public Void visitSensorExpr(SensorExpr<Void> sensorExpr) {
        sensorExpr.getSens().visit(this);
        return null;
    }

    @Override
    public Void visitMethodExpr(MethodExpr<Void> methodExpr) {
        methodExpr.getMethod().visit(this);
        return null;
    }

    @Override
    public Void visitEmptyExpr(EmptyExpr<Void> emptyExpr) {
        switch ( emptyExpr.getDefVal().getName() ) {
            case "java.lang.String":
                this.sb.append("\"\"");
                break;
            case "java.lang.Boolean":
                this.sb.append("true");
                break;
            case "java.lang.Integer":
                this.sb.append("0");
                break;
            case "java.util.ArrayList":
                break;
            case "de.fhg.iais.roberta.syntax.expr.NullConst":
                break;
            default:
                this.sb.append("[[EmptyExpr [defVal=" + emptyExpr.getDefVal() + "]]]");
                break;
        }
        return null;
    }

    @Override
    public Void visitShadowExpr(ShadowExpr<Void> shadowExpr) {
        if ( shadowExpr.getBlock() != null ) {
            shadowExpr.getBlock().visit(this);
        } else {
            shadowExpr.getShadow().visit(this);
        }
        return null;
    }

    @Override
    public Void visitExprList(ExprList<Void> exprList) {
        boolean first = true;
        for ( final Expr<Void> expr : exprList.get() ) {
            if ( expr.getKind() != BlockType.EMPTY_EXPR ) {
                if ( first ) {
                    first = false;
                } else {
                    this.sb.append(", ");
                }
                expr.visit(this);
            }
        }
        return null;
    }

    @Override
    public Void visitActionStmt(ActionStmt<Void> actionStmt) {
        actionStmt.getAction().visit(this);
        return null;
    }

    @Override
    public Void visitAssignStmt(AssignStmt<Void> assignStmt) {
        assignStmt.getName().visit(this);
        this.sb.append(" = ");
        assignStmt.getExpr().visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitExprStmt(ExprStmt<Void> exprStmt) {
        exprStmt.getExpr().visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitStmtExpr(StmtExpr<Void> stmtExpr) {
        stmtExpr.getStmt().visit(this);
        return null;
    }

    @Override
    public Void visitIfStmt(IfStmt<Void> ifStmt) {
        generateCodeFromIfElse(ifStmt);
        generateCodeFromElse(ifStmt);
        return null;
    }

    @Override
    public Void visitRepeatStmt(RepeatStmt<Void> repeatStmt) {
        //boolean additionalClosingBracket = false;
        switch ( repeatStmt.getMode() ) {
            case UNTIL:
            case WHILE:
            case FOREVER:
                generateCodeFromStmtCondition("while", repeatStmt.getExpr());
                break;
            case TIMES:
            case FOR:
                generateCodeFromStmtConditionFor("for", repeatStmt.getExpr());
                break;
            case WAIT:
                generateCodeFromStmtCondition("if", repeatStmt.getExpr());
                break;
            case FOR_EACH:
                generateCodeFromStmtCondition("for", repeatStmt.getExpr());
                break;
            default:
                break;
        }
        incrIndentation();
        repeatStmt.getList().visit(this);
        appendBreakStmt(repeatStmt);
        decrIndentation();
        nlIndent();
        this.sb.append("}");
        //if ( additionalClosingBracket ) {
        //    decrIndentation();
        //    nlIndent();
        //    this.sb.append("}");
        //}
        return null;
    }

    @Override
    public Void visitSensorStmt(SensorStmt<Void> sensorStmt) {
        sensorStmt.getSensor().visit(this);
        return null;
    }

    @Override
    public Void visitStmtFlowCon(StmtFlowCon<Void> stmtFlowCon) {
        this.sb.append(stmtFlowCon.getFlow().toString().toLowerCase() + ";");
        return null;
    }

    @Override
    public Void visitStmtList(StmtList<Void> stmtList) {
        for ( final Stmt<Void> stmt : stmtList.get() ) {
            nlIndent();
            stmt.visit(this);
        }
        return null;
    }

    @Override
    public Void visitWaitStmt(WaitStmt<Void> waitStmt) {
        this.sb.append("while ( true ) {");
        incrIndentation();
        visitStmtList(waitStmt.getStatements());
        nlIndent();
        this.sb.append("Wait(15);");
        decrIndentation();
        nlIndent();
        this.sb.append("}");
        return null;
    }

    @Override
    public Void visitWaitTimeStmt(WaitTimeStmt<Void> waitTimeStmt) {
        this.sb.append("Wait(");
        waitTimeStmt.getTime().visit(this);
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitClearDisplayAction(ClearDisplayAction<Void> clearDisplayAction) {
        this.sb.append("ClearScreen();");
        return null;
    }

    @Override
    public Void visitVolumeAction(VolumeAction<Void> volumeAction) {
        switch ( volumeAction.getMode() ) {
            case SET:
                this.sb.append("setVolume(");
                volumeAction.getVolume().visit(this);
                this.sb.append(");");
                break;
            case GET:
                this.sb.append("getVolume()");
                break;
            default:
                throw new DbcException("Invalid volume action mode!");
        }
        return null;
    }

    @Override
    public Void visitLightAction(LightAction<Void> lightAction) {
        if ( lightAction.getBlinkMode() == BlinkMode.ON ) {
            ;
        }

        {
            this.sb.append("SENSOR_TYPE_LIGHT_ACTIVE;");
        }
        if ( lightAction.getColor() == BrickLedColor.RED ) {
            this.sb.append("SetSensorLight(IN_3,IN_TYPE_COLORRED);");
        } else if ( lightAction.getColor() == BrickLedColor.ORANGE ) {
            this.sb.append("SetSensorLight(IN_3,IN_TYPE_COLORORANGE);");
        }

        if ( lightAction.getColor() == BrickLedColor.GREEN ) {
            this.sb.append("SetSensorLight(IN_3,IN_TYPE_COLORGREEN);");
            return null;
        }
        return null;

    }

    @Override
    public Void visitLightStatusAction(LightStatusAction<Void> lightStatusAction) {
        switch ( lightStatusAction.getStatus() ) {
            case OFF:
                this.sb.append("SENSOR_TYPE_LIGHT_INACTIVE;");
                break;

            case RESET:
                this.sb.append("ResetSensor(IN_);");
                break;
            default:
                throw new DbcException("Invalid LED status mode!");
        }
        return null;
    }

    @Override
    public Void visitPlayFileAction(PlayFileAction<Void> playFileAction) {
        this.sb.append("playFile(" + playFileAction.getFileName() + ");");
        return null;
    }

    @Override

    public Void visitShowPictureAction(ShowPictureAction<Void> showPictureAction) {
        this.sb.append("GraphicOut(");
        showPictureAction.getX().visit(this);
        this.sb.append(", ");
        showPictureAction.getY().visit(this);
        this.sb.append(",");
        if ( showPictureAction.getPicture() == ShowPicture.EYESOPEN ) {
            this.sb.append("\"" + "EYESOPEN" + "\"");
        } else {
            if ( showPictureAction.getPicture() == ShowPicture.EYESCLOSED ) {
                this.sb.append("\"" + "EYECLOSED" + "\"");
            }
            if ( showPictureAction.getPicture() == ShowPicture.FLOWERS ) {
                this.sb.append("\"" + "FLOWERS" + "\"");
            }

            if ( showPictureAction.getPicture() == ShowPicture.OLDGLASSES ) {
                this.sb.append("\"" + "OLDGLASSES" + "\"");
            }

        }

        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitShowTextAction(ShowTextAction<Void> showTextAction) {
        this.sb.append("TextOut(");
        showTextAction.getX().visit(this);
        this.sb.append(", LCD_LINE");
        showTextAction.getY().visit(this);
        this.sb.append(",");

        if ( showTextAction.getMsg().getKind() == BlockType.STRING_CONST ) {

            showTextAction.getMsg().visit(this);
        } else if ( showTextAction.getMsg().getKind() == BlockType.BOOL_CONST ) {
            showTextAction.equals(this.TRUE);
            this.sb.append("\"" + "true" + "\"");
            if ( showTextAction.equals(this.FALSE) ) {
                ;
            }

            this.sb.append("\"" + "false" + "\"");
        }

        if ( showTextAction.getMsg().getKind() == BlockType.NUM_CONST ) {
            this.sb.append("NumToStr(");

            showTextAction.getMsg().visit(this);
            this.sb.append(")");
        }
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitToneAction(ToneAction<Void> toneAction) {
        this.sb.append("PlayTone(");
        toneAction.getFrequency().visit(this);
        this.sb.append(", ");
        toneAction.getDuration().visit(this);
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitMotorOnAction(MotorOnAction<Void> motorOnAction) {
        {
            final boolean isDuration = motorOnAction.getParam().getDuration() != null;

            final String methodName = "RotateMotor";

            this.sb.append(methodName + "(OUT_" + motorOnAction.getPort());

            this.sb.append(", ");
            motorOnAction.getParam().getSpeed().visit(this);

            if ( isDuration ) {
                this.sb.append(", ");
                if ( motorOnAction.getParam().getDuration().getType() == de.fhg.iais.roberta.shared.action.ev3.MotorMoveMode.ROTATIONS ) {
                    this.sb.append("360.0*");
                }
                motorOnAction.getParam().getDuration().getValue().visit(this);

            }
            this.sb.append(");");
            return null;
        }
    }

    @Override
    public Void visitMotorSetPowerAction(MotorSetPowerAction<Void> motorSetPowerAction) {

        final String methodName = "on_reg";

        final boolean isRegulated = this.brickConfiguration.isMotorRegulated(motorSetPowerAction.getPort());
        this.sb.append(methodName + "(OUT_" + motorSetPowerAction.getPort() + ",");
        motorSetPowerAction.getPower().visit(this);

        this.sb.append(",OUT_REGMODE_SPEED");
        this.sb.append(");");
        return null;
    }

    private String getPower() {
        // TODO Auto-generated method stub
        return null;
    }

    private String getPort() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override //
    public Void visitMotorGetPowerAction(MotorGetPowerAction<Void> motorGetPowerAction) {
        final boolean isRegulated = this.brickConfiguration.isMotorRegulated(motorGetPowerAction.getPort());
        final String methodName = "MotorPower";
        this.sb.append(methodName + "(OUT_" + motorGetPowerAction.getPort());

        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitMotorStopAction(MotorStopAction<Void> motorStopAction) {
        final boolean StopAction = this.brickConfiguration.isMotorRegulated(motorStopAction.getPort());
        final String methodName = "Off";
        this.sb.append(methodName + "(OUT_");
        this.sb.append(this.brickConfiguration.getLeftMotorPort());
        this.sb.append(this.brickConfiguration.getRightMotorPort());
        this.sb.append(");");
        return null;
    }

    @Override // regulated drive action

    public Void visitDriveAction(DriveAction<Void> driveAction) {

        final boolean isDuration = driveAction.getParam().getDuration() != null;
        if ( isDuration ) {
            final String methodName = isDuration ? "driveDistance(" : "regulatedDrive(";
            this.sb.append("OnFwdReg");
        }
        if ( driveAction.getDirection() == DriveDirection.BACKWARD ) {
            this.sb.append("OnRevReg");
        }
        this.sb.append("(OUT_");
        this.sb.append(this.brickConfiguration.getLeftMotorPort());
        this.sb.append(this.brickConfiguration.getRightMotorPort());
        this.sb.append(",");
        driveAction.getParam().getSpeed().visit(this);
        this.sb.append(", OUT_REGMODE_SYNC");

        if ( isDuration == true ) {
            this.sb.append(",");
            if ( driveAction.getParam().getDuration().getType() == de.fhg.iais.roberta.shared.action.ev3.MotorMoveMode.DISTANCE ) {
                final String methodName = "RotateMotorEx";

                this.sb.append("18.0*"); // 18cm is one rotation //Synchronise two motors. Should be set to true if a non-zero turn percent is specified or no turning will occur
                driveAction.getParam().getDuration().getValue().visit(this);

                this.sb.append("0" + ",true" + ",true");

            }

            this.sb.append(");");
            return null;
        }

        this.sb.append(");");
        return null;
    }

    @Override

    public Void visitTurnAction(TurnAction<Void> turnAction) {

        String methodName = "OnFwdSync";
        final boolean isDuration = turnAction.getParam().getDuration() != null;
        if ( turnAction.getDirection() == TurnDirection.LEFT ) {
            methodName = "OnRevSync";
        }
        this.sb.append(methodName + "(OUT_");

        this.sb.append(this.brickConfiguration.getLeftMotorPort());
        this.sb.append(this.brickConfiguration.getRightMotorPort());
        this.sb.append(",");
        turnAction.getParam().getSpeed().visit(this);

        if ( isDuration ) {

            this.sb.append("RotateMotorEx");
            if ( turnAction.getParam().getDuration().getType() == de.fhg.iais.roberta.shared.action.ev3.MotorMoveMode.DEGREE ) {
                this.sb.append(",");
            }

            turnAction.getParam().getDuration().getValue().visit(this);
            this.sb.append(",100" + ",true" + ",true");
        }
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitMotorDriveStopAction(MotorDriveStopAction<Void> stopAction) {

        final String methodName = "Off";
        this.sb.append(methodName + "(OUT_");
        this.sb.append(this.brickConfiguration.getLeftMotorPort());
        this.sb.append(this.brickConfiguration.getRightMotorPort());
        this.sb.append(");");
        return null;
    }

    @Override // no bricksensor
    public Void visitBrickSensor(BrickSensor<Void> brickSensor) {
        switch ( brickSensor.getMode() ) {
            case IS_PRESSED:
                this.sb.append("hal.isPressed(" + getEnumCode(brickSensor.getKey()) + ")");
                break;
            case WAIT_FOR_PRESS_AND_RELEASE:
                this.sb.append("hal.isPressedAndReleased(" + getEnumCode(brickSensor.getKey()) + ")");
                break;
            default:
                throw new DbcException("Invalide mode for BrickSensor!");
        }
        return null;
    }

    @Override // TO DO: have to add nxc colours.
    public Void visitColorSensor(ColorSensor<Void> colorSensor) {
        final String Port = getEnumCode(colorSensor.getPort());

        final String methodName = "SetSensorLight";
        this.sb.append(methodName + "(IN_3");
        this.brickConfiguration.getSensors().entrySet();
        this.sb.append(",");
        switch ( colorSensor.getMode() ) {
            case AMBIENTLIGHT:
                this.sb.append("IN_TYPE_COLORAMBIENT");
                this.sb.append(")" + (";"));
                break;
            case COLOUR:
                this.sb.append("IN_TYPE_COLORCOLOUR");
                this.sb.append(")" + (";"));

                break;
            case RED:
                this.sb.append("IN_TYPE_COLORRED");
                this.sb.append(")" + (";"));

                break;
            case RGB:
                this.sb.append("IN_TYPE_COLORRGB");
                this.sb.append(")" + (";"));
                break;
            default:
                throw new DbcException("Invalide mode for Color Sensor!");
        }

        return null;
    }

    @Override
    public Void visitEncoderSensor(EncoderSensor<Void> encoderSensor) {
        final ActorPort encoderMotorPort = encoderSensor.getMotor();
        final boolean isRegulated = this.brickConfiguration.isMotorRegulated(encoderMotorPort);
        if ( encoderSensor.getMode() == MotorTachoMode.RESET ) {
            final String methodName = "ResetTachoCount";
            this.sb.append(methodName + "(OUT_" + encoderMotorPort + ");");

        } else {
            final String methodName = "MotorTachoCount";
            this.sb.append(methodName + "(OUT_" + encoderMotorPort + ");");
        }
        return null;
    }

    @Override // no gyrosensor
    public Void visitGyroSensor(GyroSensor<Void> gyroSensor) {
        final String Port = getEnumCode(gyroSensor.getPort());
        final String methodName = "SetSensorGyro";
        this.sb.append(methodName + "(IN_");

        switch ( gyroSensor.getMode() ) {
            case ANGLE:
                this.sb.append(Port + (",") + ("ANGLE"));
                this.sb.append(")" + (";"));
                break;
            case RATE:
                this.sb.append(Port + (",") + ("RATE"));
                this.sb.append(")" + (";"));
                break;
            case RESET:
                this.sb.append(Port + (",") + ("RESET"));
                this.sb.append(")" + (";"));
                break;
            default:
                throw new DbcException("Invalid GyroSensorMode");
        }
        return null;
    }

    @Override // no infrared sensor
    public Void visitInfraredSensor(InfraredSensor<Void> infraredSensor) {
        final String Port = getEnumCode(infraredSensor.getPort());
        final String methodName = "SetSensorInfrared";
        this.sb.append(methodName + "(IN_");
        switch ( infraredSensor.getMode() ) {

            case DISTANCE:
                this.sb.append(Port + (",") + ("DISTANCE"));
                this.sb.append(")" + (";"));
                break;
            case SEEK:
                this.sb.append(Port + (",") + ("SEEK"));
                this.sb.append(")" + (";"));
                break;
            default:
                throw new DbcException("Invalid Infrared Sensor Mode!");
        }
        return null;
    }

    @Override
    public Void visitTimerSensor(TimerSensor<Void> timerSensor) {
        switch ( timerSensor.getMode() ) {
            case GET_SAMPLE:
                this.sb.append("SetTimerValue(" + timerSensor.getTimer() + ")");
                break;
            case RESET:
                this.sb.append("resetTimer(" + timerSensor.getTimer() + ");");
                break;
            default:
                throw new DbcException("Invalid Time Mode!");
        }
        return null;
    }

    @Override
    public Void visitTouchSensor(TouchSensor<Void> touchSensor) {

        if ( touchSensor.getPort() == SensorPort.S1 ) {
            this.sb.append("," + "\"" + "1 pressed" + "\"");
        } else {
            if ( touchSensor.getPort() == SensorPort.S2 ) {
                ;
            }
            this.sb.append("," + "\"" + "2 pressed" + "\"");

            if ( touchSensor.getPort() == SensorPort.S3 ) {
                ;
            }
            this.sb.append("," + "\"" + "3 pressed" + "\"");
            if ( touchSensor.getPort() == SensorPort.S4 ) {
                ;
            }
            this.sb.append("," + "\"" + "2 pressed" + "\"");
        }
        return null;
    }

    @Override //to do: have to add ultrasonic mode
    public Void visitUltrasonicSensor(UltrasonicSensor<Void> ultrasonicSensor) {

        if ( ultrasonicSensor.getPort() == SensorPort.S4 ) {
            ;

            this.sb.append("SetSensorLowspeed(IN_4);");
        }

        {
            return null;
        }
    }

    @Override
    public Void visitMainTask(MainTask<Void> mainTask) {
        mainTask.getVariables().visit(this);
        //this.sb.append("\n\n").append(INDENT).append("public void run() throws Exception {\n");
        //incrIndentation();
        //if ( mainTask.getDebug().equals("TRUE") ) {
        //    this.sb.append(INDENT).append(INDENT).append("hal.startLogging();");
        //this.sb.append(INDENT).append(INDENT).append(INDENT).append("\nhal.startScreenLoggingThread();");
        //}
        return null;
    }

    @Override
    public Void visitActivityTask(ActivityTask<Void> activityTask) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitStartActivityTask(StartActivityTask<Void> startActivityTask) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitLocation(Location<Void> location) {
        return null;
    }

    @Override
    public Void visitGetSampleSensor(GetSampleSensor<Void> sensorGetSample) {
        return sensorGetSample.getSensor().visit(this);
    }

    //irrelevant?
    @Override
    public Void visitTextPrintFunct(TextPrintFunct<Void> textPrintFunct) {
        this.sb.append("System.out.println(");
        textPrintFunct.getParam().get(0).visit(this);
        this.sb.append(")");
        return null;
    }

    @Override
    public Void visitFunctionStmt(FunctionStmt<Void> functionStmt) {
        functionStmt.getFunction().visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitFunctionExpr(FunctionExpr<Void> functionExpr) {
        functionExpr.getFunction().visit(this);
        return null;
    }

    //TODO: LISTS

    @Override
    public Void visitGetSubFunct(GetSubFunct<Void> getSubFunct) {
        this.sb.append("BlocklyMethods.listsGetSubList( ");
        getSubFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        final IndexLocation where1 = IndexLocation.get(getSubFunct.getStrParam().get(0));
        this.sb.append(getEnumCode(where1));
        if ( where1 == IndexLocation.FROM_START || where1 == IndexLocation.FROM_END ) {
            this.sb.append(", ");
            getSubFunct.getParam().get(1).visit(this);
        }
        this.sb.append(", ");
        final IndexLocation where2 = IndexLocation.get(getSubFunct.getStrParam().get(1));
        this.sb.append(getEnumCode(where2));
        if ( where2 == IndexLocation.FROM_START || where2 == IndexLocation.FROM_END ) {
            this.sb.append(", ");
            if ( getSubFunct.getParam().size() == 3 ) {
                getSubFunct.getParam().get(2).visit(this);
            } else {
                getSubFunct.getParam().get(1).visit(this);
            }
        }
        this.sb.append(")");
        return null;

    }

    //TODO: perhaps look into the types
    @Override
    public Void visitIndexOfFunct(IndexOfFunct<Void> indexOfFunct) {
        final BlocklyType typeArr = indexOfFunct.getParam().get(0).getVariableType();
        if ( indexOfFunct.getLocation() == IndexLocation.LAST ) {
            switch ( typeArr ) {
                case ARRAY_NUMBER:
                    this.sb.append("array_find_last_num(");
                    indexOfFunct.getParam().get(0).visit(this);
                    this.sb.append(", ");
                    indexOfFunct.getParam().get(1).visit(this);
                    this.sb.append(")");
                    break;
                case ARRAY_STRING:
                    this.sb.append("array_find_last_str(");
                    indexOfFunct.getParam().get(0).visit(this);
                    this.sb.append(", ");
                    indexOfFunct.getParam().get(1).visit(this);
                    this.sb.append(")");
                    break;
                case ARRAY_BOOLEAN:
                    this.sb.append("array_find_last_bool(");
                    indexOfFunct.getParam().get(0).visit(this);
                    this.sb.append(", ");
                    indexOfFunct.getParam().get(1).visit(this);
                    this.sb.append(")");
                    break;
            }

        } else {
            switch ( typeArr ) {
                case ARRAY_NUMBER:
                    this.sb.append("array_find_first_num(");
                    indexOfFunct.getParam().get(0).visit(this);
                    this.sb.append(", ");
                    indexOfFunct.getParam().get(1).visit(this);
                    this.sb.append(")");
                    break;
                case ARRAY_STRING:
                    this.sb.append("array_find_first_str(");
                    indexOfFunct.getParam().get(0).visit(this);
                    this.sb.append(", ");
                    indexOfFunct.getParam().get(1).visit(this);
                    this.sb.append(")");
                    break;
                case ARRAY_BOOLEAN:
                    this.sb.append("array_find_first_bool(");
                    indexOfFunct.getParam().get(0).visit(this);
                    this.sb.append(", ");
                    indexOfFunct.getParam().get(1).visit(this);
                    this.sb.append(")");
                    break;
            }
        }
        return null;
    }

    @Override
    public Void visitLengthOfIsEmptyFunct(LengthOfIsEmptyFunct<Void> lengthOfIsEmptyFunct) {

        if ( lengthOfIsEmptyFunct.getFunctName() == FunctionNames.LIST_IS_EMPTY ) {
            this.sb.append("0");
        } else {
            this.sb.append("ArrayLen(");
            lengthOfIsEmptyFunct.getParam().get(0).visit(this);
            this.sb.append(")");
        }
        //String methodName = "BlocklyMethods.length( ";
        //if ( lengthOfIsEmptyFunct.getFunctName() == FunctionNames.LIST_IS_EMPTY ) {
        //    methodName = "BlocklyMethods.isEmpty( ";
        //}
        //this.sb.append(methodName);
        //lengthOfIsEmptyFunct.getParam().get(0).visit(this);
        //this.sb.append(")");
        return null;
    }

    @Override
    public Void visitEmptyList(EmptyList<Void> emptyList) {
        this.sb.append("{}");
        return null;
    }

    @Override
    public Void visitListCreate(ListCreate<Void> listCreate) {
        this.sb.append("{");
        listCreate.getValue().visit(this);
        this.sb.append("}");
        return null;
    }

    @Override
    public Void visitListRepeat(ListRepeat<Void> listRepeat) {
        this.sb.append("BlocklyMethods.createListWithItem(");
        listRepeat.getParam().get(0).visit(this);
        this.sb.append(", ");
        listRepeat.getParam().get(1).visit(this);
        this.sb.append(")");
        return null;
    }

    @Override
    public Void visitListGetIndex(ListGetIndex<Void> listGetIndex) {
        this.sb.append("BlocklyMethods.listsIndex(");
        listGetIndex.getParam().get(0).visit(this);
        this.sb.append(", ");
        this.sb.append(getEnumCode(listGetIndex.getElementOperation()));
        this.sb.append(", ");
        this.sb.append(getEnumCode(listGetIndex.getLocation()));
        if ( listGetIndex.getParam().size() == 2 ) {
            this.sb.append(", ");
            listGetIndex.getParam().get(1).visit(this);
        }
        this.sb.append(")");
        if ( listGetIndex.getElementOperation().isStatment() ) {
            this.sb.append(";");
        }
        return null;
    }

    @Override
    public Void visitListSetIndex(ListSetIndex<Void> listSetIndex) {
        this.sb.append("BlocklyMethods.listsIndex(");
        listSetIndex.getParam().get(0).visit(this);
        this.sb.append(", ");
        this.sb.append(getEnumCode(listSetIndex.getElementOperation()));
        this.sb.append(", ");
        listSetIndex.getParam().get(1).visit(this);
        this.sb.append(", ");
        this.sb.append(getEnumCode(listSetIndex.getLocation()));
        if ( listSetIndex.getParam().size() == 3 ) {
            this.sb.append(", ");
            listSetIndex.getParam().get(2).visit(this);
        }
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitMathConstrainFunct(MathConstrainFunct<Void> mathConstrainFunct) {
        this.sb.append("math_min(math_max(");
        mathConstrainFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        mathConstrainFunct.getParam().get(1).visit(this);
        this.sb.append("), ");
        mathConstrainFunct.getParam().get(2).visit(this);
        this.sb.append(")");
        return null;
    }

    @Override
    public Void visitMathNumPropFunct(MathNumPropFunct<Void> mathNumPropFunct) {
        switch ( mathNumPropFunct.getFunctName() ) {
            case EVEN:
                this.sb.append("(");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" % 2 == 0)");
                break;
            case ODD:
                this.sb.append("(");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" % 2 == 1)");
                break;
            case PRIME:
                this.sb.append("math_prime(");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(")");
                break;
            // % in nxc doesn't leave a a fractional residual, e.g. 5.2%1 = 0, so it is not possible to cheack the wholeness by "%1", that is why
            //an additional function is used
            case WHOLE:
                this.sb.append("math_is_whole(");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(")");
                break;
            case POSITIVE:
                this.sb.append("(");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" > 0)");
                break;
            case NEGATIVE:
                this.sb.append("(");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" < 0)");
                break;
            //it would work only for whole numbers, however, I think that it makes sense to talk about being divisible only for the whole numbers
            case DIVISIBLE_BY:
                this.sb.append("(");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" % ");
                mathNumPropFunct.getParam().get(1).visit(this);
                this.sb.append(" == 0)");
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public Void visitMathOnListFunct(MathOnListFunct<Void> mathOnListFunct) {
        switch ( mathOnListFunct.getFunctName() ) {
            case SUM:
                this.sb.append("array_sum(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case MIN:
                this.sb.append("array_min(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case MAX:
                this.sb.append("array_max(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case AVERAGE:
                this.sb.append("array_mean(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case MEDIAN:
                this.sb.append("array_median(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case STD_DEV:
                this.sb.append("array_standard_deviatioin(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case RANDOM:
                this.sb.append("array_rand(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case MODE:
                this.sb.append("array_mode(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            default:
                break;
        }
        this.sb.append(")");
        return null;
    }

    @Override
    public Void visitMathRandomFloatFunct(MathRandomFloatFunct<Void> mathRandomFloatFunct) {
        this.sb.append("Random(100) / 100");
        return null;
    }

    @Override
    public Void visitMathRandomIntFunct(MathRandomIntFunct<Void> mathRandomIntFunct) {
        this.sb.append("abs(");
        mathRandomIntFunct.getParam().get(0).visit(this);
        this.sb.append(" - ");
        mathRandomIntFunct.getParam().get(1).visit(this);
        this.sb.append(") * Random(100) / 100 + math_min(");
        mathRandomIntFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        mathRandomIntFunct.getParam().get(1).visit(this);
        this.sb.append(")");
        return null;
    }

    @Override
    public Void visitMathSingleFunct(MathSingleFunct<Void> mathSingleFunct) {
        switch ( mathSingleFunct.getFunctName() ) {
            case ROOT:
                this.sb.append("sqrt(");
                break;
            case ABS:
                this.sb.append("abs(");
                break;
            //Taylor Series converge only when value is less than one. Larger values are calculated
            //using a table.
            case LN:
                this.sb.append("math_ln(");
                break;
            case LOG10:
                this.sb.append("math_log(");
                break;
            case EXP:
                this.sb.append("math_pow(E, ");
                break;
            case POW10:
                this.sb.append("math_pow(10, ");
                break;
            //the 3 functions below accept degrees
            case SIN:
                this.sb.append("math_sin(");
                break;
            case COS:
                this.sb.append("math_cos(");
                break;
            case TAN:
                this.sb.append("math_tan(");
                break;
            case ASIN:
                this.sb.append("math_asin(");
                break;
            //Taylor Series converge only when value is less than one. Larger values are calculated
            //using a table.
            case ATAN:
                this.sb.append("math_atan(");
                break;
            case ACOS:
                this.sb.append("math_acos(");
                break;
            case ROUND:
                this.sb.append("math_floor(0.5 + ");
                break;
            case ROUNDUP:
                this.sb.append("1 + math_floor(");
                break;
            //check why there are double brackets
            case ROUNDDOWN:
                this.sb.append("math_floor(");
                break;
            default:
                break;
        }
        mathSingleFunct.getParam().get(0).visit(this);
        this.sb.append(")");

        return null;
    }

    @Override
    public Void visitMathPowerFunct(MathPowerFunct<Void> mathPowerFunct) {
        this.sb.append("math_pow(");
        mathPowerFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        mathPowerFunct.getParam().get(1).visit(this);
        this.sb.append(")");
        return null;
    }

    @Override
    public Void visitTextJoinFunct(TextJoinFunct<Void> textJoinFunct) {
        //Fix this method
        // using java methods just receive a string. So far it is not clear how to implement it in nxc directly
        // TBD: at leat how to deal with equations

        //smthToString(textJoinFunct.getParam());
        //this.sb.append(")");
        return null;
    }

    @Override
    public Void visitMethodVoid(MethodVoid<Void> methodVoid) {
        this.sb.append("\n").append(INDENT).append("void ");
        this.sb.append(methodVoid.getMethodName() + "(");
        methodVoid.getParameters().visit(this);
        this.sb.append(") {");
        methodVoid.getBody().visit(this);
        this.sb.append("\n").append(INDENT).append("}");
        return null;
    }

    @Override
    public Void visitMethodReturn(MethodReturn<Void> methodReturn) {
        this.sb.append("\n").append(INDENT).append(getBlocklyTypeCode(methodReturn.getReturnType()));
        this.sb.append(" " + methodReturn.getMethodName() + "(");
        methodReturn.getParameters().visit(this);
        this.sb.append(") {");
        methodReturn.getBody().visit(this);
        this.nlIndent();
        this.sb.append("return ");
        methodReturn.getReturnValue().visit(this);
        this.sb.append(";\n").append(INDENT).append("}");
        return null;
    }

    @Override
    public Void visitMethodIfReturn(MethodIfReturn<Void> methodIfReturn) {
        this.sb.append("if (");
        methodIfReturn.getCondition().visit(this);
        this.sb.append(") ");
        this.sb.append("return ");
        methodIfReturn.getReturnValue().visit(this);
        return null;
    }

    @Override
    public Void visitMethodStmt(MethodStmt<Void> methodStmt) {
        methodStmt.getMethod().visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitMethodCall(MethodCall<Void> methodCall) {
        this.sb.append(methodCall.getMethodName() + "(");
        methodCall.getParametersValues().visit(this);
        this.sb.append(")");
        if ( methodCall.getReturnType() == BlocklyType.VOID ) {
            this.sb.append(";");
        }
        return null;
    }

    // TODO: find out hoe to establish bluetooth connection using nxc
    @Override
    public Void visitBluetoothReceiveAction(BluetoothReceiveAction<Void> bluetoothReadAction) {
        this.sb.append("bluetooth_get_msg(");
        //bluetoothReadAction.getConnection().visit(this);
        this.sb.append(")");
        return null;
    }

    @Override
    public Void visitBluetoothConnectAction(BluetoothConnectAction<Void> bluetoothConnectAction) {
        this.sb.append("hal.establishConnectionTo(");
        if ( bluetoothConnectAction.get_address().getKind() != BlockType.STRING_CONST ) {
            this.sb.append("String.valueOf(");
            bluetoothConnectAction.get_address().visit(this);
            this.sb.append(")");
        } else {
            bluetoothConnectAction.get_address().visit(this);
        }
        this.sb.append(")");
        return null;
    }

    @Override
    public Void visitBluetoothSendAction(BluetoothSendAction<Void> bluetoothSendAction) {
        this.sb.append("bluetooth_send_msg(");
        if ( bluetoothSendAction.getMsg().getKind() != BlockType.STRING_CONST ) {
            String.valueOf(bluetoothSendAction.getMsg().visit(this));
        } else {
            bluetoothSendAction.getMsg().visit(this);
        }
        this.sb.append(", ");
        bluetoothSendAction.getConnection().visit(this);
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitBluetoothWaitForConnectionAction(BluetoothWaitForConnectionAction<Void> bluetoothWaitForConnection) {
        this.sb.append("int connection = ;");
        //get the numer
        this.sb.append("BTCheck(connection);");
        return null;
    }

    private void incrIndentation() {
        this.indentation += 1;
    }

    private void decrIndentation() {
        this.indentation -= 1;
    }

    private void indent() {
        if ( this.indentation <= 0 ) {
            return;
        } else {
            for ( int i = 0; i < this.indentation; i++ ) {
                this.sb.append(INDENT);
            }
        }
    }

    private void nlIndent() {
        this.sb.append("\n");
        indent();
    }

    private String whitespace() {
        return " ";
    }

    private boolean isStringExpr(Expr<Void> e) {
        switch ( e.getKind() ) {
            case STRING_CONST:
                return true;
            case VAR:
                return ((Var<?>) e).getVariableType() == BlocklyType.STRING;
            case FUNCTION_EXPR:
                final BlockType functionKind = ((FunctionExpr<?>) e).getFunction().getKind();
                return functionKind == BlockType.TEXT_JOIN_FUNCT || functionKind == BlockType.LIST_INDEX_OF;
            case METHOD_EXPR:
                final MethodCall<?> methodCall = (MethodCall<?>) ((MethodExpr<?>) e).getMethod();
                return methodCall.getKind() == BlockType.METHOD_CALL && methodCall.getReturnType() == BlocklyType.STRING;
            case ACTION_EXPR:
                final Action<?> action = ((ActionExpr<?>) e).getAction();
                return action.getKind() == BlockType.BLUETOOTH_RECEIVED_ACTION;

            default:
                return false;
        }
    }

    private boolean parenthesesCheck(Binary<Void> binary) {
        return binary.getOp() == Op.MINUS && binary.getRight().getKind() == BlockType.BINARY && binary.getRight().getPrecedence() <= binary.getPrecedence();
    }

    private void generateSubExpr(StringBuilder sb, boolean minusAdaption, Expr<Void> expr, Binary<Void> binary) {
        if ( expr.getPrecedence() >= binary.getPrecedence() && !minusAdaption && expr.getKind() != BlockType.BINARY ) {
            // parentheses are omitted
            expr.visit(this);
        } else {
            sb.append("(" + whitespace());
            expr.visit(this);
            sb.append(whitespace() + ")");
        }
    }

    private void generateExprCode(Unary<Void> unary, StringBuilder sb) {
        if ( unary.getExpr().getPrecedence() < unary.getPrecedence() ) {
            sb.append("(");
            unary.getExpr().visit(this);
            sb.append(")");
        } else {
            unary.getExpr().visit(this);
        }
    }

    /*private void generateCodeFromTernary(IfStmt<Void> ifStmt) {
        this.sb.append("(" + whitespace());
        ifStmt.getExpr().get(0).visit(this);
        this.sb.append(whitespace() + ")" + whitespace() + "?" + whitespace());
        ((ExprStmt<Void>) ifStmt.getThenList().get(0).get().get(0)).getExpr().visit(this);
        this.sb.append(whitespace() + ":" + whitespace());
        ((ExprStmt<Void>) ifStmt.getElseList().get().get(0)).getExpr().visit(this);
    }
    */

    private void generateCodeFromIfElse(IfStmt<Void> ifStmt) {
        for ( int i = 0; i < ifStmt.getExpr().size(); i++ ) {
            if ( i == 0 ) {
                generateCodeFromStmtCondition("if", ifStmt.getExpr().get(i));
            } else {
                generateCodeFromStmtCondition("else if", ifStmt.getExpr().get(i));
            }
            incrIndentation();
            ifStmt.getThenList().get(i).visit(this);
            decrIndentation();
            if ( i + 1 < ifStmt.getExpr().size() ) {
                nlIndent();
                this.sb.append("}").append(whitespace());
            }
        }
    }

    private void generateCodeFromElse(IfStmt<Void> ifStmt) {
        if ( ifStmt.getElseList().get().size() != 0 ) {
            nlIndent();
            this.sb.append("}").append(whitespace()).append("else").append(whitespace() + "{");
            incrIndentation();
            ifStmt.getElseList().visit(this);
            decrIndentation();
        }
        nlIndent();
        this.sb.append("}");
    }

    private void generateCodeFromStmtCondition(String stmtType, Expr<Void> expr) {
        this.sb.append(stmtType + whitespace() + "(" + whitespace());
        expr.visit(this);
        this.sb.append(whitespace() + ")" + whitespace() + "{");
    }

    private void generateCodeFromStmtConditionFor(String stmtType, Expr<Void> expr) {
        this.sb.append(stmtType + whitespace() + "(" + whitespace() + "float" + whitespace());
        final ExprList<Void> expressions = (ExprList<Void>) expr;
        expressions.get().get(0).visit(this);
        this.sb.append(whitespace() + "=" + whitespace());
        expressions.get().get(1).visit(this);
        this.sb.append(";" + whitespace());
        expressions.get().get(0).visit(this);
        this.sb.append(whitespace());
        this.sb.append("<" + whitespace());
        expressions.get().get(2).visit(this);
        this.sb.append(";" + whitespace());
        expressions.get().get(0).visit(this);
        this.sb.append(whitespace());
        this.sb.append("+=" + whitespace());
        expressions.get().get(3).visit(this);
        this.sb.append(whitespace() + ")" + whitespace() + "{");
    }

    private void appendBreakStmt(RepeatStmt<Void> repeatStmt) {
        if ( repeatStmt.getMode() == Mode.WAIT ) {
            nlIndent();
            this.sb.append("break;");
        }
    }

    /*private void addFunctions() {

        for ( final FunctionNames customFunction : this.usedFunctions ) {
            switch ( customFunction ) {
                case PRIME:
                    this.sb.append("inline bool mathPrime(float number){");
                    nlIndent();
                    this.sb.append("for ( int i = 2; i <= sqrt(number); i++ ) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("if ((number % i) == 0 ) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return false;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("else{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return true;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}  \n");
                    this.incrIndentation();
                    break;
                case CLAMP:
                    this.sb.append("inline float mathMin(float firstValue, float secondValue) {");
                    nlIndent();
                    this.sb.append("if (firstValue < secondValue){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return firstValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("else{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return secondValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("} \n");
                    this.incrIndentation();
    
                    //max of two values
                    this.sb.append("inline float mathMax(float firstValue, float secondValue) {");
                    nlIndent();
                    this.sb.append("if (firstValue > secondValue){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return firstValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("else{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return secondValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("} \n");
                    this.incrIndentation();
                    break;
                    case RINT:
                    this.sb.append("inline float mathMin(float firstValue, float secondValue) {");
                    nlIndent();
                    this.sb.append("if (firstValue < secondValue){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return firstValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("else{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return secondValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("} \n");
                    this.incrIndentation();
                    break;
                case ROUNDDOWN:
                    this.sb.append("inline int mathFloor(float val) {");
                    nlIndent();
                    this.sb.append("int temp = val;");
                    nlIndent();
                    this.sb.append("return temp; \n");
                    this.sb.append("} \n");
                    break;
                case ROUNDUP:
                    this.sb.append("inline int mathFloor(float val) {");
                    nlIndent();
                    this.sb.append("int temp = val;");
                    nlIndent();
                    this.sb.append("return temp; \n");
                    this.sb.append("} \n");
                case ROUND:
                    this.sb.append("inline int mathFloor(float val) {");
                    nlIndent();
                    this.sb.append("int temp = val;");
                    nlIndent();
                    this.sb.append("return temp; \n");
                    this.sb.append("} \n");
                    //won't work with fractional power
                case POW:
                    this.sb.append("inline float mathPow(float firstValue, float secondValue) {");
                    nlIndent();
                    this.sb.append("float result = 1;");
                    nlIndent();
                    this.sb.append("for (int i = 0; i < secondValue; i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("result = result * firstValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return result; \n");
                    this.sb.append("} \n");
                    break;
                case EXP:
                    this.sb.append("inline float mathPow(float firstValue, float secondValue) {");
                    nlIndent();
                    this.sb.append("float result = 1;");
                    nlIndent();
                    this.sb.append("for (int i = 0; i < secondValue; i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("result = result * firstValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return result; \n");
                    this.sb.append("} \n");
                    break;
                case POW10:
                    this.sb.append("inline float mathPow(float firstValue, float secondValue) {");
                    nlIndent();
                    this.sb.append("float result = 1;");
                    nlIndent();
                    this.sb.append("for (int i = 0; i < secondValue; i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("result = result * firstValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return result; \n");
                    this.sb.append("} \n");
                    break;
                case SUM:
                    this.sb.append("inline float arraySum(float arr[]) {");
                    nlIndent();
                    this.sb.append("float sum = 0;");
                    nlIndent();
                    this.sb.append("for(int i = 0; i < ArrayLen(arr); i++) {{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("sum += arr[i];");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return sum; \n");
                    this.sb.append("} \n");
                case MIN:
                    this.sb.append("inline float arrayMin(float arr[]) {");
                    nlIndent();
                    this.sb.append("float min = arr[0];");
                    nlIndent();
                    this.sb.append("for(int i = 1; i < ArrayLen(arr); i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("if (arr[i] < min){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("min = arr[i];");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return min;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}  \n");
                    this.incrIndentation();
                    break;
                case MAX:
                    this.sb.append("inline float arrayMax(float arr[]) {");
                    nlIndent();
                    this.sb.append("float max = arr[0];");
                    nlIndent();
                    this.sb.append("for(int i = 1; i < ArrayLen(arr); i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("if (arr[i] > max){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("max = arr[i];");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return max;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}  \n");
                    this.incrIndentation();
                    break;
                case AVERAGE:
                    this.sb.append("inline float arrayMean(float arr[]) {");
                    nlIndent();
                    this.sb.append("float sum = 0;");
                    nlIndent();
                    this.sb.append("for(int i = 0; i < ArrayLen(arr); i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("sum += arr[i];");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("sum/ArrayLen(arr); \n");
                    this.sb.append("} \n");
                case MEDIAN:
                    this.sb.append("inline void arrayInsertionSort(float &arr[]) {");
                    nlIndent();
                    this.sb.append("for (int i=1; i < ArrayLen(arr); i++){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("int index = arr[i];");
                    nlIndent();
                    this.sb.append("int j = i;");
                    nlIndent();
                    this.sb.append("while (j > 0 && arr[j-1] > index){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("arr[j] = arr[j-1];");
                    nlIndent();
                    this.sb.append("j--;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("arr[j] = index;");
                    nlIndent();
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}  \n");
                    this.incrIndentation();
    
                    this.sb.append("inline float arrayMedian(float arr[]) {");
                    nlIndent();
                    this.sb.append("int n = ArrayLen(arr);");
                    nlIndent();
                    this.sb.append("if ( n == 0 ) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return 0;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("arrayInsertionSort(arr);");
                    nlIndent();
                    this.sb.append("float median;");
                    nlIndent();
                    this.sb.append("if ( n % 2 == 0 ) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("median = (arr[n/2] + arr[n / 2 - 1]) / 2;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("else{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("median = arr[n / 2];");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return median;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("} \n");
                    this.incrIndentation();
                    break;
                case STD_DEV:
                    this.sb.append("inline float mathPow(float firstValue, float secondValue) {");
                    nlIndent();
                    this.sb.append("float result = 1;");
                    nlIndent();
                    this.sb.append("for (int i = 0; i < secondValue; i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("result = result * firstValue;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return result; \n");
                    this.sb.append("} \n");
    
                    this.sb.append("inline float arrayMean(float arr[]) {");
                    nlIndent();
                    this.sb.append("float sum = 0;");
                    nlIndent();
                    this.sb.append("for(int i = 0; i < ArrayLen(arr); i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("sum += arr[i];");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("sum/ArrayLen(arr); \n");
                    this.sb.append("} \n");
    
                    this.sb.append("inline float arrayStandardDeviatioin(float arr[]) {");
                    nlIndent();
                    this.sb.append("int n = ArrayLen(arr);");
                    nlIndent();
                    this.sb.append("if ( n == 0 ) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return 0;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("float variance = 0;");
                    nlIndent();
                    this.sb.append("float mean = arrayMean(arr);");
                    this.sb.append("for ( int i = 0; i < ArrayLen(arr); i++) {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("variance += mathPow(arr[i] - mean, 2);");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("variance /= n;");
                    nlIndent();
                    this.sb.append("return sqrt(variance)");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("} \n");
                    this.incrIndentation();
                    break;


                case WHOLE:
                    this.sb.append("inline bool isWhole(float val){");
                    nlIndent();
                    this.sb.append("int intPart = val;");
                    nlIndent();
                    this.sb.append("return ((val - intPart) == 0);  \n");
                    this.sb.append("} \n");
                    case RANDOM:
                    this.sb.append("inline float arrayRand(float arr[]) {");
                    nlIndent();
                    this.sb.append("int arrayInd = ArrayLen(arr) * Random(100) / 100;");
                    nlIndent();
                    this.sb.append("return arr[arrayInd - 1];  \n");
                    this.sb.append("} \n");
                    case MODE:
                    //mode search is much easier for a sorted array
                    this.sb.append("inline void arrayInsertionSort(float &arr[]) {");
                    nlIndent();
                    this.sb.append("for (int i=1; i < ArrayLen(arr); i++){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("int index = arr[i];");
                    nlIndent();
                    this.sb.append("int j = i;");
                    nlIndent();
                    this.sb.append("while (j > 0 && arr[j-1] > index){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("arr[j] = arr[j-1];");
                    nlIndent();
                    this.sb.append("j--;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("arr[j] = index;");
                    nlIndent();
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}  \n");
                    this.incrIndentation();
    
                    this.sb.append("inline float arrayMode(float arr[]){");
                    nlIndent();
                    this.sb.append("arrayInsertionSort(arr);");
                    nlIndent();
                    this.sb.append("float element = arr[0];");
                    nlIndent();
                    this.sb.append("float maxSeen = element;");
                    nlIndent();
                    this.sb.append("int count = 1;");
                    nlIndent();
                    this.sb.append("int modeCount = 1;");
                    nlIndent();
                    this.sb.append("for (int i = 1; i < ArrayLen(arr); i++){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("if (arr[i] == element){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("count++;");
                    nlIndent();
                    this.sb.append("if (count > modeCount){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("modeCount = count;");
                    nlIndent();
                    this.sb.append("maxSeen = element;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.sb.append("else{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("element = arr[i];");
                    nlIndent();
                    this.sb.append("count = 1;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return maxSeen;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}  \n");
                    this.incrIndentation();
                    break;
    
                    case FIRSTARR:
                    this.sb.append("inline int arrayFindFirst( item) {");
                    this.sb.append(this.arrType);
                    this.sb.append(" arr[], ");
                    this.sb.append(this.varType);
                    this.sb.append(" item) {");
                    nlIndent();
                    this.sb.append("int i = 0;");
                    nlIndent();
                    this.sb.append("if (arr[0] == item){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return i;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("else{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("do{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("i++;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("} while((arr[i] != item) && (i != ArrayLen(arr)));");
                    nlIndent();
                    this.sb.append("return i;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("} \n");
                    this.incrIndentation();
    
                    //TODO: may be put into another function
                    this.sb.append("inline int arrayFindLast( item) {");
                    this.sb.append(this.arrType);
                    this.sb.append(" arr[], ");
                    this.sb.append(this.varType);
                    this.sb.append(" item) {");
                    nlIndent();
                    this.sb.append("int i = 0;");
                    nlIndent();
                    this.sb.append("if (arr[ArrayLen(arr) - 1] == item){");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("return ArrayLen(arr) - 1 - i;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("else{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("do{");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("i++;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("while((arr[ArrayLen(arr) - 1 - i] != item)&&(i != 0));");
                    nlIndent();
                    this.sb.append("return ArrayLen(arr) - 1 - i;");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("} \n");
                    this.incrIndentation();
                    break;
    
    
                    case JTEXT:
                    this.sb.append("inline string textJoin (string arr[]) {");
                    nlIndent();
                    this.sb.append("string temp;");
                    nlIndent();
                    this.sb.append("for (int i=0; i < ArrayLen(arr); i++)  {");
                    this.incrIndentation();
                    nlIndent();
                    this.sb.append("temp = temp + arr[i];");
                    this.decrIndentation();
                    nlIndent();
                    this.sb.append("}");
                    nlIndent();
                    this.sb.append("return temp; \n");
                    this.sb.append("}\n");
                    break;

            }
        }
    }*/

    private void addConstants() {

        this.sb.append("#define WHEELDIAMETER " + this.brickConfiguration.getWheelDiameterCM() + "\n");
        this.sb.append("#define TRACKWIDTH " + this.brickConfiguration.getTrackWidthCM() + "\n");
        this.sb.append("#include \"hal.h\" \n");
        this.sb.append("#include \"NXCDefs.h\" \n");
        //TODO: change it to remove custom function visitor
        //  added constants for turnaction
        //  for ( final FunctionNames customFunction : this.usedFunctions ) {

        //    switch ( customFunction ) {
        //      case TURN_LEFT:
        // this.sb.append("#define turn_left(s,t)OnRev(OUT_A, s);OnFwd(OUT_B, s);\n");

        //       case TURN_RIGHT:
        // this.sb.append("#define turn_right(s,t)OnFwd(OUT_A, s);OnRev(OUT_B, s);\n");
    }
    //   }
    // }

    private void generatePrefix(boolean withWrapping) {
        if ( !withWrapping ) {
            return;
        }

        this.addConstants();
        //this.addFunctions();

        this.sb.append("task main(){");

        //add sensors:

        for ( final Entry<SensorPort, EV3Sensor> entry : this.brickConfiguration.getSensors().entrySet() ) {
            System.out.println(entry.getValue().getComponentTypeName());
            switch ( entry.getValue().getComponentTypeName() ) {
                case "EV3_COLOR_SENSOR":
                    nlIndent();
                    this.sb.append("SetSensorLight(IN_" + entry.getKey().getPortNumber() + ");");
                    break;
                case "EV3_TOUCH_SENSOR":
                    nlIndent();
                    this.sb.append("SetSensorTouch(IN_" + entry.getKey().getPortNumber() + ");");
                    break;
                case "EV3_ULTRASONIC_SENSOR":
                    nlIndent();
                    this.sb.append("SetSensorLowspeed(IN_" + entry.getKey().getPortNumber() + ");");
                    break;
                case "EV3_GYRO_SENSOR":
                    nlIndent();
                    this.sb.append("SetSensorSound(IN_" + entry.getKey().getPortNumber() + ");");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @return Java code used in the code generation to regenerates the same brick configuration
     */
    /*
    public String generateRegenerateConfiguration() {
        StringBuilder sb = new StringBuilder();
        sb.append(" brickConfiguration = new Ev3Configuration.Builder()\n");
        sb.append(INDENT).append(INDENT).append(INDENT).append("    .setWheelDiameter(" + this.brickConfiguration.getWheelDiameterCM() + ")\n");
        sb.append(INDENT).append(INDENT).append(INDENT).append("    .setTrackWidth(" + this.brickConfiguration.getTrackWidthCM() + ")\n");
        appendActors(sb);
        appendSensors(sb);
        sb.append(INDENT).append(INDENT).append(INDENT).append("    .build();");
        return sb.toString();
    }


    private void appendSensors(StringBuilder sb) {
        for ( Map.Entry<SensorPort, EV3Sensor> entry : this.brickConfiguration.getSensors().entrySet() ) {
            sb.append(INDENT).append(INDENT).append(INDENT);
            appendOptional(sb, "    .addSensor(", entry.getKey(), entry.getValue());
        }
    }

    private void appendActors(StringBuilder sb) {
        for ( Map.Entry<ActorPort, EV3Actor> entry : this.brickConfiguration.getActors().entrySet() ) {
            sb.append(INDENT).append(INDENT).append(INDENT);
            appendOptional(sb, "    .addActor(", entry.getKey(), entry.getValue());
        }
    }
    */

    /* NXT can run the sensors quite fast, so this check is unnecessary. The sensors are already added above.
     private static void appendOptional(StringBuilder sb, String type, @SuppressWarnings("rawtypes") Enum port, HardwareComponent hardwareComponent) {
        if ( hardwareComponent != null ) {
            sb.append(type).append(getEnumCode(port)).append(", ");
            if ( hardwareComponent.getCategory() == Category.SENSOR ) {
                sb.append(generateRegenerateEV3Sensor(hardwareComponent));
            } else {
                sb.append(generateRegenerateEV3Actor(hardwareComponent));
            }
            sb.append(")\n");
        }
    }


    private String generateRegenerateUsedSensors() {
        StringBuilder sb = new StringBuilder();
        String arrayOfSensors = "";
        for ( UsedSensor usedSensor : this.usedSensors ) {
            arrayOfSensors += usedSensor.generateRegenerate();
            arrayOfSensors += ", ";
        }

        sb.append("private Set<UsedSensor> usedSensors = " + "new LinkedHashSet<UsedSensor>(");
        if ( this.usedSensors.size() > 0 ) {
            sb.append("Arrays.asList(" + arrayOfSensors.substring(0, arrayOfSensors.length() - 2) + ")");
        }
        sb.append(");");
        return sb.toString();
    }
    */

    /* There is no need in explicit instantiation of the motors and other actors, except for the sensors,
     * in nxc
     private static String generateRegenerateEV3Actor(HardwareComponent actor) {
        StringBuilder sb = new StringBuilder();
        EV3Actor ev3Actor = (EV3Actor) actor;
        sb.append("new EV3Actor(").append(getHardwareComponentTypeCode(actor.getComponentType()));
        sb.append(", ").append(ev3Actor.isRegulated());
        sb.append(", ").append(getEnumCode(ev3Actor.getRotationDirection())).append(", ").append(getEnumCode(ev3Actor.getMotorSide())).append(")");
        return sb.toString();
    }

    private static String generateRegenerateEV3Sensor(HardwareComponent sensor) {
        StringBuilder sb = new StringBuilder();
        sb.append("new EV3Sensor(").append(getHardwareComponentTypeCode(sensor.getComponentType()));
        sb.append(")");
        return sb.toString();
    }

    private static String getHardwareComponentTypeCode(HardwareComponentType type) {
        return type.getClass().getSimpleName() + "." + type.getTypeName();
    }
     */

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch ( final NumberFormatException e ) {
            return false;
        }
    }

}
