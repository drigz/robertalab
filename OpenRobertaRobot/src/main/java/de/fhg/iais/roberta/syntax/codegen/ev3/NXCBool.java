package de.fhg.iais.roberta.syntax.codegen.ev3;

public class NXCBool {

    boolean mathConstrainFunct = false;

    boolean rint = false;

    boolean prime = false;

    public boolean isRint() {
        return this.rint;
    }

    public void setRint(boolean rint) {
        this.rint = rint;
    }

    public boolean isPrime() {
        return this.prime;
    }

    public void setPrime(boolean prime) {
        this.prime = prime;
    }

    public boolean isMathConstrainFunct() {
        return this.mathConstrainFunct;
    }

    public void setMathConstrainFunct(boolean mathConstrainFunct) {
        this.mathConstrainFunct = mathConstrainFunct;
    }
}
