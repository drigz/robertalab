package de.fhg.iais.roberta.syntax.codegen.ev3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.fhg.iais.roberta.shared.action.ev3.TurnDirection;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.action.ev3.DriveAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorDriveStopAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorGetPowerAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorOnAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorSetPowerAction;
import de.fhg.iais.roberta.syntax.action.ev3.MotorStopAction;
import de.fhg.iais.roberta.syntax.action.ev3.TurnAction;
import de.fhg.iais.roberta.syntax.expr.Expr;
import de.fhg.iais.roberta.syntax.functions.FunctionNames;
import de.fhg.iais.roberta.syntax.functions.IndexOfFunct;
import de.fhg.iais.roberta.syntax.functions.MathConstrainFunct;
import de.fhg.iais.roberta.syntax.functions.MathNumPropFunct;
import de.fhg.iais.roberta.syntax.functions.MathOnListFunct;
import de.fhg.iais.roberta.syntax.functions.MathPowerFunct;
import de.fhg.iais.roberta.syntax.functions.MathRandomIntFunct;
import de.fhg.iais.roberta.syntax.functions.MathSingleFunct;
import de.fhg.iais.roberta.syntax.functions.TextJoinFunct;
import de.fhg.iais.roberta.syntax.hardwarecheck.CheckVisitor;
import de.fhg.iais.roberta.syntax.sensor.ev3.ColorSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.EncoderSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.GyroSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.InfraredSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.TouchSensor;
import de.fhg.iais.roberta.syntax.sensor.ev3.UltrasonicSensor;
import de.fhg.iais.roberta.util.dbc.Assert;

public class CustomFunctionsVisitor extends CheckVisitor {

    private final Set<FunctionNames> functionWasMet = new HashSet<>();

    public static Set<FunctionNames> check(ArrayList<ArrayList<Phrase<Void>>> phrasesSet) {
        Assert.isTrue(phrasesSet.size() >= 1);
        final CustomFunctionsVisitor checkVisitor = new CustomFunctionsVisitor();
        for ( final ArrayList<Phrase<Void>> phrases : phrasesSet ) {
            for ( final Phrase<Void> phrase : phrases ) {
                phrase.visit(checkVisitor);
            }
        }
        return checkVisitor.getUsedFuctions();
    }

    public Set<FunctionNames> getUsedFuctions() {
        return this.functionWasMet;
    }

    @Override
    public Void visitMathNumPropFunct(MathNumPropFunct<Void> mathNumPropFunct) {
        for ( final Expr<Void> expr : mathNumPropFunct.getParam() ) {
            this.functionWasMet.add(mathNumPropFunct.getFunctName());
            expr.visit(this);
        }
        return null;
    }

    @Override
    public Void visitTextJoinFunct(TextJoinFunct<Void> textJoinFunct) {
        this.functionWasMet.add(FunctionNames.JTEXT);
        textJoinFunct.getParam().visit(this);
        return null;
    }

    @Override
    public Void visitMathOnListFunct(MathOnListFunct<Void> mathOnListFunct) {
        for ( final Expr<Void> expr : mathOnListFunct.getParam() ) {
            this.functionWasMet.add(mathOnListFunct.getFunctName());
            expr.visit(this);
        }

        /*
        switch ( mathOnListFunct.getFunctName() ) {
            case SUM:
                this.functionWasMet.add(mathOnListFunct.getFunctName());
                break;
            case MIN:
                this.functionWasMet.add(mathOnListFunct.getFunctName());
                break;
            case MAX:
                this.functionWasMet.add(mathOnListFunct.getFunctName());
                break;
            case AVERAGE:
                this.functionWasMet.add(mathOnListFunct.getFunctName());
                break;
            case MEDIAN:
                this.functionWasMet.add(mathOnListFunct.getFunctName());
                break;
            case STD_DEV:
                this.functionWasMet.add(mathOnListFunct.getFunctName());
                break;
            case RANDOM:
                this.functionWasMet.add(mathOnListFunct.getFunctName());
                break;
            case MODE:
                this.functionWasMet.add(mathOnListFunct.getFunctName());
                break;
            default:
                break;
        }*/

        return null;
    }

    @Override
    public Void visitMathSingleFunct(MathSingleFunct<Void> mathSingleFunct) {

        for ( final Expr<Void> expr : mathSingleFunct.getParam() ) {
            this.functionWasMet.add(mathSingleFunct.getFunctName());
            expr.visit(this);
        }

        /*
        switch ( mathSingleFunct.getFunctName() ) {
        case EXP:
            this.functionWasMet.add(mathSingleFunct.getFunctName());
            break;
        case POW10:
            this.functionWasMet.add(mathSingleFunct.getFunctName());
            break;

        case ROUND:
            this.functionWasMet.add(mathSingleFunct.getFunctName());
            break;
        case ROUNDUP:
            this.functionWasMet.add(mathSingleFunct.getFunctName());
            break;
        case ROUNDDOWN:
            this.functionWasMet.add(mathSingleFunct.getFunctName());
            break;
        default:
            break;
        } */

        return null;
    }

    @Override
    public Void visitIndexOfFunct(IndexOfFunct<Void> indexOfFunct) {
        this.functionWasMet.add(FunctionNames.FIRSTARR);
        indexOfFunct.getParam().get(0).visit(this);
        indexOfFunct.getParam().get(1).visit(this);
        return null;
    }

    @Override
    public Void visitMathPowerFunct(MathPowerFunct<Void> mathPowerFunct) {
        this.functionWasMet.add(FunctionNames.POW);
        mathPowerFunct.getParam().get(0).visit(this);
        mathPowerFunct.getParam().get(1).visit(this);
        return null;
    }

    @Override
    public Void visitMathConstrainFunct(MathConstrainFunct<Void> mathConstrainFunct) {
        this.functionWasMet.add(FunctionNames.CLAMP);
        mathConstrainFunct.getParam().get(0).visit(this);
        mathConstrainFunct.getParam().get(1).visit(this);
        mathConstrainFunct.getParam().get(2).visit(this);
        return null;
    }

    @Override
    public Void visitMathRandomIntFunct(MathRandomIntFunct<Void> mathRandomIntFunct) {
        this.functionWasMet.add(FunctionNames.RINT);
        mathRandomIntFunct.getParam().get(0).visit(this);
        mathRandomIntFunct.getParam().get(1).visit(this);
        return null;
    }

    @Override
    public Void visitDriveAction(DriveAction<Void> driveAction) {
        driveAction.getParam().getSpeed().visit(this);
        final boolean isDuration = driveAction.getParam().getDuration() != null;
        if ( isDuration == true ) {
            driveAction.getParam().getDuration().getValue().visit(this);
        }

        return null;
    }

    @Override
    public Void visitTurnAction(TurnAction<Void> turnAction) {

        final boolean isDuration = turnAction.getParam().getDuration() != null;
        if ( turnAction.getDirection() == TurnDirection.RIGHT ) {
            this.functionWasMet.add(FunctionNames.TURN_RIGHT);

        } else {
            this.functionWasMet.add(FunctionNames.TURN_LEFT);
        }

        turnAction.getParam().getSpeed().visit(this);
        return null;
    }

    @Override
    public Void visitMotorGetPowerAction(MotorGetPowerAction<Void> motorGetPowerAction) {
        return null;
        // TODO Auto-generated method stub

    }

    @Override
    public Void visitMotorOnAction(MotorOnAction<Void> motorOnAction) {
        final boolean isDuration = motorOnAction.getParam().getDuration() != null;
        if ( isDuration == true ) {
            motorOnAction.getParam().getDuration().getValue().visit(this);
        }
        motorOnAction.getParam().getSpeed().visit(this);
        return null;
    }

    @Override
    public Void visitMotorSetPowerAction(MotorSetPowerAction<Void> motorSetPowerAction) {
        this.functionWasMet.add(FunctionNames.OnfwdReg);
        return null;
        // TODO Auto-generated method stub
    }

    @Override
    public Void visitMotorStopAction(MotorStopAction<Void> motorStopAction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitMotorDriveStopAction(MotorDriveStopAction<Void> stopAction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitColorSensor(ColorSensor<Void> colorSensor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitEncoderSensor(EncoderSensor<Void> encoderSensor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitGyroSensor(GyroSensor<Void> gyroSensor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitInfraredSensor(InfraredSensor<Void> infraredSensor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitTouchSensor(TouchSensor<Void> touchSensor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitUltrasonicSensor(UltrasonicSensor<Void> ultrasonicSensor) {
        // TODO Auto-generated method stub
        return null;
    }

}
