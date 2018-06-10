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
        int sameValue = Float.compare(this.value, obj.getValue());
        boolean sameName = obj.hasName(this.getName());

        return sameObjectClass && (sameValue == 0) && sameName;
    }

    private String getName() {
        return this.name;
    }

    @Override
    public Variable clon() {
        return new Variable(this.value, this.name);
    }

    @Override
    public String toString() {
        String theValue = Float.toString(this.value);

        return theValue + this.name;
    }

}
