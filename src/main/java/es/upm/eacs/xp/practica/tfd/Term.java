package es.upm.eacs.xp.practica.tfd;

import java.util.Set;

//This class can be abstract and its method clon and dispatch also
//By being not abstract we allow to test its methods
//Though, if it is abstract we can test then in any of his derived classes.
public abstract class Term {
    protected float value;

    protected Term(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    public boolean equal(Term obj) {
        boolean sameValue = (Math.abs(obj.getValue() - this.getValue()) <= 0.00001);
        return sameValue;
    }

    public abstract Term clon();

    public boolean hasName(String varName) {
        return false;
    }

    public boolean hasName(Set<String> setNames) {
        return false;
    }

    @Override
    public String toString() {
        String output = Float.toString(this.value);
        if (this.value >= 0) {
            output = "+" + output;
        }
        return output;
    }

    public void multiply(float value) {
        this.value = this.value * value;
    }

    public abstract void dispatch(TermVisitor termVisitor);
}
