package es.upm.eacs.xp.practica.tfd;

import java.util.Set;

public class Variable extends Term {

    private String name;

    public Variable(float value, String name) {
        super(value);
        this.name = name;
    }

    @Override
    public boolean hasName(String varName) {
        boolean isEqual = this.name.equalsIgnoreCase(varName);
        return isEqual;
    }

    @Override
    public boolean hasName(Set<String> setNames) {
        boolean isEqual = false;
        for (String name : setNames) {
            if (name.equalsIgnoreCase(this.name)) {
                isEqual = true;
                break;
            }
        }
        return isEqual;
    }

    @Override
    public boolean equal(Term obj) {
        boolean sameObjectClass = obj instanceof Variable;
        // int sameValue = Float.compare(this.value, obj.getValue());
        // boolean sameValue = Math.abs(obj.getValue() - this.getValue()) <= 0.001;
        boolean sameName = obj.hasName(this.getName());

        return sameObjectClass && super.equal(obj) && sameName;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Variable clon() {
        return new Variable(this.value, this.name);
    }

    @Override
    public String toString() {
        String theValue = Float.toString(this.value);
        if (this.value >= 0) {
            theValue = "+" + theValue;
        }

        return theValue + this.name;
    }

    @Override
    public void dispatch(TermVisitor termVisitor) {
        termVisitor.visit(this);
    }

}
